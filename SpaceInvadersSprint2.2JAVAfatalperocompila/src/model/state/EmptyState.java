package model.state;

import java.awt.Color;

public class EmptyState implements SquareState {

   public Color   getColor()  { return Color.BLACK; }
   public boolean isEmpty()   { return true; }
   public String  getStateS() { return "Empty"; }

   public String collideWith(SquareState coli) { return block; }
   public String collideWithAlien()  { return move; }
   public String collideWithShot()   { return move; }
   public String collideWithPlayer() { return move; }
}