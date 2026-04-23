package model.state;

import java.awt.Color;

public class AlienState implements SquareState {

    @Override
    public Color getColor() {return Color.MAGENTA;}

    @Override
    public boolean isEmpty() {return false;}

    @Override
    public String getStateS() {return "ALIEN";}

    @Override
    public String collideWith(SquareState other) {return other.collideWithAlien();}

    @Override
    public String collideWithPlayer() {return gamelost;}

    @Override
    public String collideWithShot() {return destroyboth;}

    @Override
    public String collideWithAlien() {return block;}
}