package model.player;

import model.composite.Square;
import model.composite.SquareComposite;
import model.state.PlayerState;
import model.strategy.DiamondStrategy;
import model.strategy.PixelStrategy;
import model.strategy.ShotStrategy;

import java.awt.Color;
import java.util.ArrayList;

public class BluePlayer extends AbstractPlayer {

    public BluePlayer(int centerX, int centerY) {
        super(centerX, centerY);
    }

    @Override
    protected SquareComposite makeShape(int x, int y) {
        SquareComposite c = new SquareComposite();
        PlayerState s = new PlayerState(Color.CYAN);
        c.add(new Square(x - 1, y - 1, s));
        c.add(new Square(x,     y - 1, s));
        c.add(new Square(x + 1, y - 1, s));
        c.add(new Square(x,     y,     s));
        c.add(new Square(x - 2, y + 1, s));
        c.add(new Square(x - 1, y + 1, s));
        c.add(new Square(x,     y + 1, s));
        c.add(new Square(x + 1, y + 1, s));
        c.add(new Square(x + 2, y + 1, s));
        return c;
    }

    @Override
    protected ArrayList<ShotStrategy> createStrategyList() {
    	ArrayList<ShotStrategy> resul = new ArrayList<ShotStrategy> ();
    	resul.add(new PixelStrategy());
    	resul.add(new DiamondStrategy());
        return resul;
    }
    
    @Override
    public String getType() {
    	return "Blue";
    }
}
