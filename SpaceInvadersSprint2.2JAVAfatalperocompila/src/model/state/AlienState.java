package model.state;

import java.awt.Color;

//Casilla de alien
public class AlienState implements SquareState {
    public Color getColor() {
        return Color.MAGENTA;
    }
    public boolean isEmpty() {
        return false;
    }
	public String getStateS() {
		return "Alien";
	}
}
