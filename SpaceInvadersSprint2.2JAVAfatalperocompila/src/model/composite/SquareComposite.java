package model.composite;

import java.util.ArrayList;
import model.AlienGroup;
import model.Board;
import model.state.EmptyState;
import model.state.SquareState;


public class SquareComposite implements Component {

    @SuppressWarnings("FieldMayBeFinal")
    private ArrayList<Component> children; 


    public SquareComposite() {
        this.children = new ArrayList<>();
    }

    public void add(Component c) {
        children.add(c);
    } 

    public void remove(Component c) {
        children.remove(c);
    }


    
    @Override
    public void move(int dx, int dy) {
        
ArrayList<Component> current = new ArrayList<>(this.children);

    ArrayList<Square> target = new ArrayList<>();
    ArrayList<SquareState> originStates = new ArrayList<>();
    ArrayList<String> results = new ArrayList<>();

    for (Component c : current) {
        Square from = (Square) c;

        int nx = from.getPosX() + dx;
        int ny = from.getPosY() + dy;

        if (nx < 0 || nx >= Board.getMyBoard().getWidth()
                || ny < 0 || ny >= Board.getMyBoard().getHeight()) {
            return;
        }

        Square dest = Board.getMyBoard().getSquare(nx, ny);

        SquareState originState = from.getState();

        SquareState effectiveTargetState =
                isInCurrentByPosition(current, dest) ? new EmptyState() : dest.getState();

        String result = originState.collideWith(effectiveTargetState);

        if ("notmove".equals(result)) {
            return;
        }

        if (target.contains(dest)) {
            return;
        }

        target.add(dest);
        originStates.add(originState);
        results.add(result);
        }

        for (Component c : current) {
            ((Square) c).changeState(new EmptyState());
        }

        for (int i = 0; i < target.size(); i++) {
            Square dest = target.get(i);

            switch (results.get(i)) {
                case "move" -> dest.changeState(originStates.get(i));
                case "destroyboth" -> AlienGroup.getAlienGroup().removeAlienAt(dest.getPosX(), dest.getPosY());
                default -> { /* si llega algo raro, no hacemos nada */ }
            }
        }

        this.children.clear();
        for (int i = 0; i < target.size(); i++) {
            if (!"destroyboth".equals(results.get(i))) {
                this.children.add(target.get(i)); // Square es Component
            }
        }
    }

    private boolean isInCurrentByPosition(ArrayList<Component> current, Square dest) {
        for (Component c : current) {
            Square s = (Square) c;
            if (s.getPosX() == dest.getPosX() && s.getPosY() == dest.getPosY()) {
                return true;
            }
        }
        return false;
}


    public ArrayList<Component> getSquares() {
        return children;
    }


    public Square getCenterSquare() { // El método creo que está hecho pero cuidado porque alomejor al moverse no se actualiza

        int maxX = -1;
        int maxY = -1;
        int minX = -1;
        int minY = -1;

        for (Component c : children) {
            Square actSquare = (Square) c;
            int actX = actSquare.getPosX();
            int actY = actSquare.getPosY();
            if(maxX < actX || maxX == -1) { maxX = actX; }
            if(maxY < actY || maxY == -1) { maxY = actY; }
            if(minX > actX || minX== -1) { minX = actX; }
            if(minY > actY || minY == -1) { minY = actY;}
        }

        return Board.getMyBoard().getSquare((maxX+minX)/2, (maxY+minY)/2);
    }

    public void turnEmpty() {
        for (Component c : children) {
            Square s = (Square) c;
            s.changeState(new EmptyState());
        }
    }
}
