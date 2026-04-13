package spaceinvaders.model.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * SquareComposite - Composite in the Composite Pattern.
 * Represents multi-pixel shapes (player ship, alien, shot shapes).
 */
public class SquareComposite implements Component {
    private final List<Component> children = new ArrayList<>();

    public void add(Component component) {
        children.add(component);
    }

    public void remove(Component component) {
        children.remove(component);
    }

    public List<Component> getChildren() {
        return children;
    }

    @Override
    public List<Square> getSquares() {
        List<Square> squares = new ArrayList<>();
        for (Component child : children) {
            squares.addAll(child.getSquares());
        }
        return squares;
    }

    @Override
    public void move(int dx, int dy) {
        for (Component child : children) {
            child.move(dx, dy);
        }
    }
}
