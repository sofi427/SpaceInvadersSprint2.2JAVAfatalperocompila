package model.state;

import java.awt.Color;

public class EmptyState implements SquareState {

   @Override
   public Color   getColor()  { return Color.BLACK; }
   @Override
   public boolean isEmpty()   { return true; }
   @Override
   public String  getStateS() { return "Empty"; }

   @Override
   public String collideWith(SquareState other) {
	return "move"; //por completar y que devuelva algo, una casilla vacia nunca se mueve}
  }
}