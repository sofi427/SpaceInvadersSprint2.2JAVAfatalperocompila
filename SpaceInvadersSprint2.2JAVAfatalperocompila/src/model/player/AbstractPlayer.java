package model.player;

import model.Board;
import model.Shot;
import model.composite.Square;
import model.composite.SquareComposite;
import model.strategy.ShotStrategy;

import java.awt.Color;
import model.composite.Component; 
import java.util.ArrayList;

public abstract class AbstractPlayer{

	//atributos
    private static AbstractPlayer instance;

    private SquareComposite squares;
    private ShotStrategy currentStrategy;
    @SuppressWarnings("FieldMayBeFinal")
    private ArrayList<ShotStrategy> strategyList;
    private int strategyIndex;
    @SuppressWarnings("FieldMayBeFinal")
    private ArrayList<Shot> shots;

    //constructora
    @SuppressWarnings("Convert2Diamond")
    protected AbstractPlayer(int centerX, int centerY) {
    	instance = this; 
        this.squares = makeShape(centerX, centerY);
        this.strategyList = createStrategyList();
        this.strategyIndex = 0;
        this.currentStrategy = strategyList.get(0);
        this.shots = new ArrayList<Shot>();
    }

    public static AbstractPlayer getPlayer() { 
    	return instance; } //si que es static sofinu
    public abstract Color getColor();
    protected abstract SquareComposite makeShape(int x, int y);
    protected abstract ArrayList<ShotStrategy> createStrategyList();
    public abstract String getType();

    //movimientos
    public void moveLeft() {
    	this.squares.move( -1,  0);

    }
    public void moveRight() {
    	this.squares.move( 1,  0);
    }
    public void moveUp() {
    	squares.move( 0, -1);
    }
    public void moveDown() {
    	this.squares.move( 0,  1);

    }
    
    //metodos de disparo
    public void shoot() {
    	if (canShootCurrentStrategy())
    	{
    		ShotStrategy newShotStrategy = strategyList.get(strategyIndex);
    		Square centre = this.squares.getCenterSquare();
    		if (centre.getPosX() < Board.getMyBoard().getWidth() && centre.getPosY()+3 < Board.getMyBoard().getHeight()) {
    			Shot newShot = new Shot(newShotStrategy, centre.getPosX(), centre.getPosY()-2);
    			newShot.startMoving("up");
    			this.shots.add(newShot);
    			this.consumeShot();
    		}
    	}
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
    
    // aniadido para que Board.StopGame() pueda detener los timers de todos los disparos activos y limpiar sus casillas del board
   public void stopAllShots() {
       for (Shot shot : shots) {
           if (shot.isActive()) {
               shot.destroyShot();
           }
       }
       shots.clear();
   }
   
   public abstract void clearPlayer();
   

 //Para registrar las casillas de player en board
    public void registerOnBoard() {
    	ArrayList<Component> original = new ArrayList<>(squares.getSquares()); //es una copia de las casillas
        SquareComposite boardSquares = new SquareComposite();
        for (Component c : original) {
            Square sq = (Square) c;
            // Obtener el square real del Board
            Square boardSquare = Board.getMyBoard().getSquare(sq.getPosX(), sq.getPosY());
            // Copiarle el estado
            boardSquare.changeState(sq.getState());
            // Aniadir el square del Board al composite (no el privado)
            boardSquares.add(boardSquare);
        }
        squares = boardSquares;
    }
    
    public void removeShotAt(int x, int y) {
        Shot s = getShotAt(x, y);
        if (s == null) return;
        // Eliminar por completo el composite del shot
        s.destroyShot();
        shots.remove(s);
    }

    private Shot getShotAt(int x, int y) {
        for (Shot s : new ArrayList<>(shots)) {
            if (s.containsSquare(x, y)) {
                return s;
            }
        }
        return null;
    }

    
    //getters
    public ShotStrategy getCurrentStrategy() { return currentStrategy; }
    public SquareComposite getSquares() { return squares; }

	public int getRemainingShots(){
		return this.currentStrategy.getRemainingShots();
	}

	public String getShotType() {
		return this.currentStrategy.getName();
	}

	
}
