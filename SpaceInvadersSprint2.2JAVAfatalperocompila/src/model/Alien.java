package model;

import java.util.ArrayList;

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
        // Tomamos copia de los hijos actuales (new Squares con posición)
        ArrayList<Component> original = new ArrayList<>(squares.getSquares());
        // Vaciamos el composite
        for (Component c : original) squares.remove(c);
        // Sustituimos por las casillas reales del board y las ponemos a AlienState
        AlienState s = new AlienState();
        for (Component c : original) {
            Square sq = (Square) c;
            Square boardSq = Board.getMyBoard().getSquare(sq.getPosX(), sq.getPosY());
            boardSq.changeState(s);
            squares.add(boardSq);
        }
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
    
    public void turnSquaresToEmpty() {
    	this.squares.turnEmpty();		
    }

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

    public boolean containsSquare(int x, int y) {
        for (Component c : squares.getSquares()) {
            Square s = (Square) c;
            if (s.getPosX() == x && s.getPosY() == y) {
                return true;
            }
        }
        return false;
    }
}
