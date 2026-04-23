package model.composite;

import java.util.ArrayList;

import model.state.AlienState;
import model.state.EmptyState;
import model.state.PlayerState;
import model.state.ShotState;
import model.state.SquareState;


import model.Board;


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


    @Override
    public void move(int dx, int dy) {
        
        for (Component comp : children) {
            Square from = (Square) comp; 
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

            // Colisión: Alien toca Player muere Player y el Alien ocupa la casilla
            if (originState instanceof AlienState && targetState instanceof PlayerState) {
                System.out.println("Player muerto");
                from.setState(new EmptyState());
                comp.setSquare(to);
                to.setState(originState);
                Board.getMyBoard().gameLost();
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

    public ArrayList<Component> getSquares() {
        return children;
    }
}
