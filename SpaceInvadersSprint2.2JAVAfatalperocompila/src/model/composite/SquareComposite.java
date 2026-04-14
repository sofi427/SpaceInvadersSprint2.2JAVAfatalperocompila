package model.composite;

import java.util.ArrayList;

import model.Board;

public class SquareComposite implements Component {

    private ArrayList<Component> children; // o components, items, etc.

    public SquareComposite() {
        this.children = new ArrayList<>();
    }

    public void add(Component c) {
        children.add(c);
    }

    @Override
    public ArrayList<Square> getSquares() {
        ArrayList<Square> result = new ArrayList<>();
        for (Component c : children) {
            result.addAll(c.getSquares());
        }
        return result;
    }

    public void move(int dx, int dy) {

        ArrayList<Square> current = this.getSquares();          
        ArrayList<Component> newChildren = new ArrayList<>();

        for (Square s : current) {
            int nx = s.getPosX() + dx;
            int ny = s.getPosY() + dy;

            if (nx < 0 || nx >= Board.getMyBoard().getWidth() || ny < 0 || ny >= Board.getMyBoard().getHeight()) {
                return;
            }

            Square newSquare = Board.getMyBoard().getSquare(nx, ny);
            newChildren.add(newSquare);
        }

        this.children.clear();
        this.children.addAll(newChildren);
    }
}
