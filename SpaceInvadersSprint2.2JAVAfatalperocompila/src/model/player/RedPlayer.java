package model.player;

import model.composite.Square;
import model.composite.SquareComposite;
import model.state.PlayerState;
import model.strategy.ArrowStrategy;
import model.strategy.DiamondStrategy;
import model.strategy.PixelStrategy;
import model.strategy.ShotStrategy;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

/**
 * RedPlayer - strategies: Pixel, Arrow and Diamond
 */
public class RedPlayer extends AbstractPlayer {

    public RedPlayer(int centerX, int centerY) {
        super(centerX, centerY);
    }

    @Override
    protected SquareComposite buildShape(int cx, int cy) {
        SquareComposite c = new SquareComposite();
        PlayerState s = new PlayerState(Color.RED);
        c.add(new Square(cx - 1, cy - 2, s));
        c.add(new Square(cx,     cy - 2, s));
        c.add(new Square(cx + 1, cy - 2, s));
        c.add(new Square(cx - 1, cy - 1, s));
        c.add(new Square(cx,     cy - 1, s));
        c.add(new Square(cx + 1, cy - 1, s));
        c.add(new Square(cx - 2, cy,     s));
        c.add(new Square(cx - 1, cy,     s));
        c.add(new Square(cx,     cy,     s));
        c.add(new Square(cx + 1, cy,     s));
        c.add(new Square(cx + 2, cy,     s));
        return c;
    }

    @Override
    protected List<ShotStrategy> buildStrategies() {
        return Arrays.asList(new PixelStrategy(), new ArrowStrategy(), new DiamondStrategy());
    }
}
