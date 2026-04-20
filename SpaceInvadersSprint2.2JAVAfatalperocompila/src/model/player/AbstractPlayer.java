package model.player;

import model.Board;
import model.composite.Square;
import model.composite.SquareComposite;
import model.strategy.ShotStrategy;

import java.util.ArrayList;
import java.util.Observable;

@SuppressWarnings("deprecation")
public abstract class AbstractPlayer extends Observable{

	//atributos
	private static AbstractPlayer myPlayer;
    private SquareComposite squares;
    private ShotStrategy currentStrategy;
    private ArrayList<ShotStrategy> strategyList;
    private int strategyIndex;
    private boolean gameLost;

    //constructora
    protected AbstractPlayer(int centerX, int centerY) {
        this.squares = makeShape(centerX, centerY);
        this.strategyList = createStrategyList();
        this.strategyIndex = 0;
        this.currentStrategy = strategyList.get(0);
    }

    protected abstract AbstractPlayer getPlayer(int centerX, int centerY);
    protected abstract SquareComposite makeShape(int x, int y);
    protected abstract ArrayList<ShotStrategy> createStrategyList();
    public abstract String getType();

    //movimientos
    public void moveLeft() { squares.move(-1,  0); }
    public void moveRight() { squares.move( 1,  0); }
    public void moveUp() { squares.move( 0, -1); }
    public void moveDown() { squares.move( 0,  1); }

    
    //metodos de disparo
    
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
    
    public void shoot() {
    	
    }

 //Para registrar las casills de player en board
    public void registerOnBoard() {
        ArrayList<Square> mySquares = squares.getSquares();
        squares = new SquareComposite();
        
        for (Square sq : mySquares) {
            // Obtener el square real del Board
            Square boardSquare = Board.getMyBoard().getSquare(sq.getPosX(), sq.getPosY());
            // Copiarle el estado
            boardSquare.setState(sq.getState());
            // Añadir el square del Board al composite (no el privado)
            squares.add(boardSquare);
        }
    }
    
    //getters
    public ShotStrategy getCurrentStrategy() { return currentStrategy; }
    public SquareComposite getSquares() { return squares; }
}
