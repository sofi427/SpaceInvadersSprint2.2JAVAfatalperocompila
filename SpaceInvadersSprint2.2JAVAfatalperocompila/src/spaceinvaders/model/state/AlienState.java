package spaceinvaders.model.state;

import java.awt.Color;

public class AlienState implements SquareState {
    @Override
    public Color getColor() {
        return Color.MAGENTA;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
