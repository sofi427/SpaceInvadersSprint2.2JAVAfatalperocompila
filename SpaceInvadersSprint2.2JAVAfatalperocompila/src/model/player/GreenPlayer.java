package model.player;

import model.composite.Square;
import model.composite.SquareComposite;
import model.state.PlayerState;
import model.strategy.ArrowStrategy;
import model.strategy.PixelStrategy;
import model.strategy.ShotStrategy;

import java.awt.Color;
import java.util.ArrayList;

public class GreenPlayer extends AbstractPlayer {

	private static GreenPlayer myGreenPlayer;
	
    private GreenPlayer(int centerX, int centerY) {
        super(centerX, centerY);
    }

    public static GreenPlayer getPlayer() {
		if (myGreenPlayer == null) {
			myGreenPlayer = new GreenPlayer(50,55);
		}
		return myGreenPlayer;
	}
    
    @Override
    public Color getColor()
    { return Color.GREEN; }
    
    @Override
    protected SquareComposite makeShape(int x, int y) {
        SquareComposite c = new SquareComposite();
        PlayerState s = new PlayerState(Color.GREEN);
        c.add(new Square(x,     y - 2, s));
        c.add(new Square(x - 1, y - 1, s));
        c.add(new Square(x,     y - 1, s));
        c.add(new Square(x + 1, y - 1, s));
        c.add(new Square(x,     y,     s));
        c.add(new Square(x - 1, y + 1, s));
        c.add(new Square(x,     y + 1, s));
        c.add(new Square(x + 1, y + 1, s));
        return c;
    }

    @Override
    protected ArrayList<ShotStrategy> createStrategyList() {
    	ArrayList<ShotStrategy> resul = new ArrayList<ShotStrategy> ();
    	resul.add(new PixelStrategy());
    	resul.add(new ArrowStrategy());
        return resul;
    }
    
    @Override
    public String getType() {
    	return "Green";
    }
}
