package spaceinvaders.model.composite;

import java.util.List;

/**
 * Component interface - Composite Pattern
 * Both Square (leaf) and SquareComposite implement this.
 */
public interface Component {
    List<Square> getSquares();
    void move(int dx, int dy);
}
