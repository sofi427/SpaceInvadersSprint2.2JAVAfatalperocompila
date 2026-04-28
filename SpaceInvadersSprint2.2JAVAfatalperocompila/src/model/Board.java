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
    private static Board myBoard;
    private Square[][] squares;
    private Timer timer;

    private boolean gameLost;
    private boolean gameWon;



    // Métodos de patrón Singleton
    private Board() {

	}

    
    public static Board getMyBoard() {
		if (myBoard == null)
			myBoard = new Board();
		return myBoard;
	}

    // Inicializaciones de board

    public void initializeBoard(String type){
        this.initializeSquares();
        PlayerGenerator.getPlayerGenerator().generatePlayer(type, 50,55);
        AbstractPlayer.getPlayer().registerOnBoard();
        AlienGroup.getAlienGroup().generateAliens();
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



    // Métodos de movimiento del jugador

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
        if(AbstractPlayer.getPlayer().canShootCurrentStrategy()){
            AbstractPlayer.getPlayer().consumeShot();
        }
    }
   
    // Método que se ejecuta cada 20ms para actualizar el estado del tablero y notificar a los observadores

    public void actBoardEvery20ms() {
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
        System.out.println("El juego está");
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

    public Square getSquare(int x, int y) {
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

    public void StopGame(){
    	if (timer != null) {
            this.timer.cancel();
    	}
    }

    public boolean isInside(int x, int y){
        if(x>this.LENGTH || x < 0){ return false;}
        if(y>this.WIDTH || y < 0){ return false;}
        return true;
    }

    public void gameWon() {
        this.gameWon = true;
        setChanged();
        notifyObservers("WON");
    }

    public void gameLost() {
        this.gameLost = true;
        setChanged();
        notifyObservers("LOST");
    }
    
}
