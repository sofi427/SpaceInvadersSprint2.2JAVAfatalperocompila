package model.composite;

import java.util.ArrayList;
import java.util.List;
import model.Board;
import model.state.EmptyState;
import model.state.SquareState;

public class SquareComposite implements Component {

    private ArrayList<Component> children; 

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
        ArrayList<Square> target = new ArrayList<>();

        for (Square s : current) {
            int nx = s.getPosX() + dx;
            int ny = s.getPosY() + dy;

            if (nx < 0 || nx >= Board.getMyBoard().getWidth()
             || ny < 0 || ny >= Board.getMyBoard().getHeight()) return;

            Square dest = Board.getMyBoard().getSquare(nx, ny);
            if (!dest.getState().isEmpty()) {
                boolean isOwnSquare = false;
                for (Square currentSquare : current) {
                    if (currentSquare.getPosX() == nx && currentSquare.getPosY() == ny) {
                        isOwnSquare = true;
                        break;
                    }
                }
                if (!isOwnSquare) return;
            }
            target.add(dest);
        }

        SquareState shipState = current.get(0).getState();

        // Limpiar squares del Board
        for (Square s : current) {
            Board.getMyBoard().getSquare(s.getPosX(), s.getPosY()).setState(new EmptyState());
        }

        // Pintar destinos en el Board
        for (Square s : target) {
            s.setState(shipState);
        }

        // Actualizar referencias internas del composite
        children.clear();
        for (Square s : target) {
            children.add(s);
        }
    }

    @Override
    public List<Component> getChildren() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
