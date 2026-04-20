package model.player;

import model.composite.Square;
import model.composite.SquareComposite;
import model.state.PlayerState;
import model.strategy.ArrowStrategy;
import model.strategy.DiamondStrategy;
import model.strategy.PixelStrategy;
import model.strategy.ShotStrategy;

import java.awt.Color;
import java.util.ArrayList;

public class RedPlayer extends AbstractPlayer {

	private static RedPlayer myRedPlayer;
	
    private RedPlayer(int centerX, int centerY) {
        super(centerX, centerY);
    }

    @Override
    public RedPlayer getPlayer(int centerX, int centerY) {
		if (myRedPlayer == null) {
			myRedPlayer = new RedPlayer(centerX, centerY);
		}
		return myRedPlayer;
	}
    
    @Override
    protected SquareComposite makeShape(int x, int y) {
        SquareComposite c = new SquareComposite();
        PlayerState s = new PlayerState(Color.RED);
        c.add(new Square(x - 1, y - 2, s));
        c.add(new Square(x,     y - 2, s));
        c.add(new Square(x + 1, y - 2, s));
        c.add(new Square(x - 1, y - 1, s));
        c.add(new Square(x,     y - 1, s));
        c.add(new Square(x + 1, y - 1, s));
        c.add(new Square(x - 2, y,     s));
        c.add(new Square(x - 1, y,     s));
        c.add(new Square(x,     y,     s));
        c.add(new Square(x + 1, y,     s));
        c.add(new Square(x + 2, y,     s));
        return c;
    }

    @Override
    protected ArrayList<ShotStrategy> createStrategyList() {
    	ArrayList<ShotStrategy> resul = new ArrayList<ShotStrategy> ();
    	resul.add(new PixelStrategy());
    	resul.add(new ArrowStrategy());
    	resul.add(new DiamondStrategy());
        return resul;
    }
    
    @Override
    public String getType() {
    	return "Red";
    }
}
