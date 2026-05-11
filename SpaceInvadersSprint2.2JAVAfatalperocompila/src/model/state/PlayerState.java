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
        String state = other.getStateS().toUpperCase();
        switch (state) {
            case "EMPTY":
                return "move";
            case "ALIEN":
            case "SHOT":
                Board.getMyBoard().gameLost();
                break;
        }

        return "notmove";
    }
}