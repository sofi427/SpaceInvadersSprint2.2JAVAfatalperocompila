package model.state;

import java.awt.Color;
import model.Board;

public class AlienState implements SquareState {

    @Override public Color   getColor()  { return Color.MAGENTA; }
    @Override public boolean isEmpty()   { return false; }
    @Override public String  getStateS() { return "ALIEN"; }

    @Override
    public String collideWith(SquareState other) {
        if (other.getStateS().equalsIgnoreCase("Empty"))  {return "move";}
        if (other.getStateS().equalsIgnoreCase("PLAYER")) {Board.getMyBoard().gameLost();}
        if (other.getStateS().equalsIgnoreCase("Shot"))   {return "destroyboth";}
        return "notmove";
    }
}