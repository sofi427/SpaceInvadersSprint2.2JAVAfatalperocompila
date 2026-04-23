package model;

import model.composite.Component;
import model.composite.Square;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Random;

@SuppressWarnings("deprecation")
public class AlienGroup extends Observable{

	private static AlienGroup myAlienGroup;
    private final ArrayList<Alien> aliens = new ArrayList<>();
    private Random random = new Random();
    private boolean gameWon;

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
    }

    public static AlienGroup getAlienGroup() {
        if (myAlienGroup == null) {
            myAlienGroup = new AlienGroup();
        }
        return myAlienGroup;
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
