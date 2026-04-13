package model;

import model.composite.Square;
import model.player.AbstractPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * AlienGroup - manages the collection of Alien enemies.
 * Spawns 4-8 aliens randomly (no overlaps). Moves them down each tick.
 */
public class AlienGroup {

    private final List<Alien> aliens = new ArrayList<>();
    private static final int MIN_SPACING = 7; // min distance between alien centers

    public AlienGroup() {
        spawnAliens();
    }

    // ── Spawn ─────────────────────────────────────────────────────────────────

    private void spawnAliens() {
        Random rng   = new Random();
        int    count = 4 + rng.nextInt(5); // [4, 8]

        List<int[]> placed = new ArrayList<>();
        int attempts = 0;

        while (aliens.size() < count && attempts < 2000) {
            attempts++;
            // keep aliens in the top quarter, away from edges
            int cx = 3 + rng.nextInt(Board.LENGTH - 6);
            int cy = 2 + rng.nextInt(8);

            if (noOverlap(cx, cy, placed)) {
                aliens.add(new Alien(cx, cy));
                placed.add(new int[]{cx, cy});
            }
        }
    }

    private boolean noOverlap(int cx, int cy, List<int[]> placed) {
        for (int[] p : placed) {
            if (Math.abs(p[0] - cx) < MIN_SPACING && Math.abs(p[1] - cy) < MIN_SPACING) {
                return false;
            }
        }
        return true;
    }

    // ── Movement ──────────────────────────────────────────────────────────────

    /** Moves every living alien one row down. */
    public void moveDown() {
        for (Alien a : aliens) a.move(0, 1);
    }

    // ── Collision helpers ─────────────────────────────────────────────────────

    /**
     * Returns the first alien hit by the given shot pixels, or null if none.
     */
    public Alien findHitAlien(List<Square> shotSquares) {
        for (Alien alien : aliens) {
            for (Square as : alien.getSquares().getSquares()) {
                for (Square ss : shotSquares) {
                    if (as.getPosX() == ss.getPosX() && as.getPosY() == ss.getPosY()) {
                        return alien;
                    }
                }
            }
        }
        return null;
    }

    /** Returns true if any alien pixel overlaps any player pixel. */
    public boolean collidesWithPlayer(AbstractPlayer player) {
        List<Square> playerSqs = player.getSquares().getSquares();
        for (Alien alien : aliens) {
            for (Square as : alien.getSquares().getSquares()) {
                for (Square ps : playerSqs) {
                    if (as.getPosX() == ps.getPosX() && as.getPosY() == ps.getPosY()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /** Returns true if any alien has reached the bottom boundary. */
    public boolean hasReachedBottom() {
        for (Alien a : aliens) {
            if (a.getBottomY() >= Board.WIDTH - 1) return true;
        }
        return false;
    }

    // ── Accessors ─────────────────────────────────────────────────────────────

    public void    remove(Alien a)   { aliens.remove(a); }
    public boolean isEmpty()         { return aliens.isEmpty(); }
    public List<Alien> getAliens()   { return aliens; }
}
