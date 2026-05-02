package model.state;

import java.awt.Color;

import model.Board;

public class ShotState implements SquareState {

    @Override
    public Color getColor() {return Color.YELLOW;}

    @Override
    public boolean isEmpty() {return false;}

    @Override
    public String getStateS() {return "SHOT";}

    @Override
    public String collideWith(SquareState other) {
        if (other.getStateS().equalsIgnoreCase("Empty")) {return "move";}
        if (other.getStateS().equalsIgnoreCase("ALIEN")) {return "destroyboth";}
        if (other.getStateS().equalsIgnoreCase("PLAYER")) {Board.getMyBoard().gameLost();}
        if (other.getStateS().equalsIgnoreCase("SHOT")) {return "destroyboth";}
        return "notmove";
    }
}

