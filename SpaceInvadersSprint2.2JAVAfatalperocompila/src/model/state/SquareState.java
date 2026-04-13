package model.state;

import java.awt.Color;

/**
 * Interface SquareState - State Pattern
 * Represents the possible states a Square can be in.
 */
public interface SquareState {
    Color getColor();
    boolean isEmpty();
}
