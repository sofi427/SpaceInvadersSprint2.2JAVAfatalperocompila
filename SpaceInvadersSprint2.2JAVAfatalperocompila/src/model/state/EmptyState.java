package model.state;

import java.awt.Color;

public class EmptyState implements SquareState {

   public Color   getColor()  { return Color.BLACK; }
   public boolean isEmpty()   { return true; }
   public String  getStateS() { return "Empty"; }

   public String collideWith(SquareState other) {
	return "notmove"; //por completar y que devuelva algo, una casilla vacia nunca se mueve}
  }
}