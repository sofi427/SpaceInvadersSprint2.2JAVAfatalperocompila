package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import model.composite.Component;
import model.composite.Square;

public class AlienGroup{

	private static AlienGroup myAlienGroup=null;
    private ArrayList<Alien> aliens = new ArrayList<>();
    @SuppressWarnings("FieldMayBeFinal")
    private Random random = new Random();
    private Timer timer;
    private Timer timer2;
    private int steps = 0;
    private boolean goingRight = true;


    private AlienGroup() {}

    public static AlienGroup getAlienGroup() {
        if (myAlienGroup == null) {
            myAlienGroup = new AlienGroup();
        }
        return myAlienGroup;
    }
    
    public void generateNormalAliens() {
        aliens.clear();

    	int count = random.nextInt(5) + 4; // 4 a 8 aliens
        int x, y;
        Alien possible;
        for (int i = 0; i < count; i++) {
            do {
                x = random.nextInt(95) + 3;
                y = random.nextInt(8)  + 2;
                possible = new NormalAlien(x, y);
            } while (!noOverlap(possible));
            possible.changeSquaresState();
            aliens.add(possible);
        }
        moveNormalEvery350ms();	//un unico timer para todos los aliens
        shootNormalEvery2s();
    }
    
    
    private void moveNormalEvery350ms()
	{
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
            	for (Alien a: new ArrayList<>(aliens)) {
                    a.moveDown();
                }
                // Verificar si algun alien llego al fondo
                if (hasReachedBottom()) {
	            	stopTimer(); // Detener el timer antes de notificar
	            	Board.getMyBoard().gameLost(); // Esto tambien llamara a StopGame()
	            }
            }
        }, 0, 350);
	}
    
    private void shootNormalEvery2s() {
    	timer2 = new Timer();
		timer2.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
              	int shootingAliens = random.nextInt(3);
            	for (int i=0; i<shootingAliens;i++) {
                	int x = random.nextInt(aliens.size());
                	aliens.get(x).shoot();
            	}
            }
        }, 0, 2000);
    }
    
    public void stopTimer() {
    	if (timer != null) {
            timer.cancel();
            timer = null;
        }
    	if (timer2 != null) {
            timer2.cancel();
            timer2 = null;
        }
    }
    
    public void clearAliens() {
    	this.aliens=new ArrayList<Alien>();
    }
    

    private boolean noOverlap(Alien newAlien) {  		// mira que los aliens que se crean no coincidan de posiciones con otros
        for (Component newSq : newAlien.getSquareComposite().getSquares()) {
            for (Alien existing : new ArrayList<>(aliens)) {
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
        for (Alien a : new ArrayList<>(aliens)) {
            if (a.getBottomY() >= 60 - 1) return true;
        }
        return false;
    }

    
    public void    remove(Alien a)   { aliens.remove(a); }
    public boolean isEmpty()         { return aliens.isEmpty(); }
    public List<Alien> getAliens()   { return new ArrayList<>(aliens); }

    private Alien getAlienAt(int x, int y) {
        for (Alien a : new ArrayList<>(aliens)) {
            if (a.containsSquare(x, y)) {
                return a;
            }
        }
        return null;
    }

    public void removeAlienAt(int x, int y) {
        Alien a = getAlienAt(x, y);
        if (a == null) return;
        // Eliminar por completo el composite del alien
        else if (a instanceof FinalBoss) {
        	a.destroy();
        	if (((FinalBoss) a).isItDead()) {
            	stopTimer();
            	Board.getMyBoard().gameWon();
        	}
        }
        else {
        	a.destroy();
        	aliens.remove(a);
        	if (aliens.isEmpty()){
        		this.generateFinalBoss();
        	}
        }
    }

    public void generateFinalBoss() {
        if (!aliens.isEmpty()) {
            System.out.println("No se puede generar el Final Boss mientras queden aliens normales.");
            return;
        }
        this.stopTimer();
        //int x = random.nextInt(79) + 10;
        FinalBoss finalBoss = new FinalBoss(25, 8);
        finalBoss.changeSquaresState();
        aliens.add(finalBoss);
        moveBossEvery100ms();
        shootBossEvery1s();
    }
    
    private void moveBossEvery100ms() {
    	timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
            	if (aliens.isEmpty()) return;
            	moveFinalBoss();
                // Verificar si algun alien llego al fondo
                if (hasReachedBottom()) {
	            	stopTimer(); // Detener el timer antes de notificar
	            	Board.getMyBoard().gameLost(); // Esto tambien llamara a StopGame()
	            }
            }
        }, 0, 100);
    }
    
    private void moveFinalBoss() {
    	if (steps < 50) {
    		int x;
    		if (goingRight)
    		    x = 1;
    		else
    		    x = -1;
    		aliens.get(0).move(x, 0);
    		steps++;
    	} else {
    		aliens.get(0).move(0, 2);      // baja
    		steps = 0;
    		goingRight = !goingRight;
    	}
    }
    
    
    private void shootBossEvery1s() {
    	timer2 = new Timer();
		timer2.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
            	if (aliens.isEmpty()) return;
            	aliens.get(0).shoot();
            }
        }, 0, 1000);
    }
    
    public void reduceFinalBossLife() {
    	//if (this.aliens.get(0) instanceof FinalBoss) {
    		this.aliens.get(0).reduceLife();
    	//}
    }
    
    public int getFinalBossRemainingLife() {
    	return this.aliens.get(0).getRemainingLife();
    }
}