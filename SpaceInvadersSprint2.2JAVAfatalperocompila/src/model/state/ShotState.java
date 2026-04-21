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
    public void collideWith(SquareState other) {other.collideWithShot();}

    @Override
    public void collideWithAlien() {
        //Aqui deberia desaparecer el disparo
    }

    @Override
    public void collideWithShot() {}

    @Override
    public void collideWithPlayer() {}
}
