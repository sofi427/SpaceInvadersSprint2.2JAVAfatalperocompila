package model.state;

import java.awt.Color;

//Metodos para cada uno de los estados de la casilla:

import java.awt.Color;

public interface SquareState {
    Color  getColor();
    boolean isEmpty();
    String getStateS();
    String collideWith(SquareState other); // devuelve: "move", "notmove", "destroyboth"
}
