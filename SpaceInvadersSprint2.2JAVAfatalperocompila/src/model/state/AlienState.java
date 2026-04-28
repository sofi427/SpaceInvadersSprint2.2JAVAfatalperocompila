package model.state;

import java.awt.Color;

import model.Board;

public class AlienState implements SquareState {

    @Override public Color   getColor()  { return Color.MAGENTA; }
    @Override public boolean isEmpty()   { return false; }
    @Override public String  getStateS() { return "ALIEN"; }

    @Override
    public String collideWith(SquareState other) {
        if (other.getStateS().equals("EMPTY"))  {return "move";}
        if (other.getStateS().equals("PLAYER")) {Board.getMyBoard().gameLost();}
        if (other.getStateS().equals("SHOT"))   {return "destroyboth";}
        return "notmove";
    }
}