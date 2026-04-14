package model.state;

import java.awt.Color;

//Casilla vacia
public class EmptyState implements SquareState {
	
    public Color getColor() {
    	return Color.BLACK;}
    public boolean isEmpty() {
    	return true;}
	public String getStateS() {
		return "Empty";}
	public String collideWith(SquareState coli) { 
		return "NONE"; }
	public String collideWithAlien()  { 
		return "NONE"; }
	public String collideWithShot()   { 
		return "NONE"; }
	public String collideWithPlayer() { 
		return "NONE"; }
}
