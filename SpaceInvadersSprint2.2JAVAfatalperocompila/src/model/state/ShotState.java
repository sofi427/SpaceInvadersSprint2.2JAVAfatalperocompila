package model.state;

import java.awt.Color;

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
        return "notmove";
    }
}

