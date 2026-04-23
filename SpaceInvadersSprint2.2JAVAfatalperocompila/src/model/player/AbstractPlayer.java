package model.player;

import model.Board;
import model.Shot;
import model.composite.Square;
import model.composite.SquareComposite;
import model.strategy.ShotStrategy;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Observable;

@SuppressWarnings("deprecation")
public abstract class AbstractPlayer extends Observable{

	//atributos
    private SquareComposite squares;
    private ShotStrategy currentStrategy;
    private ArrayList<ShotStrategy> strategyList;
    private int strategyIndex;
    private ArrayList<Shot> shots;
    //private boolean gameLost;

    //constructora
    protected AbstractPlayer(int centerX, int centerY) {
        this.squares = makeShape(centerX, centerY);
        this.strategyList = createStrategyList();
        this.strategyIndex = 0;
        this.currentStrategy = strategyList.get(0);
        this.shots = new ArrayList<Shot>();
    }

    public abstract AbstractPlayer getPlayer(); //DEBERIA SER STATIC???
    public abstract Color getColor();
    protected abstract SquareComposite makeShape(int x, int y);
    protected abstract ArrayList<ShotStrategy> createStrategyList();
    public abstract String getType();

    //movimientos
    public void moveLeft() {
    	squares.move(-1,  0);
    	this.setChanged();
    	this.notifyObservers();
    }
    public void moveRight() {
    	squares.move( 1,  0);
    	this.setChanged();
    	this.notifyObservers();
    }
    public void moveUp() {
    	squares.move( 0, -1);
    	this.setChanged();
    	this.notifyObservers();
    }
    public void moveDown() {
    	squares.move( 0,  1);
    	this.setChanged();
    	this.notifyObservers();
    }

    
    //metodos de disparo
    public void shoot() {
    	if (canShootCurrentStrategy())
    	{
    		ShotStrategy newShotStrategy = strategyList.get(strategyIndex);
    		//AQUI NECESITO METODO PARA CONSEGUIR LA X,Y CENTRAS DE SQUARES, Y +2
    		Shot newShot = new Shot(newShotStrategy, 2, 2);
    		newShot.startMoving();
    		this.shots.add(newShot);
    		this.consumeShot();
    	}
    }
    
    public void shotMoved(Shot shot) {
    	this.setChanged();
    	this.notifyObservers();
    }
    
    public void nextStrategy() {
        if (strategyIndex + 1 >= strategyList.size())
        { strategyIndex = 0; }
        else
        { strategyIndex++; }
        currentStrategy = strategyList.get(strategyIndex);
    }
    
    public boolean canShootCurrentStrategy() {
        int max = currentStrategy.getMaxShots();
        if (max == -1) 
        { return true; }
        return currentStrategy.getRemainingShots() > 0;
    }

    public void consumeShot() {
        currentStrategy.consumeShot();
    }
    


 //Para registrar las casillas de player en board
    public void registerOnBoard() {
        ArrayList<Square> mySquares = squares.getSquares();
        squares = new SquareComposite();
        
        for (Square sq : mySquares) {
            // Obtener el square real del Board
            Square boardSquare = Board.getMyBoard().getSquare(sq.getPosX(), sq.getPosY());
            // Copiarle el estado
            boardSquare.setState(sq.getState());
            // A�adir el square del Board al composite (no el privado)
            squares.add(boardSquare);
        }
    }
    
    public void notifyLost() {
		this.setChanged();
    	this.notifyObservers("LOST");
	}
    
    
    //getters
    public ShotStrategy getCurrentStrategy() { return currentStrategy; }
    public SquareComposite getSquares() { return squares; }

	
}
