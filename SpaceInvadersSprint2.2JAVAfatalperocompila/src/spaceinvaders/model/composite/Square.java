package spaceinvaders.model.composite;

import spaceinvaders.model.state.SquareState;
import spaceinvaders.model.state.EmptyState;

import java.util.Collections;
import java.util.List;

/**
 * Square - Leaf in the Composite Pattern, also uses State Pattern.
 */
public class Square implements Component {
    private int posX;
    private int posY;
    private SquareState state;

    public Square(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
        this.state = new EmptyState();
    }

    public Square(int posX, int posY, SquareState state) {
        this.posX = posX;
        this.posY = posY;
        this.state = state;
    }

    public int getPosX() { return posX; }
    public int getPosY() { return posY; }
    public SquareState getState() { return state; }
    public void setState(SquareState state) { this.state = state; }

    @Override
    public List<Square> getSquares() {
        return Collections.singletonList(this);
    }

    @Override
    public void move(int dx, int dy) {
        this.posX += dx;
        this.posY += dy;
    }
}
