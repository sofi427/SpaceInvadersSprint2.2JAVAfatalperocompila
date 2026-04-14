package model.state;

import java.awt.Color;

//Casilla de disparo
public class ShotState implements SquareState {
    public Color getColor() { 
        return Color.YELLOW;}
    public boolean isEmpty() {
        return false;}
	public String getStateS() {
		return "Shot";} 
	public String collideWith(SquareState coli) { 
		return coli.collideWithShot(); }
	public String collideWithAlien()  { 
		return "ALIEN_KILLED"; }
	public String collideWithShot()   { 
		return "NONE"; }
	public String collideWithPlayer() { 
		return "NONE"; }
}
