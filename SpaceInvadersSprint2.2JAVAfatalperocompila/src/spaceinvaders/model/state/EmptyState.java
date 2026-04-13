package spaceinvaders.model.state;

import java.awt.Color;

public class EmptyState implements SquareState {
    @Override
    public Color getColor() {
        return Color.BLACK;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }
}
