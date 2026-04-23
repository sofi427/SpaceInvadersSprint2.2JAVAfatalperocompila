package model;

import model.composite.Component;
import model.composite.Square;
import model.composite.SquareComposite;
import model.strategy.ShotStrategy;

import java.util.Timer;
import java.util.TimerTask;

public class Shot {
    private ShotStrategy strategy;
    private SquareComposite squares;
    private Timer timer;
    private boolean active;

    
    public Shot(ShotStrategy strategy, int startX, int startY) {
        this.strategy = strategy;
        this.squares = strategy.buildShape(startX, startY);
        this.active = true;
    }

    public ShotStrategy getStrategy(){ 
    	return strategy; 
    }
    
    public void changeStrategy(ShotStrategy strategy){ 
    	this.strategy = strategy; 
    }
    
    public SquareComposite getSquares(){ 
    	return squares; 
    }
    
    public void startMoving() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (active) {
                    moveUp();
                }
            }
        }, 0, 50);
    }

    private void moveUp() {
        for (int i=0; i<squares.getSquares().size(); i++) {
        	Component sq=squares.getSquares().get(i);
            if (((Square) sq).getPosY()-1 < 0) {
                destroyShot();
                return;
            }
        }
        squares.move(0, -1);
        Board.getMyBoard().onShotMoved(this);
    }

    public void destroyShot() {
        active = false;
        if (timer != null) {
        	timer.cancel();
        }
    }

    public boolean isActive(){ 
    	return active; 
    }
    

}
 

