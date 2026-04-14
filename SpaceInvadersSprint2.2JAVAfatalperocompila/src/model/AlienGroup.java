package model;

import model.composite.Square;
import model.player.AbstractPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AlienGroup {

	private static AlienGroup myAlienGroup;
    private final ArrayList<Alien> aliens = new ArrayList<>();
    private Random random = new Random();

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

    private boolean noOverlap(Alien newAlien) {
        for (Square newSq : newAlien.getSquares().getSquares()) {
            for (Alien existing : aliens) {
                for (Square existSq : existing.getSquares().getSquares()) {
                    if (newSq.getPosX() == existSq.getPosX() && newSq.getPosY() == existSq.getPosY())
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
