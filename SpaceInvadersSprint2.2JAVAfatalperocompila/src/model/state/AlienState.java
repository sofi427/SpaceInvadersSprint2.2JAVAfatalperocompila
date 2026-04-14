package model.state;

import java.awt.Color;

//Casilla de alien
public class AlienState implements SquareState {
    public Color getColor() {
        return Color.MAGENTA;}
    public boolean isEmpty() {
        return false;}
	public String getStateS() {
		return "Alien";}
	public String collideWith(SquareState coli) { 
		return coli.collideWithAlien(); }
	public String collideWithAlien()  { 
		return "NONE"; }
	public String collideWithShot()   { 
		return "ALIEN_KILLED"; }
	public String collideWithPlayer() { 
		return "PLAYER_HIT"; }
}
