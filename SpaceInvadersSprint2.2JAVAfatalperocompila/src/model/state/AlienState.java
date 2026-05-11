package model.state;

import java.awt.Color;
import model.Board;

public class AlienState implements SquareState {

    @Override public Color   getColor()  { return Color.MAGENTA; }
    @Override public boolean isEmpty()   { return false; }
    @Override public String  getStateS() { return "ALIEN"; }

    @Override
    public String collideWith(SquareState other) {
        String state = other.getStateS().toUpperCase();
        switch (state) {
            case "EMPTY":
                return "move";
            case "SHOT":
            	return "destroyboth";
            case "PLAYER":
            	Board.getMyBoard().gameLost();
                break;
        }

        return "notmove";
    }
}