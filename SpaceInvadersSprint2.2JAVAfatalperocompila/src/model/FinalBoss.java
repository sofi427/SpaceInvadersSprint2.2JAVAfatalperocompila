package model;

import model.composite.Square;
import model.composite.SquareComposite;
import model.state.AlienState;
import model.strategy.DiamondStrategy;
import model.strategy.ShotStrategy;

public class FinalBoss extends NormalAlien {

	private int life = 15;
	
    public FinalBoss(int centerX, int centerY) {
        super(centerX, centerY);
    }
   
    @Override
    protected SquareComposite makeShape(int x, int y) {
        SquareComposite c = new SquareComposite();
        AlienState s = new AlienState();

        // y - 4
        c.add(new Square(x -10, y -8, s));
        c.add(new Square(x -9,  y -8, s));
        c.add(new Square(x -10, y -7, s));
        c.add(new Square(x -9,  y -7, s));

        c.add(new Square(x -8, y -8, s));
        c.add(new Square(x -7, y -8, s));
        c.add(new Square(x -8, y -7, s));
        c.add(new Square(x -7, y -7, s));

        c.add(new Square(x -6, y -8, s));
        c.add(new Square(x -5, y -8, s));
        c.add(new Square(x -6, y -7, s));
        c.add(new Square(x -5, y -7, s));

        // y - 3
        c.add(new Square(x -10, y -6, s));
        c.add(new Square(x -9,  y -6, s));
        c.add(new Square(x -10, y -5, s));
        c.add(new Square(x -9,  y -5, s));

        c.add(new Square(x -8, y -6, s));
        c.add(new Square(x -7, y -6, s));
        c.add(new Square(x -8, y -5, s));
        c.add(new Square(x -7, y -5, s));

        c.add(new Square(x -6, y -6, s));
        c.add(new Square(x -5, y -6, s));
        c.add(new Square(x -6, y -5, s));
        c.add(new Square(x -5, y -5, s));

        c.add(new Square(x -4, y -6, s));
        c.add(new Square(x -3, y -6, s));
        c.add(new Square(x -4, y -5, s));
        c.add(new Square(x -3, y -5, s));

        c.add(new Square(x +6, y -6, s));
        c.add(new Square(x +7, y -6, s));
        c.add(new Square(x +6, y -5, s));
        c.add(new Square(x +7, y -5, s));

        c.add(new Square(x +8, y -6, s));
        c.add(new Square(x +9, y -6, s));
        c.add(new Square(x +8, y -5, s));
        c.add(new Square(x +9, y -5, s));

        c.add(new Square(x +10, y -6, s));
        c.add(new Square(x +11, y -6, s));
        c.add(new Square(x +10, y -5, s));
        c.add(new Square(x +11, y -5, s));

        // y - 2
        c.add(new Square(x -10, y -4, s));
        c.add(new Square(x -9,  y -4, s));
        c.add(new Square(x -10, y -3, s));
        c.add(new Square(x -9,  y -3, s));

        c.add(new Square(x -8, y -4, s));
        c.add(new Square(x -7, y -4, s));
        c.add(new Square(x -8, y -3, s));
        c.add(new Square(x -7, y -3, s));

        c.add(new Square(x -6, y -4, s));
        c.add(new Square(x -5, y -4, s));
        c.add(new Square(x -6, y -3, s));
        c.add(new Square(x -5, y -3, s));

        c.add(new Square(x -2, y -4, s));
        c.add(new Square(x -1, y -4, s));
        c.add(new Square(x -2, y -3, s));
        c.add(new Square(x -1, y -3, s));

        c.add(new Square(x +10, y -4, s));
        c.add(new Square(x +11, y -4, s));
        c.add(new Square(x +10, y -3, s));
        c.add(new Square(x +11, y -3, s));

        // y - 1
        c.add(new Square(x -10, y -2, s));
        c.add(new Square(x -9,  y -2, s));
        c.add(new Square(x -10, y -1, s));
        c.add(new Square(x -9,  y -1, s));

        c.add(new Square(x -8, y -2, s));
        c.add(new Square(x -7, y -2, s));
        c.add(new Square(x -8, y -1, s));
        c.add(new Square(x -7, y -1, s));

        c.add(new Square(x +6, y -2, s));
        c.add(new Square(x +7, y -2, s));
        c.add(new Square(x +6, y -1, s));
        c.add(new Square(x +7, y -1, s));

        c.add(new Square(x +8, y -2, s));
        c.add(new Square(x +9, y -2, s));
        c.add(new Square(x +8, y -1, s));
        c.add(new Square(x +9, y -1, s));

        c.add(new Square(x +10, y -2, s));
        c.add(new Square(x +11, y -2, s));
        c.add(new Square(x +10, y -1, s));
        c.add(new Square(x +11, y -1, s));

        // y
        c.add(new Square(x -10, y, s));
        c.add(new Square(x -9,  y, s));
        c.add(new Square(x -10, y +1, s));
        c.add(new Square(x -9,  y +1, s));

        c.add(new Square(x -8, y, s));
        c.add(new Square(x -7, y, s));
        c.add(new Square(x -8, y +1, s));
        c.add(new Square(x -7, y +1, s));

        c.add(new Square(x -6, y, s));
        c.add(new Square(x -5, y, s));
        c.add(new Square(x -6, y +1, s));
        c.add(new Square(x -5, y +1, s));

        c.add(new Square(x -4, y, s));
        c.add(new Square(x -3, y, s));
        c.add(new Square(x -4, y +1, s));
        c.add(new Square(x -3, y +1, s));

        c.add(new Square(x,     y, s));
        c.add(new Square(x +1,  y, s));
        c.add(new Square(x,     y +1, s));
        c.add(new Square(x +1,  y +1, s));

        c.add(new Square(x +8, y, s));
        c.add(new Square(x +9, y, s));
        c.add(new Square(x +8, y +1, s));
        c.add(new Square(x +9, y +1, s));

        c.add(new Square(x +10, y, s));
        c.add(new Square(x +11, y, s));
        c.add(new Square(x +10, y +1, s));
        c.add(new Square(x +11, y +1, s));

        // y + 1
        c.add(new Square(x -6, y +2, s));
        c.add(new Square(x -5, y +2, s));
        c.add(new Square(x -6, y +3, s));
        c.add(new Square(x -5, y +3, s));

        c.add(new Square(x +2, y +2, s));
        c.add(new Square(x +3, y +2, s));
        c.add(new Square(x +2, y +3, s));
        c.add(new Square(x +3, y +3, s));

        c.add(new Square(x +6, y +2, s));
        c.add(new Square(x +7, y +2, s));
        c.add(new Square(x +6, y +3, s));
        c.add(new Square(x +7, y +3, s));

        // y + 2
        c.add(new Square(x -6, y +4, s));
        c.add(new Square(x -5, y +4, s));
        c.add(new Square(x -6, y +5, s));
        c.add(new Square(x -5, y +5, s));

        c.add(new Square(x -4, y +4, s));
        c.add(new Square(x -3, y +4, s));
        c.add(new Square(x -4, y +5, s));
        c.add(new Square(x -3, y +5, s));

        c.add(new Square(x -2, y +4, s));
        c.add(new Square(x -1, y +4, s));
        c.add(new Square(x -2, y +5, s));
        c.add(new Square(x -1, y +5, s));

        c.add(new Square(x +2, y +4, s));
        c.add(new Square(x +3, y +4, s));
        c.add(new Square(x +2, y +5, s));
        c.add(new Square(x +3, y +5, s));

        c.add(new Square(x +4, y +4, s));
        c.add(new Square(x +5, y +4, s));
        c.add(new Square(x +4, y +5, s));
        c.add(new Square(x +5, y +5, s));

        c.add(new Square(x +6, y +4, s));
        c.add(new Square(x +7, y +4, s));
        c.add(new Square(x +6, y +5, s));
        c.add(new Square(x +7, y +5, s));

        // y + 3
        c.add(new Square(x -2, y +6, s));
        c.add(new Square(x -1, y +6, s));
        c.add(new Square(x -2, y +7, s));
        c.add(new Square(x -1, y +7, s));

        c.add(new Square(x +2, y +6, s));
        c.add(new Square(x +3, y +6, s));
        c.add(new Square(x +2, y +7, s));
        c.add(new Square(x +3, y +7, s));

        // y + 4
        c.add(new Square(x -2, y +8, s));
        c.add(new Square(x -1, y +8, s));
        c.add(new Square(x -2, y +9, s));
        c.add(new Square(x -1, y +9, s));

        c.add(new Square(x, y +8, s));
        c.add(new Square(x +1, y +8, s));
        c.add(new Square(x, y +9, s));
        c.add(new Square(x +1, y +9, s));

        c.add(new Square(x +2, y +8, s));
        c.add(new Square(x +3, y +8, s));
        c.add(new Square(x +2, y +9, s));
        c.add(new Square(x +3, y +9, s));

        // y + 5
        c.add(new Square(x -2, y +10, s));
        c.add(new Square(x -1, y +10, s));
        c.add(new Square(x -2, y +11, s));
        c.add(new Square(x -1, y +11, s));

        c.add(new Square(x, y +10, s));
        c.add(new Square(x +1, y +10, s));
        c.add(new Square(x, y +11, s));
        c.add(new Square(x +1, y +11, s));

        c.add(new Square(x +2, y +10, s));
        c.add(new Square(x +3, y +10, s));
        c.add(new Square(x +2, y +11, s));
        c.add(new Square(x +3, y +11, s));

        return c;
    }
    
    
    @Override
    public synchronized void shoot() {
		Square centre = this.getSquareComposite().getCenterSquare();
    	if (centre.getPosX() < Board.getMyBoard().getWidth() && centre.getPosY()+16 < Board.getMyBoard().getHeight()) {
    		ShotStrategy shot = new DiamondStrategy();
    		Shot newShot = new Shot(shot, centre.getPosX(), centre.getPosY()+16);
    		newShot.startMoving("down");
    		getShots().add(newShot);
    	}
    }
    
    public void ChangeSquaresState() {
        super.changeSquaresState();
    }

    public void moveDown() {
        super.moveDown();
    }

    public SquareComposite getSquareComposite() {
        return super.getSquareComposite();
    }  

    public void turnSquaresToEmpty() {
        super.turnSquaresToEmpty();
    }  

    public void destroy() {
    	if (this.life<=0) {
    		getSquareComposite().destroy();
    	}
    }

    public int getBottomY() {
        return super.getBottomY();
    }

    public boolean containsSquare(int x, int y) {
        return super.containsSquare(x, y);
    }
    
    public void reduceLife() {
    	this.life--;
    }
    
    public Integer getRemainingLife() {
    	return this.life;
    }
    
    @Override
    public boolean isItDead() {
    	return this.life <= 0;
    }
    
    public void move(int x, int y) {
    	this.getSquareComposite().move(x, y);
    }

    @Override
    public boolean isAFinalBoss() {
        return true;
    }
}