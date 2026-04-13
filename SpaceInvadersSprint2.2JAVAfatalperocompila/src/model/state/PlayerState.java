package model.state;

import java.awt.Color;

public class PlayerState implements SquareState {
    private final Color color;

    public PlayerState(Color color) {
        this.color = color;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
