package model;

import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;
import model.composite.Square;
import model.player.AbstractPlayer;
import model.player.PlayerGenerator;

@SuppressWarnings("deprecation")
public class Board extends Observable {
    public final int LENGTH = 100; // Les he puesto final porque so
	private final int WIDTH = 60;
    private static Board myBoard=null;
    private Square[][] squares;
    private Timer timer;

    private boolean gameLost;
    private boolean gameWon;



    // Metodos de patron Singleton
    private Board() {

	}

    
    public static Board getMyBoard() {
		if (myBoard == null)
			myBoard = new Board();
		return myBoard;
	}

    // Inicializaciones de board

    public void initializeBoard(String type){
    	this.gameLost = false;
        this.gameWon = false;
        this.initializeSquares();
        PlayerGenerator.getPlayerGenerator().generatePlayer(type); //50,55 no se necesitan como parametro
        AbstractPlayer.getPlayer().registerOnBoard();
        AlienGroup.getAlienGroup().generateNormalAliens();
        this.startTimer();
        setChanged();
        notifyObservers("READY");
    }

    private void initializeSquares(){
        squares = new Square[WIDTH][LENGTH];
        for (int row = 0; row < WIDTH; row++)
			for (int col = 0; col < LENGTH; col++)
				squares[row][col] = new Square(col, row);
        
        setChanged();
    }



    // Metodos de movimiento del jugador

	public void movePlayerRight(){
        AbstractPlayer.getPlayer().moveRight();
    }


    public void movePlayerLeft(){
        AbstractPlayer.getPlayer().moveLeft();
    }

    public void movePlayerUp(){
       AbstractPlayer.getPlayer().moveUp();
    }

    public void movePlayerDown(){
        AbstractPlayer.getPlayer().moveDown();
    }

    // Metodo de disparo del jugador

    public void shoot(){
        /*if(AbstractPlayer.getPlayer().canShootCurrentStrategy()){
            AbstractPlayer.getPlayer().consumeShot();
        }*/
        AbstractPlayer.getPlayer().shoot();
    }
   
    // Metodo que se ejecuta cada 20ms para actualizar el estado del tablero y notificar a los observadores

    public void actBoardEvery20ms() {
    	//solo si no se ha lanzado la vic/derr antes
    	if (gameLost || gameWon) {
            return;
        }
    	 // Verificar victoria si no hay aliens en aliengroup
        if (AlienGroup.getAlienGroup().isEmpty()) {
            gameWon();
            return;
        }
        int[][] matrix = new int[WIDTH][LENGTH];
        for (int row = 0; row < WIDTH; row++) {
            for (int col = 0; col < LENGTH; col++) {

                Square sq = squares[row][col];
                if (sq == null) {
                    matrix[row][col] = 0;
                    continue;
                }
                String state = sq.getStateString();
                matrix[row][col] = encodeStateToInt(state);
            }
        }
        setChanged();
        notifyObservers(matrix);
    }

    private void startTimer() {
    	timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                actBoardEvery20ms();
            }
        }, 0, 20);		
	}

    private int encodeStateToInt(String state) {
        if (state == null) return 0;
        state = state.trim().toUpperCase();

        return switch (state) {
            case "EMPTY" -> 0;
            case "PLAYER" -> 1;
            case "ALIEN" -> 2;
            case "SHOT" -> 3;
            default -> 0;
        };
    }

    public String getPlayerType(){
        return AbstractPlayer.getPlayer().getType();
    }

    public synchronized Square getSquare(int x, int y) {
        return squares[y][x];
    }

    public int getWidth() { return LENGTH; }
    public int getHeight() { return WIDTH; }

    public String getCurrentShotStrategy(){
        return AbstractPlayer.getPlayer().getCurrentStrategy().getName();
    }

    public void changePlayerStrategy(){
        AbstractPlayer.getPlayer().nextStrategy();
    }

    public void StopGame() {
        // Para el timer del board
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        // Tambien paramos el timer de los aliens.
        AlienGroup.getAlienGroup().stopTimer();
        AlienGroup.getAlienGroup().clearAliens();
        
        // Paramos y limpiamos los disparos activos del jugador.
        if (AbstractPlayer.getPlayer() != null) {
            AbstractPlayer.getPlayer().stopAllShots();
            AbstractPlayer.getPlayer().clearPlayer();
        }
    }

    public boolean isInside(int x, int y){
        if(x>this.LENGTH || x < 0){ return false;}
        return !(y>this.WIDTH || y < 0);
    }

    public void gameWon() {
        if (gameLost || gameWon) return; // Evitar multiples notificaciones
        this.gameWon = true;
        StopGame(); // Detener todo al ganar
        setChanged();
        notifyObservers("WON");
    }

    public void gameLost() {
        if (gameLost || gameWon) return; // Evitar m�ltiples notificaciones
        this.gameLost = true;
        StopGame(); // Detener todo al perder
        setChanged();
        notifyObservers("LOST");
    }

    public int getRemainingShots() {
        return AbstractPlayer.getPlayer().getRemainingShots();
    }

    public String getShotType(){
        return AbstractPlayer.getPlayer().getShotType();
    }
    
    
    //Getters que necesito para la barra de vida del final boss
    public boolean isFinalBossActive() {
        for (model.Alien a : AlienGroup.getAlienGroup().getAliens()) {
            if (a.isAFinalBoss() && !a.isItDead()) {
                return true;
            }
        }
        return false;
    }

    public int getFinalBossLife() {
        return AlienGroup.getAlienGroup().getFinalBossRemainingLife();
    }

    public int getFinalBossMaxLife() {
        return 15; // vida inicial del FinalBoss
    }
    
}
