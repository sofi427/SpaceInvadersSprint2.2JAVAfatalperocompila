package model.strategy;

import model.composite.Square;
import model.composite.SquareComposite;
import model.state.ShotState;

public class DiamondStrategy implements ShotStrategy {

	private int remaining = 20;

	@Override
	public SquareComposite buildShape(int originX, int originY) {
		SquareComposite composite = new SquareComposite();
		ShotState s = new ShotState();
		composite.add(new Square(originX,     originY,     s));
		composite.add(new Square(originX - 1, originY + 1, s));
		composite.add(new Square(originX,     originY + 1, s));
		composite.add(new Square(originX + 1, originY + 1, s));
		composite.add(new Square(originX,     originY + 2, s));
		return composite;
	}

	@Override 
	public int getMaxShots(){ 
		return 20; 
	}

	@Override 
	public String getName(){
		return "Diamond"; 
	}

	@Override 
	public int getRemainingShots(){ 
		return remaining; 
	}

	@Override
	public void consumeShot(){
		if (remaining > 0) {
			remaining--;
		}
	}
	
	
}