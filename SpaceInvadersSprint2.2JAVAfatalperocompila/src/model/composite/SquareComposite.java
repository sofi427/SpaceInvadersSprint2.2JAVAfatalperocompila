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


    @Override //mario te he cambiado las instancias x los states
    public void move(int dx, int dy) {
        
        for (int i = 0; i < children.size(); i++) {
            Square from = (Square) children.get(i);
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
}
