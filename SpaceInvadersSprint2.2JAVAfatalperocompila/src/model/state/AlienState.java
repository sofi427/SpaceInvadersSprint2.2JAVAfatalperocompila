package model.state;

import java.awt.Color;

import model.player.AbstractPlayer;

public class AlienState implements SquareState {

    @Override
    public Color getColor() {return Color.MAGENTA;}

    @Override
    public boolean isEmpty() {return false;}

    @Override
    public String getStateS() {return "ALIEN";}

    @Override
    public void collideWith(SquareState other) {other.collideWithAlien();}

    @Override
    public void collideWithPlayer() {AbstractPlayer.getPlayer().notifyLost();}

    @Override
    public void collideWithShot() {
        // Aquí tenemos que eliminar el alien, linea con alienmanager??
    }

    @Override
    public void collideWithAlien() {}
}
``