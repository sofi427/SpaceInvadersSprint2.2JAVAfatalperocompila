package model;

import java.util.Timer;
import java.util.TimerTask;
import model.composite.Square;
import model.composite.SquareComposite;
import model.state.AlienState;

public class Alien {

    private final SquareComposite squares;
    private Timer timer;

    public Alien(int centerX, int centerY) {
        this.squares = makeShape(centerX, centerY);
        moveEvery350ms();	//segun se crea, empieza a bajar automaticamente
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

    private void moveEvery350ms()
	{
		timer = new Timer();
		
		timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                moveDown();
            }
        }, 0, 350);

	}
    
    private void moveDown() {
    	squares.move(0, 1);
	}

    public void stopTimer() {
        timer.cancel();
    }
    
    public SquareComposite getSquares() { return squares; }

    public int getBottomY() {
        int maxY = 0;
        for (Square sq : squares.getSquares()) {
            if (sq.getPosY() > maxY) {
                maxY = sq.getPosY();
            }
        }
        return maxY;
    }
}
