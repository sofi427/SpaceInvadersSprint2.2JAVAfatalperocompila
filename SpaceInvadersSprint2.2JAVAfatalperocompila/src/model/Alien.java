package model;

import model.composite.Component;
import model.composite.Square;
import model.composite.SquareComposite;
import model.state.AlienState;

public class Alien {

    private final SquareComposite squares;

    public Alien(int centerX, int centerY) {
        this.squares = makeShape(centerX, centerY);
    }

    private SquareComposite makeShape(int x, int y) {
        SquareComposite c = new SquareComposite();
        AlienState s = new AlienState();
        c.add(new Square(x,     y - 1, s)); // top
        c.add(new Square(x - 1, y,     s)); // mid-left
        c.add(new Square(x,     y,     s)); // mid-center
        c.add(new Square(x + 1, y,     s)); // mid-right
        c.add(new Square(x - 1, y + 1, s)); // bot-left
        c.add(new Square(x + 1, y + 1, s)); // bot-right
        return c;
    }
    
    public void moveDown() {
    	squares.move(0, 1);
	}
    
    public SquareComposite getSquareComposite() { return squares; }

    public int getBottomY() {
        int maxY = 0;
        for (Component squ : squares.getSquares()) {
        	Square sq = (Square) squ;
            if (sq.getPosY() > maxY) {
                maxY = sq.getPosY();
            }
        }
        return maxY;
    }
}
