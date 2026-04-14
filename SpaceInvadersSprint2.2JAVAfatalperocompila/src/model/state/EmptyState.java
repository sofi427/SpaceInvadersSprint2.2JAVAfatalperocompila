package model.state;

import java.awt.Color;

//Casilla vacia
public class EmptyState implements SquareState {
    public Color getColor() {
        return Color.BLACK;
    }
    public boolean isEmpty() {
        return true;
    }
	public String getStateS() {
		return "Empty";
	}
}
