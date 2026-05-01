package model.strategy;

import model.Alien;
import model.composite.SquareComposite;

public class NormalAlien extends Alien {

    public NormalAlien(int centerX, int centerY) {
        super(centerX, centerY);
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
        super.destroy();
    }

    public int getBottomY() {
        return super.getBottomY();
    }

    public boolean containsSquare(int x, int y) {
        return super.containsSquare(x, y);
    }
}

