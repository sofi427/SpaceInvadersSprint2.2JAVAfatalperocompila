package model.player;

import spaceinvaders.model.BluePlayer;
import spaceinvaders.model.GreenPlayer;
import spaceinvaders.model.RedPlayer;

/**
 * PlayerFactory - Factory + Singleton.
 * Produces AbstractPlayer subclasses by type string.
 */
public class PlayerFactory {

    private static PlayerFactory instance;

    private PlayerFactory() {}

    public static PlayerFactory getInstance() {
        if (instance == null) instance = new PlayerFactory();
        return instance;
    }

    public AbstractPlayer generate(String type, int centerX, int centerY) {
        return switch (type.toUpperCase()) {
            case "GREEN" -> new GreenPlayer(centerX, centerY);
            case "BLUE"  -> new BluePlayer(centerX, centerY);
            case "RED"   -> new RedPlayer(centerX, centerY);
            default -> throw new IllegalArgumentException("Unknown player type: " + type);
        };
    }
}
