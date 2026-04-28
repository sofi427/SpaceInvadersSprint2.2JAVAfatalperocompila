package model.composite;

import java.util.ArrayList;
import model.state.EmptyState;
import model.state.SquareState;

public class Square implements Component {
    private int posX;
    private int posY;
    private SquareState state;
    
    //Se a�ade un estado vacio de casilla(predeterminado)
    public Square(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
        this.state = new EmptyState();
    }
    //Se a�ade un estado concreto de casilla (state)
    public Square(int posX, int posY, SquareState state) {
        this.posX = posX;
        this.posY = posY;
        this.state = state;
    }
    
    //Metodos necesarios para otras clases (cambiar estado de la casilla, get la posicion, saber el estado mediante una string...)
    public SquareState getState() { return state; }
    public void changeState(Object state) { this.state = (SquareState) state; }
    public int getPosX() { return posX; }
    public int getPosY() { return posY; }
    public String getStateString() { return this.state.getStateS();}
    
    //Metodos necesarios para el movimiento
    public ArrayList<Square> getSquares() {
        ArrayList<Square> list = new ArrayList<>();
        list.add(this);
        return list;
    }
    
    @Override
    public void move(int dx, int dy) {
        this.posX += dx;
        this.posY += dy;
    }
   
}
