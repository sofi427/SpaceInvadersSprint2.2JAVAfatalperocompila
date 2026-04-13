package model;

import model.composite.Square;
import model.composite.SquareComposite;
import model.strategy.ShotStrategy;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Shot - uses Strategy pattern for shape, Composite for multi-pixel body.
 */
public class Shot {
    private ShotStrategy strategy;
    private SquareComposite squares;
    private Timer timer;
    private boolean active;
    private final Board board;

    public Shot(ShotStrategy strategy, int startX, int startY, Board board) {
        this.strategy = strategy;
        this.squares = strategy.buildShape(startX, startY);
        this.board = board;
        this.active = true;
    }

    public void startMoving() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (active) {
                    moveUp();
                }
            }
        }, 0, 50);
    }

    private void moveUp() {
        List<Square> sqs = squares.getSquares();
        // Check bounds
        for (Square sq : sqs) {
            if (sq.getPosY() - 1 < 0) {
                destroy();
                return;
            }
        }
        squares.move(0, -1);
        board.onShotMoved(this);
    }

    public void destroy() {
        active = false;
        if (timer != null) timer.cancel();
    }

    public boolean isActive() { return active; }
    public SquareComposite getSquares() { return squares; }
    public ShotStrategy getStrategy() { return strategy; }
    public void setStrategy(ShotStrategy strategy) { this.strategy = strategy; }
}
