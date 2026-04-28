package model;

import model.composite.Component;
import model.composite.Square;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class AlienGroup{

	private static AlienGroup myAlienGroup;
    private final ArrayList<Alien> aliens = new ArrayList<>();
    private Random random = new Random();
    private Timer timer;

    public AlienGroup() {
    	int count = random.nextInt(5) + 4; // 4 a 8 aliens
        int x, y;
        for (int i = 0; i < count; i++) {
        	Alien possible;
            do {
                x = random.nextInt(95) + 3;
                y = random.nextInt(8)  + 2;
                possible = new Alien(x, y);
            } while (!noOverlap(possible));
            aliens.add(new Alien(x, y));
        }
        moveEvery350ms();	//un único timer para todos los aliens

    }

    public static AlienGroup getAlienGroup() {
        if (myAlienGroup == null) {
            myAlienGroup = new AlienGroup();
        }
        return myAlienGroup;
    }
    

    private void moveEvery350ms()
	{
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
            	for (Alien a: aliens) {
            		a.moveDown();
            	}
            }
        }, 0, 350);

	}
    
    public void stopTimer() {
        timer.cancel();
    }
    

    private boolean noOverlap(Alien newAlien) {  		// mira que los aliens que se crean no coincidan de posiciones con otros
        for (Component newSq : newAlien.getSquareComposite().getSquares()) { 
            for (Alien existing : aliens) {
                for (Component existSq : existing.getSquareComposite().getSquares()) {
                	Square newSquare = (Square) newSq;
                	Square existSquare = (Square) existSq;
                    if (newSquare.getPosX() == existSquare.getPosX() && newSquare.getPosY() == existSquare.getPosY())
                    {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    

    public boolean hasReachedBottom() {
        for (Alien a : aliens) {
            if (a.getBottomY() >= 60 - 1) return true;
        }
        return false;
    }

    
    public void    remove(Alien a)   { aliens.remove(a); }
    public boolean isEmpty()         { return aliens.isEmpty(); }
    public List<Alien> getAliens()   { return aliens; }
}
