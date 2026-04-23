package model.state;

import java.awt.Color;

public class PlayerState implements SquareState {

    private final Color color;

    public PlayerState(Color color) {
        this.color = color;
    }

    @Override
    public Color getColor() {return color;}

    @Override
    public boolean isEmpty() {return false; }

    @Override
    public String getStateS() {return "PLAYER"; }

    @Override
    public String collideWith(SquareState other) {return other.collideWithPlayer();}
    
    @Override
    public String collideWithAlien() {return gamelost;}

    @Override
    public String collideWithShot() {return block;}

    @Override
    public String collideWithPlayer() {return block;}
}