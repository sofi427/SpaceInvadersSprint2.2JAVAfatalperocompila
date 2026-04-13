package model.state;

import java.awt.Color;

public class ShotState implements SquareState {
    @Override
    public Color getColor() {
        return Color.YELLOW;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
