package model.composite;

import java.util.ArrayList;
import java.util.List;
import model.Board;


public class SquareComposite implements Component {

    private ArrayList<Component> children; 

    public SquareComposite() {
        this.children = new ArrayList<>();
    }

    public void add(Component c) {
        children.add(c);
    }


    @Override
    public void move(int dx, int dy) {
        
        for (java.util.Iterator<Component> it = children.iterator(); it.hasNext(); ) {
            Component comp = it.next();

            Square from = comp.getSquare();
            int nx = from.getPosX() + dx;
            int ny = from.getPosY() + dy;

            // Si sale del tablero no se mueve
            if (Board.getMyBoard().isInside(nx, ny)) {
                System.out.println("Se ha salido del tablero");
                return;
            }

            Square to = Board.getMyBoard().getSquare(nx, ny);

            SquareState originState = from.getState();
            SquareState targetState = to.getState();

            // --- Colisión: Alien toca Player muere Player y el Alien ocupa la casilla
            if (originState instanceof AlienState && targetState instanceof PlayerState) {
                System.out.println("Player muerto");
                from.setState(new EmptyState());
                comp.setSquare(to);
                to.setState(originState);
                continue;
            }

            if (originState instanceof ShotState && targetState instanceof AlienState) {
                System.out.println("Alien eliminado");
                // El disparo se consume y el alien desaparece del board
                from.setState(new EmptyState());
                to.setState(new EmptyState());
                // El componente que se movía (el shot) ya no existe
                // No sé cómo eliminar el disparo jeje
                continue;
            }

            // Demás combinaciones: si no está vacío, bloquea (no hay movimiento)
            if (!(targetState instanceof EmptyState)) {
                continue;
            }

            from.setState(new EmptyState());
            comp.setSquare(to);
            to.setState(originState);
        }
    }
}
