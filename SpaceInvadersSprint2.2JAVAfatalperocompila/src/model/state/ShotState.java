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
        String state = other.getStateS().toUpperCase();
        switch (state) {
            case "EMPTY":
                return "move";
            case "ALIEN":
            	return "destroyboth";
            case "SHOT":
            	return "destroyboth";
            case "PLAYER":
                Board.getMyBoard().gameLost();
                break;
        }

        return "notmove";
    }
}

