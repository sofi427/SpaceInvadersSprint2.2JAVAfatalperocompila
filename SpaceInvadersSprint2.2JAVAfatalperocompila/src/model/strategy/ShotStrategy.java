package model.strategy;

import model.composite.SquareComposite;

public interface ShotStrategy {

    public SquareComposite buildShape(int originX, int originY);
    public int getMaxShots();
    public String getName();
    public int getRemainingShots();
    public void consumeShot();
}
