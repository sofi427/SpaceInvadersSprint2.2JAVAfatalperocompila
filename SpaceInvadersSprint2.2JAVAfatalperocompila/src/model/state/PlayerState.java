package model.state;

import java.awt.Color;
import model.player.AbstractPlayer;

public class PlayerState implements SquareState {

    @Override
    public Color getColor() {return AbstractPlayer.getPlayer().getColor();}

    @Override
    public boolean isEmpty() {return false; }

    @Override
    public String getStateS() {return "PLAYER"; }

    @Override
    public void collideWith(SquareState other) {other.collideWithPlayer();}
    
    @Override
    public void collideWithAlien() {AbstractPlayer.getPlayer().notifyLost();}

    @Override
    public void collideWithShot() {}

    @Override
    public void collideWithPlayer() {}
}