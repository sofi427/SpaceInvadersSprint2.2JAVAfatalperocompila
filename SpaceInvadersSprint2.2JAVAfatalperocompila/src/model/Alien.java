package model;

import model.composite.Square;
import model.composite.SquareComposite;
import model.state.AlienState;

/**
 * Alien - multi-pixel enemy using Composite + State patterns.
 *
 * Shape (center at cx, cy):
 *   .X.
 *   XXX
 *   X.X
 */
public class Alien {

    private final SquareComposite squares;

    public Alien(int centerX, int centerY) {
        this.squares = buildShape(centerX, centerY);
    }

    private SquareComposite buildShape(int cx, int cy) {
        SquareComposite c = new SquareComposite();
        AlienState s = new AlienState();
        c.add(new Square(cx,     cy - 1, s)); // top
        c.add(new Square(cx - 1, cy,     s)); // mid-left
        c.add(new Square(cx,     cy,     s)); // mid-center
        c.add(new Square(cx + 1, cy,     s)); // mid-right
        c.add(new Square(cx - 1, cy + 1, s)); // bot-left
        c.add(new Square(cx + 1, cy + 1, s)); // bot-right
        return c;
    }

    public void move(int dx, int dy) {
        squares.move(dx, dy);
    }

    public SquareComposite getSquares() { return squares; }

    public int getBottomY() {
        return squares.getSquares().stream()
                .mapToInt(Square::getPosY).max().orElse(0);
    }
}
