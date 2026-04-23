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
    public String collideWith(SquareState other) {return other.collideWithShot();}

    @Override
    public String collideWithAlien() {return destroyboth;}

    @Override
    public String collideWithShot() {return block;}

    @Override
    public String collideWithPlayer() {return block;}
}

