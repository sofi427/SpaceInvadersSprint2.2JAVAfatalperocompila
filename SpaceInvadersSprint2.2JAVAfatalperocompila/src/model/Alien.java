package model;

import java.util.ArrayList;
import model.composite.Component;
import model.composite.Square;
import model.composite.SquareComposite;
import model.state.AlienState;
import model.strategy.PixelStrategy;
import model.strategy.ShotStrategy;

public abstract class Alien {

    protected SquareComposite squares;
    protected ArrayList<Shot> shots = new ArrayList<Shot>();

    protected Alien(int centerX, int centerY) {
        this.squares = makeShape(centerX, centerY);
    }

    protected void changeSquaresState() {
        // Tomamos copia de los hijos actuales (new Squares con posici�n)
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
    
    protected abstract SquareComposite makeShape(int x, int y);
   
    protected void moveDown() {
    	squares.move(0, 1);
	}
    
    public SquareComposite getSquareComposite() { return squares; }
    
    protected void turnSquaresToEmpty() {
    	this.squares.turnEmpty();		
    }
    
    /*protected void destroy() {
        squares.getSquares().clear();
    }*/
    public abstract void destroy();

    protected int getBottomY() {
        int maxY = 0;
        for (Component squ : squares.getSquares()) {
        	Square sq = (Square) squ;
            if (sq.getPosY() > maxY) {
                maxY = sq.getPosY();
            }
        }
        return maxY;
    }

    public  boolean containsSquare(int x, int y) {
        for (Component c : squares.getSquares()) {
            Square s = (Square) c;
            if (s.getPosX() == x && s.getPosY() == y) {
                return true;
            }
        }
        return false;
    }
    
    public void shoot() {
		Square centre = this.squares.getCenterSquare();
		if (centre.getPosX() < Board.getMyBoard().getWidth() && centre.getPosY()+3 < Board.getMyBoard().getHeight()) {
			ShotStrategy shot = new PixelStrategy();
			Shot newShot = new Shot(shot, centre.getPosX(), centre.getPosY()+3);
			newShot.startMoving("down");
			shots.add(newShot);
		}
    }

	public abstract void reduceLife();

	protected abstract Integer getRemainingLife();

	protected abstract void move(int x, int y);

    public abstract boolean isItDead();

    public boolean isAFinalBoss() {
        return false;
    }
    
    
    
}