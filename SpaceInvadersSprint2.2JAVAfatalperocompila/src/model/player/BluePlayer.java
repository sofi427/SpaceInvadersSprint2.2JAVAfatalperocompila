package model.player;

import model.composite.Square;
import model.composite.SquareComposite;
import model.state.PlayerState;
import model.strategy.DiamondStrategy;
import model.strategy.PixelStrategy;
import model.strategy.ShotStrategy;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

/**
 * BluePlayer - strategies: Pixel and Diamond
 */
public class BluePlayer extends AbstractPlayer {

    public BluePlayer(int centerX, int centerY) {
        super(centerX, centerY);
    }

    @Override
    protected SquareComposite buildShape(int cx, int cy) {
        SquareComposite c = new SquareComposite();
        PlayerState s = new PlayerState(Color.CYAN);
        c.add(new Square(cx - 1, cy - 1, s));
        c.add(new Square(cx,     cy - 1, s));
        c.add(new Square(cx + 1, cy - 1, s));
        c.add(new Square(cx,     cy,     s));
        c.add(new Square(cx - 2, cy + 1, s));
        c.add(new Square(cx - 1, cy + 1, s));
        c.add(new Square(cx,     cy + 1, s));
        c.add(new Square(cx + 1, cy + 1, s));
        c.add(new Square(cx + 2, cy + 1, s));
        return c;
    }

    @Override
    protected List<ShotStrategy> buildStrategies() {
        return Arrays.asList(new PixelStrategy(), new DiamondStrategy());
    }
}
