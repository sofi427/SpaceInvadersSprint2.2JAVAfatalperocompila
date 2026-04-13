package spaceinvaders.model;

import spaceinvaders.model.composite.Square;
import spaceinvaders.model.composite.SquareComposite;
import spaceinvaders.model.state.PlayerState;
import model.player.AbstractPlayer;
import model.strategy.ArrowStrategy;
import model.strategy.PixelStrategy;
import model.strategy.ShotStrategy;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

/**
 * GreenPlayer - strategies: Pixel and Arrow
 * Shape (center at cx,cy):
 *   .X.
 *   XXX
 *   .X.
 *   XXX
 */
public class GreenPlayer extends AbstractPlayer {

    public GreenPlayer(int centerX, int centerY) {
        super(centerX, centerY);
    }

    @Override
    protected SquareComposite buildShape(int cx, int cy) {
        SquareComposite c = new SquareComposite();
        PlayerState s = new PlayerState(Color.GREEN);
        c.add(new Square(cx,     cy - 2, s));
        c.add(new Square(cx - 1, cy - 1, s));
        c.add(new Square(cx,     cy - 1, s));
        c.add(new Square(cx + 1, cy - 1, s));
        c.add(new Square(cx,     cy,     s));
        c.add(new Square(cx - 1, cy + 1, s));
        c.add(new Square(cx,     cy + 1, s));
        c.add(new Square(cx + 1, cy + 1, s));
        return c;
    }

    @Override
    protected List<ShotStrategy> buildStrategies() {
        return Arrays.asList(new PixelStrategy(), new ArrowStrategy());
    }
}
