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
    
    public void changeSquaresState() {
    	for (int i=0; i<squares.getSquares().size(); i++) {		// al crearse el alien en una posicion correcta, 
        	Component sq=squares.getSquares().get(i);			//  cambia el estado de las casillas que ocupa
        	((Square)sq).changeState(new AlienState());
        }
    }

    private SquareComposite makeShape(int x, int y) {
        SquareComposite c = new SquareComposite();
        AlienState s = new AlienState();
        c.add(new Square(x,     y - 1, s)); // top
    	Board.getMyBoard().getSquare(x, y - 1).changeState(s);
        c.add(new Square(x - 1, y,     s)); // mid-left
    	Board.getMyBoard().getSquare(x-1, y).changeState(s);
        c.add(new Square(x,     y,     s)); // mid-center
    	Board.getMyBoard().getSquare(x, y).changeState(s);
        c.add(new Square(x + 1, y,     s)); // mid-right
    	Board.getMyBoard().getSquare(x+1, y).changeState(s);
        c.add(new Square(x - 1, y + 1, s)); // bot-left
    	Board.getMyBoard().getSquare(x-1, y + 1).changeState(s);
        c.add(new Square(x + 1, y + 1, s)); // bot-right
    	Board.getMyBoard().getSquare(x+1, y +1).changeState(s);
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
