package model;

import model.composite.Square;
import model.player.AbstractPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Board - Singleton + Observable (Observer pattern).
 *
 * This is the central model.  It is the ONLY class that calls
 * setChanged() / notifyObservers().  It sends:
 *
 *   "READY"   – board fully initialised, player placed → StartScreen reacts
 *   int[][]   – normal repaint frame                   → GameScreen reacts
 *   "WON"     – all aliens destroyed                   → GameScreen reacts
 *   "LOST"    – alien reached bottom or hit player     → GameScreen reacts
 *
 * Public API (used by controllers):
 *   actBoard()                  – reset aliens, clear shots
 *   actBoard(AbstractPlayer)    – place player → notifies "READY"
 *   updateBoardEvery200ms()     – start alien movement timer
 *   stopGame()                  – stop all timers
 *   movePlayerLeft/Right/Up/Down()
 *   shoot()
 *   changePlayerStrategy()
 */
@SuppressWarnings("deprecation")
public class Board extends Observable {

    // ── Singleton ─────────────────────────────────────────────────────────────
    private static Board myBoard;

    private Board() {}

    public static Board getMyBoard() {
        if (myBoard == null) myBoard = new Board();
        return myBoard;
    }

    // ── Board dimensions & cell codes ─────────────────────────────────────────
    public static final int WIDTH  = 60;   // rows  (Y axis)
    public static final int LENGTH = 100;  // cols  (X axis)

    public static final int CELL_EMPTY  = 0;
    public static final int CELL_PLAYER = 1;
    public static final int CELL_ALIEN  = 2;
    public static final int CELL_SHOT   = 3;

    // ── State ─────────────────────────────────────────────────────────────────
    private AbstractPlayer    player;
    private AlienGroup        alienGroup;
    private final List<Shot>  shots      = new ArrayList<>();
    private Timer             alienTimer;
    private boolean           running    = false;

    // ── Initialisation (called by StartController) ────────────────────────────

    /**
     * Step 1 – reset everything except the player.
     * Called before actBoard(player) so the view already has a clean board.
     */
    public void actBoard() {
        stopGame();
        shots.clear();
        alienGroup = new AlienGroup();
        player     = null;
    }

    /**
     * Step 2 – place the player and notify "READY" so StartScreen
     * switches to GameScreen.
     */
    public void actBoard(AbstractPlayer p) {
        this.player = p;
        running     = true;
        notifyView("READY");
    }

    /**
     * Step 3 – start the alien-movement timer (200 ms).
     * Called AFTER the GameScreen observer has been registered.
     */
    public void updateBoardEvery200ms() {
        alienTimer = new Timer(true);
        alienTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!running) return;
                alienGroup.moveDown();
                checkEndConditions();
                if (running) notifyView(buildMatrix());
            }
        }, 200, 200);
    }

    /** Stops all timers and marks the game as not running. */
    public void stopGame() {
        running = false;
        if (alienTimer != null) { alienTimer.cancel(); alienTimer = null; }
        for (Shot s : shots) s.destroy();
    }

    // ── Player movement (called by GameController) ────────────────────────────

    public void movePlayerLeft()  { movePlayer(-1,  0); }
    public void movePlayerRight() { movePlayer( 1,  0); }
    public void movePlayerUp()    { movePlayer( 0, -1); }
    public void movePlayerDown()  { movePlayer( 0,  1); }

    private void movePlayer(int dx, int dy) {
        if (!running || player == null) return;
        for (Square sq : player.getSquares().getSquares()) {
            int nx = sq.getPosX() + dx;
            int ny = sq.getPosY() + dy;
            if (nx < 0 || nx >= LENGTH || ny < 0 || ny >= WIDTH) return;
        }
        player.move(dx, dy);
        notifyView(buildMatrix());
    }

    // ── Weapon / shooting ─────────────────────────────────────────────────────

    public void changePlayerStrategy() {
        if (!running || player == null) return;
        player.cycleWeapon();
        notifyView(buildMatrix());
    }

    public void shoot() {
        if (!running || player == null) return;

        // Check ammo limit
        int maxShots = player.getCurrentStrategy().getMaxShots();
        if (maxShots != -1) {
            long active = shots.stream()
                    .filter(Shot::isActive)
                    .filter(s -> s.getStrategy().getName()
                            .equals(player.getCurrentStrategy().getName()))
                    .count();
            if (active >= maxShots) return;
        }

        // Also check stored ammo on the player
        if (!player.hasAmmo()) return;

        // Origin: center-X of player, 2 rows above topmost pixel
        int cx = player.getSquares().getSquares().stream()
                .mapToInt(Square::getPosX).sum()
                / player.getSquares().getSquares().size();
        int topY = player.getSquares().getSquares().stream()
                .mapToInt(Square::getPosY).min().orElse(50) - 2;

        Shot shot = new Shot(player.getCurrentStrategy(), cx, topY, this);
        shots.add(shot);
        player.consumeAmmo();   // deduct ammo for arrow/diamond
        shot.startMoving();
        notifyView(buildMatrix());
    }

    // ── Shot callback (called by Shot when it moves) ──────────────────────────

    /**
     * Called by each Shot after it moves one step.
     * Handles alien collision and triggers a repaint.
     */
    public void onShotMoved(Shot shot) {
        if (!running) return;

        Alien hit = alienGroup.findHitAlien(shot.getSquares().getSquares());
        if (hit != null) {
            alienGroup.remove(hit);
            shot.destroy();
        }

        shots.removeIf(s -> !s.isActive());
        checkEndConditions();
        if (running) notifyView(buildMatrix());
    }

    // ── Win / lose ────────────────────────────────────────────────────────────

    private void checkEndConditions() {
        if (!running) return;

        if (alienGroup.collidesWithPlayer(player) || alienGroup.hasReachedBottom()) {
            stopGame();
            notifyView("LOST");
        } else if (alienGroup.isEmpty()) {
            stopGame();
            notifyView("WON");
        }
    }

    // ── Matrix builder ────────────────────────────────────────────────────────

    /**
     * Builds the int[WIDTH][LENGTH] matrix that GameScreen uses to repaint.
     * Priority: SHOT > ALIEN > PLAYER > EMPTY.
     */
    public int[][] buildMatrix() {
        int[][] m = new int[WIDTH][LENGTH];

        // Player
        if (player != null) {
            for (Square sq : player.getSquares().getSquares()) {
                set(m, sq.getPosY(), sq.getPosX(), CELL_PLAYER);
            }
        }

        // Aliens
        if (alienGroup != null) {
            for (Alien alien : alienGroup.getAliens()) {
                for (Square sq : alien.getSquares().getSquares()) {
                    set(m, sq.getPosY(), sq.getPosX(), CELL_ALIEN);
                }
            }
        }

        // Shots (highest priority — drawn on top)
        for (Shot shot : shots) {
            if (shot.isActive()) {
                for (Square sq : shot.getSquares().getSquares()) {
                    set(m, sq.getPosY(), sq.getPosX(), CELL_SHOT);
                }
            }
        }

        return m;
    }

    /** Writes a cell value only if the coordinates are within bounds. */
    private void set(int[][] m, int row, int col, int value) {
        if (row >= 0 && row < WIDTH && col >= 0 && col < LENGTH) {
            m[row][col] = value;
        }
    }

    // ── Observable helpers ────────────────────────────────────────────────────

    private void notifyView(Object payload) {
        setChanged();
        notifyObservers(payload);
    }

    // ── Getters ───────────────────────────────────────────────────────────────

    public AbstractPlayer getPlayer()    { return player; }
    public AlienGroup     getAlienGroup(){ return alienGroup; }
    public List<Shot>     getShots()     { return shots; }
}
