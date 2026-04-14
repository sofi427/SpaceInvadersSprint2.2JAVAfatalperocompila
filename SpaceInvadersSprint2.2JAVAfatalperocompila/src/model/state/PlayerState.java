package model.state;

import java.awt.Color;

//Casilla de jugador
public class PlayerState implements SquareState {
    private final Color color;

    public PlayerState(Color color) {
        this.color = color;}
    public Color getColor() {
        return color; }
    public boolean isEmpty() {
        return false;}
	public String getStateS() {
		return "PLAYER";}
	public String collideWith(SquareState coli) { 
		return coli.collideWithPlayer(); }
	public String collideWithAlien()  { 
		return "PLAYER_HIT"; }
	public String collideWithShot()   { 
		return "NONE"; }
	public String collideWithPlayer() { 
		return "NONE"; }
}
