package model.state;

import java.awt.Color;

public class EmptyState implements SquareState {

   public Color   getColor()  { return Color.BLACK; }
   public boolean isEmpty()   { return true; }
   public String  getStateS() { return "Empty"; }

   public void collideWith(SquareState coli) { }
   public void collideWithAlien()  { }
   public void collideWithShot()   { }
   public void collideWithPlayer() { }
}
