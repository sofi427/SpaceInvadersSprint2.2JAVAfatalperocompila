package model.composite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.state.EmptyState;
import model.state.SquareState;

public class Square implements Component {
    private int posX;
    private int posY;
    private SquareState state;
    
    //Se añade un estado vacio de casilla(predeterminado)
    public Square(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
        this.state = new EmptyState();
    }
    //Se añade un estado concreto de casilla (state)
    public Square(int posX, int posY, SquareState state) {
        this.posX = posX;
        this.posY = posY;
        this.state = state;
    }
    
    //Metodos necesarios para otras clases (cambiar estado de la casilla, get la posicion, saber el estado mediante una string...)
    public SquareState getState() { return state; }
    public void setState(SquareState state) { this.state = state; }
    public int getPosX() { return posX; }
    public int getPosY() { return posY; }
    public String getStateString() { return this.state.getStateS();}
    
    //Metodos necesarios para el movimiento
    public ArrayList<Square> getSquares() {
        ArrayList<Square> list = new ArrayList<>();
        list.add(this);
        return list;
    }
    
    public void move(int dx, int dy) {
        this.posX += dx;
        this.posY += dy;
    }
    //Devuelve lista vacia porque Square no tiene hijos(implementado debido a el requerimiento de la interfaz component)
    public List<Component> getChildren() {
        return Collections.emptyList();
    }
}
