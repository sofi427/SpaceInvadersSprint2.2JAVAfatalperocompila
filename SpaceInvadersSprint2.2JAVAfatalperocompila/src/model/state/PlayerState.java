package model.state;

import java.awt.Color;

import model.Board;

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
    public String collideWith(SquareState other) {
        if (other.getStateS().equals("EMPTY")) {return "move";}
        if (other.getStateS().equals("ALIEN")) {Board.getMyBoard().gameLost();}
        return "notmove";
    }
}