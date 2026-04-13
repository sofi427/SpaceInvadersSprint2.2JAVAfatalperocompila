package model.player;

/**
 * PlayerGenerator - Singleton.
 *
 * Used by StartController:
 *   AbstractPlayer player = PlayerGenerator.getGenerator().generate(selectedShip);
 *
 * Delegates to PlayerFactory for actual instantiation.
 * Default spawn position: centre of the board (50, 55) as per the spec.
 */
public class PlayerGenerator {

    private static PlayerGenerator myPlayerGenerator;
    private final PlayerFactory playerFactory;

    private PlayerGenerator() {
        this.playerFactory = PlayerFactory.getInstance();
    }

    public static PlayerGenerator getGenerator() {
        if (myPlayerGenerator == null) {
            myPlayerGenerator = new PlayerGenerator();
        }
        return myPlayerGenerator;
    }

    /**
     * Creates a player of the given type at the default starting position (50, 55).
     * @param type "GREEN", "BLUE" or "RED" (case-insensitive)
     */
    public AbstractPlayer generate(String type) {
        return playerFactory.generate(type, 50, 55);
    }

    /**
     * Overload with explicit starting position.
     */
    public AbstractPlayer generate(String type, int centerX, int centerY) {
        return playerFactory.generate(type, centerX, centerY);
    }
}
