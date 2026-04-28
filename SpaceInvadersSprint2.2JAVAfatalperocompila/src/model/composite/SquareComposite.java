package model.composite;

import java.util.ArrayList;
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

    public void remove(Component c) {
        children.remove(c);
    }


    @Override //mario te he cambiado las instancias x los states
    public void move(int dx, int dy) {
        
        for (int i = 0; i < children.size(); i++) {
            Square from = (Square) children.get(i);
            int nx = from.getPosX() + dx;
            int ny = from.getPosY() + dy;

            // Si sale del tablero no se mueve
            if (!Board.getMyBoard().isInside(nx, ny)) {
                System.out.println("Se ha salido del tablero");
                return;
            }

            Square to = Board.getMyBoard().getSquare(nx, ny);

            SquareState originState = from.getState();
            SquareState targetState = to.getState();
            String result = originState.collideWith(targetState);

            // Colisión: Alien toca Player muere Player y el Alien ocupa la casilla
            if (result.equals(SquareState.move)) {                
            	System.out.println("Player muerto");
                from.setState(new EmptyState());
                children.set(i, to);
                to.setState(originState);
                continue;
            }

            if (result.equals(SquareState.destroyboth)) {
                System.out.println("Alien eliminado");
                // El disparo se consume y el alien desaparece del board
                from.setState(new EmptyState());
                to.setState(new EmptyState());
                // El componente que se movía (el shot) ya no existe
                // No sé cómo eliminar el disparo jeje
                return;
            }

            // Demás combinaciones: si no está vacío, bloquea (no hay movimiento)
            if (result.equals(SquareState.gamelost)) {
            	from.setState(new EmptyState());
                to.setState(originState);
                Board.getMyBoard().gameLost();
                return;
            }

            

        }
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
}
