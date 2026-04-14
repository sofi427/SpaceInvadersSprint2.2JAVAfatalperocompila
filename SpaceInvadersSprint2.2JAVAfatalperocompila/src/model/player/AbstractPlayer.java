package model.player;

import model.composite.SquareComposite;
import model.strategy.ShotStrategy;

import java.util.ArrayList;

public abstract class AbstractPlayer {

	//atributos
    protected SquareComposite squares;
    protected ShotStrategy currentStrategy;
    protected ArrayList<ShotStrategy> strategyList;
    protected int strategyIndex;

    //constructora
    public AbstractPlayer(int centerX, int centerY) {
        this.squares = makeShape(centerX, centerY);
        this.strategyList = createStrategyList();
        this.strategyIndex = 0;
        this.currentStrategy = strategyList.get(0);
    }

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

    
    //getters
    public ShotStrategy getCurrentStrategy() { return currentStrategy; }
    public SquareComposite getSquares() { return squares; }
}
