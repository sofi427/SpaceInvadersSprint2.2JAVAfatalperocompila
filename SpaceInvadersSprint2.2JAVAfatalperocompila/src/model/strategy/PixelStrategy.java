package model.strategy;

import model.composite.Square;
import model.composite.SquareComposite;
import model.state.ShotState;

public class PixelStrategy implements ShotStrategy {

    @Override
    public SquareComposite buildShape(int originX, int originY) {
        SquareComposite composite = new SquareComposite();
        composite.add(new Square(originX, originY, new ShotState()));
        return composite;
    }

    @Override public int    getMaxShots()       { return -1; }
    @Override public String getName()            { return "Pixel"; }
    @Override public int    getRemainingShots()  { return -1; }
    @Override public void   consumeShot()        { /* unlimited */ }
}
