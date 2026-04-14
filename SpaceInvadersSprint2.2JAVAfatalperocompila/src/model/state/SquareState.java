package model.state;

import java.awt.Color;

//Metodos para cada uno de los estados de la casilla:
public interface SquareState {
    Color getColor(); //Saber su color
    boolean isEmpty(); //Si es la casilla vacía
    String getStateS(); //Saber su estado por una string
    
  //Colisiones con otras casillas
    String collideWith(SquareState coli); 
    String collideWithAlien();
    String collideWithShot();
    String collideWithPlayer();
}
