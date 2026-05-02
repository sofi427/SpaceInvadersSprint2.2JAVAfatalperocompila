package model;

import model.composite.Square;
import model.composite.SquareComposite;
import model.state.AlienState;

public class NormalAlien extends Alien {

    public NormalAlien(int centerX, int centerY) {
        super(centerX, centerY);
    }
    
    @Override
    protected SquareComposite makeShape(int x, int y) {
        SquareComposite c = new SquareComposite();
        AlienState s = new AlienState();
        c.add(new Square(x - 2, y - 2, s));
        c.add(new Square(x - 1, y - 1, s));
        c.add(new Square(x + 1, y - 1, s));
        c.add(new Square(x + 2, y - 2, s));
        c.add(new Square(x - 2, y,     s));
        c.add(new Square(x,     y,     s));
        c.add(new Square(x + 2, y,     s));
        c.add(new Square(x - 2, y + 1, s));
        c.add(new Square(x - 1, y + 1, s));
        c.add(new Square(x - 1, y + 2, s));
        c.add(new Square(x,     y + 1, s));
        c.add(new Square(x + 1, y + 1, s));
        c.add(new Square(x + 2, y + 1, s));
        c.add(new Square(x + 1, y + 2, s));
        return c;
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
        squares.getSquares().clear();
    }

    public int getBottomY() {
        return super.getBottomY();
    }

    public boolean containsSquare(int x, int y) {
        return super.containsSquare(x, y);
    }

	@Override
	protected void reduceLife() {
	}

	@Override
	protected Integer getRemainingLife() {
		return null;
	}

	@Override
	protected void move(int x, int y) {
	}
}

