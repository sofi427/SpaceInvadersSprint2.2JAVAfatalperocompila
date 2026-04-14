package model;

import java.util.Observable;
import java.util.Timer;
import model.composite.Square;
import model.player.AbstractPlayer;
import model.player.PlayerGenerator;

@SuppressWarnings("deprecation")
public class Board extends Observable {
    public int LENGTH = 100;
	private int WIDTH = 60;
    private static Board myBoard;
    private Square[][] squares;
    private Timer timer;

    private boolean gameLost;
    private boolean gameWon;

    private AbstractPlayer player;

    private Board() {

	}

    private void initializeSquares(){
        squares = new Square[WIDTH][LENGTH];
        for (int row = 0; row < WIDTH; row++)
			for (int col = 0; col < LENGTH; col++)
				squares[row][col] = new Square(col, row);
        
        setChanged();
        this.notifyObservers("READY");

    }

    public static Board getMyBoard() {
		if (myBoard == null)
			myBoard = new Board();
		return myBoard;
	}

    public void initializeBoard(String type){
        this.initializeSquares();
        this.player = PlayerGenerator.getPlayerGenerator().generatePlayer(type, 5,5);
    }

    public void movePlayerRight(){
        this.player.moveRight();
    }

    public void movePlayerLeft(){
        this.player.moveLeft();
    }

    public void movePlayerUp(){
        this.player.moveUp();
    }

    public void movePlayerDown(){
        this.player.moveDown();
    }

    public void shoot(){
        if(this.player.canShootCurrentStrategy()){
            this.player.consumeShot();
        }
    }
   
    public void actBoardEvery200ms() {
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
        return this.player.getType();
    }

    public Square getSquare(int x, int y) {
        return squares[y][x];
    }

    public int getWidth() { return LENGTH; }
    public int getHeight() { return WIDTH; }

    public String getCurrentShotStrategy(){
        return this.player.getCurrentStrategy().getName();
    }

    public void changePlayerStrategy(){
        this.player.nextStrategy();
    }

    public void StopGame(){
        this.timer.cancel();
    }
    
}
