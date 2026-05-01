package model;

import model.composite.Square;
import model.composite.SquareComposite;
import model.state.AlienState;
import model.strategy.DiamondStrategy;
import model.strategy.ShotStrategy;

public class FinalBoss extends NormalAlien {

    public FinalBoss(int centerX, int centerY) {
        super(centerX, centerY);
    }
   
    @Override
    public void shoot() {
		Square centre = this.squares.getCenterSquare();
		ShotStrategy shot = new DiamondStrategy();
		Shot newShot = new Shot(shot, centre.getPosX(), centre.getPosY()+4);
		newShot.startMoving("down");
		shots.add(newShot);
    }
    
}