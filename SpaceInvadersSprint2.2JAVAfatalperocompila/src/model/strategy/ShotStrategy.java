package model.strategy;

import model.composite.SquareComposite;

/**
 * ShotStrategy interface - Strategy Pattern.
 *
 * Extended from the original to add:
 *   - getShotTypeName()    → "PIXEL" | "ARROW" | "DIAMOND"  (used by GameScreen)
 *   - getRemainingShots()  → current ammo pool
 *   - consumeShot()        → deduct one shot
 */
public interface ShotStrategy {

    SquareComposite buildShape(int originX, int originY);

    /**
     * Maximum shots allowed for this strategy (-1 = unlimited).
     * This is the INITIAL cap, not the remaining count.
     */
    int getMaxShots();

    /**
     * Short identifier used internally (e.g. "Pixel", "Arrow", "Diamond").
     */
    String getName();

    /**
     * Uppercase type name sent to the GameScreen status bar.
     * Default implementation: getName().toUpperCase()
     */
    default String getShotTypeName() {
        return getName().toUpperCase();
    }

    /**
     * Remaining shots in this strategy's ammo pool.
     * Strategies with getMaxShots() == -1 always return -1.
     */
    int getRemainingShots();

    /**
     * Deducts one shot from the ammo pool.
     * No-op when getMaxShots() == -1.
     */
    void consumeShot();
}
