package model.state;

import java.awt.Color;
import model.composite.Square;
import model.player.AbstractPlayer;

//Metodos para cada uno de los estados de la casilla:
public interface SquareState {
    String move = "MOVE";
    String block = "BLOCK";
    String destroyboth = "DESTROY_BOTH";
    String gamelost = "GAME_LOST";

    Color getColor(); //Saber su color
    boolean isEmpty(); //Si es la casilla vacia
    String getStateS(); //Saber su estado por una string
  //Colisiones con otras casillas
    String collideWith(SquareState coli); 
    String collideWithAlien();
    String collideWithShot();
    String collideWithPlayer();
}
