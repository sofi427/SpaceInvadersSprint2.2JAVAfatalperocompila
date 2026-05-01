package model;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import model.composite.Component;
import model.composite.Square;
import model.composite.SquareComposite;
import model.state.ShotState;
import model.strategy.ShotStrategy;

public class Shot {
    private ShotStrategy strategy;
    @SuppressWarnings("FieldMayBeFinal")
    private SquareComposite squares;
    private Timer timer;
    private boolean active;

    
    public Shot(ShotStrategy strategy, int startX, int startY) {
        this.strategy = strategy;
        this.squares = strategy.buildShape(startX, startY);
        this.active = true;
        for (int i=0; i<squares.getSquares().size(); i++) {		// al crearse el disparo cambia el estado de las casillas que ocupa
        	Component sq=squares.getSquares().get(i);
        	((Square)sq).changeState(new ShotState());
        }
        registerOnBoard();
    }

    public ShotStrategy getStrategy(){ 
    	return strategy; 
    }
    
    public void changeStrategy(ShotStrategy strategy){ 
    	this.strategy = strategy; 
    }
    
    public SquareComposite getSquares(){ 
    	return squares; 
    }
    
    public void startMoving(String dir) {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (active) {
                    move(dir);
                }
            }
        }, 0, 50);
    }

    private void move(String dir) {
        for (int i=0; i<squares.getSquares().size(); i++) {
        	Component sq=squares.getSquares().get(i);
            if (((Square) sq).getPosY()-1 < 0 || ((Square) sq).getPosY()+1 > 59 || squares.getSquares().isEmpty()) {
                destroyShot();
                return;
            }
        }
        if (dir.equals("up")) {
            squares.move(0, -1);
        } else {
            squares.move(0, 1);
        }
    }

    public void destroyShot() {
        active = false;
        squares.destroy();
        if (timer != null) {
        	timer.cancel();
        	timer= null;
        }
    }

    public boolean isActive(){ 
    	return active; 
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
}
 
