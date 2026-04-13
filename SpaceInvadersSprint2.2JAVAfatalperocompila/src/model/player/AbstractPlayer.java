package model.player;

import spaceinvaders.model.composite.SquareComposite;
import model.strategy.ShotStrategy;

import java.util.List;

/**
 * AbstractPlayer - base class for all player ships.
 * Renamed from Player to match the package/import used by StartScreen and Board.
 *
 * Adds ammo tracking so GameScreen can display remaining arrow/diamond shots.
 */
public abstract class AbstractPlayer {

    protected SquareComposite    squares;
    protected ShotStrategy       currentStrategy;
    protected List<ShotStrategy> availableStrategies;
    protected int                strategyIndex = 0;

    public AbstractPlayer(int centerX, int centerY) {
        this.squares             = buildShape(centerX, centerY);
        this.availableStrategies = buildStrategies();
        this.currentStrategy     = availableStrategies.get(0);
    }

    protected abstract SquareComposite    buildShape(int cx, int cy);
    protected abstract List<ShotStrategy> buildStrategies();

    // ── Movement ──────────────────────────────────────────────────────────────

    public void move(int dx, int dy) { squares.move(dx, dy); }

    // ── Weapon cycling ────────────────────────────────────────────────────────

    public void cycleWeapon() {
        strategyIndex    = (strategyIndex + 1) % availableStrategies.size();
        currentStrategy  = availableStrategies.get(strategyIndex);
    }

    // ── Ammo ──────────────────────────────────────────────────────────────────

    /**
     * Returns true if the player can still fire with the current strategy.
     * Pixel strategy is always unlimited (-1).
     */
    public boolean hasAmmo() {
        int max = currentStrategy.getMaxShots();
        if (max == -1) return true;
        return currentStrategy.getRemainingShots() > 0;
    }

    /**
     * Deducts one shot from the current strategy's ammo pool.
     * No-op for unlimited strategies.
     */
    public void consumeAmmo() {
        currentStrategy.consumeShot();
    }

    /** Remaining arrow shots (used by GameScreen status bar). */
    public int getAmmoFecha() {
        return availableStrategies.stream()
                .filter(s -> s.getName().equals("Arrow"))
                .mapToInt(ShotStrategy::getRemainingShots)
                .findFirst()
                .orElse(0);
    }

    /** Remaining diamond shots (used by GameScreen status bar). */
    public int getAmmoRombo() {
        return availableStrategies.stream()
                .filter(s -> s.getName().equals("Diamond"))
                .mapToInt(ShotStrategy::getRemainingShots)
                .findFirst()
                .orElse(0);
    }

    // ── Getters ───────────────────────────────────────────────────────────────

    public ShotStrategy    getCurrentStrategy() { return currentStrategy; }
    public SquareComposite getSquares()          { return squares; }
}
