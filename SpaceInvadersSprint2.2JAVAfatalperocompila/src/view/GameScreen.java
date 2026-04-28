package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import model.Board;
import model.player.AbstractPlayer;

@SuppressWarnings("deprecation")
public class GameScreen extends JFrame implements Observer {

    private static final long serialVersionUID = 1L;
    private JPanel     contentPane;
    private JPanel     matrixPanel;
    private JLabel     statusLabel;
    private JLabel[][] pixelMatrix;
    private GameController gController;


    public GameScreen() {
        pixelMatrix = new JLabel[60][100];
        setTitle("Space Invaders");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(Color.BLACK);
        setContentPane(contentPane);
        contentPane.add(buildMatrixPanel(), BorderLayout.CENTER);
        contentPane.add(getStatusLabel(),   BorderLayout.SOUTH);
        gController = new GameController();
        addKeyListener(gController);
        setFocusable(true);
        addWindowListener(gController);
        setSize(1000, 650); 
        setLocationRelativeTo(null);
        requestFocusInWindow();
        //Aniadimos observer a gamescreen
        Board.getMyBoard().addObserver(this);
    }


    private JPanel buildMatrixPanel() {
        matrixPanel = new JPanel(new GridLayout(60, 100, 0, 0));
        matrixPanel.setBackground(Color.BLACK);

        for (int row = 0; row < 60; row++) {
            for (int col = 0; col < 100; col++) {
                JLabel lbl = new JLabel();
                lbl.setOpaque(true);
                lbl.setBackground(Color.BLACK);
                pixelMatrix[row][col] = lbl;
                matrixPanel.add(lbl);
            }
        }
        return matrixPanel;
    }


    private JLabel getStatusLabel() {
        if (statusLabel == null) {
            statusLabel = new JLabel(" Pulsa WASD para moverte | ESPACIO para disparar | M para cambiar arma");
            statusLabel.setForeground(Color.GREEN);
            statusLabel.setBackground(Color.BLACK);
            statusLabel.setOpaque(true);
            statusLabel.setFont(new Font("Courier New", Font.BOLD, 12));
        }
        return statusLabel;
    }

  

    
    //El update reacciona segun lo que le notifique board, si llega una string sera del juego ganado o perdido, aparecera el mensaje que corresponda.
    //Si llega una matriz de enteros sera porque board se ha terminado de actualizarse y la vista debe hacerlo tambien.
    public void update(Observable o, Object arg) {
        if (arg instanceof String) {
            String msg = (String) arg;
            if (msg.equals("WON")) {
                showGameOverMessage("�Has salvado a la humanidad! Premio o castigo?", Color.GREEN);
            } else if (msg.equals("LOST")) {
                showGameOverMessage("Has perdido. La invasi�n ha comenzado. Corre", Color.RED);
            }
            return;
        }

        if (arg instanceof int[][]) {
            int[][] matrix = (int[][]) arg;
                refreshMatrix(matrix);
        }
    }
    
    private void refreshMatrix(int[][] matrix) {
        for (int row = 0; row < 60; row++) {
            for (int col = 0; col < 100; col++) {
            	if (matrix[row][col]==1) {pixelMatrix[row][col].setBackground(AbstractPlayer.getPlayer().getColor());}
            	else if (matrix[row][col]==2) {pixelMatrix[row][col].setBackground(Color.MAGENTA);}
				else if (matrix[row][col]==3) {pixelMatrix[row][col].setBackground(Color.YELLOW);}
				else {pixelMatrix[row][col].setBackground(Color.BLACK);}
            }
        }
    }

    //Tratar los mensajes de ganar o perder el juego
    private void showGameOverMessage(String msg, Color color) {
            JOptionPane.showMessageDialog(this, msg, "Game Over", JOptionPane.INFORMATION_MESSAGE);
            Board.getMyBoard().deleteObserver(this);
            Board.getMyBoard().StopGame();
            dispose();
            //Volvemos a abrir startscreen por si el usu quiere volver a jugar
            StartScreen start = new StartScreen();
            start.setVisible(true);
       
    }

    public void colorOnePixel(int row, int col, Color color) {
        if (row >= 0 && row < 60 && col >= 0 && col < 100) {
            pixelMatrix[row][col].setBackground(color);
        }
    }


    //Controller
    private class GameController implements KeyListener, WindowListener, ActionListener {

        private GameController() {}

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_A) {
                AbstractPlayer.getPlayer().moveLeft();
            } else if (key == KeyEvent.VK_D) {
            	AbstractPlayer.getPlayer().moveRight();
            } else if (key == KeyEvent.VK_W) {
            	AbstractPlayer.getPlayer().moveUp();
            } else if (key == KeyEvent.VK_S) {
            	AbstractPlayer.getPlayer().moveDown();
            } else if (key == KeyEvent.VK_SPACE) {
            	AbstractPlayer.getPlayer().shoot();
            } else if (key == KeyEvent.VK_M) {
            	//AbstractPlayer.getPlayer().changeStrategy();             
            	}
        }

        @Override public void keyTyped(KeyEvent e) {}
        @Override public void keyReleased(KeyEvent e) {}

        @Override
        public void windowClosing(WindowEvent e) { Board.getMyBoard().StopGame(); } //Si se cierra la ventana se para el juego
        @Override public void windowOpened(WindowEvent e)      {}
        @Override public void windowClosed(WindowEvent e)      {}
        @Override public void windowIconified(WindowEvent e)   {}
        @Override public void windowDeiconified(WindowEvent e) {}
        @Override public void windowActivated(WindowEvent e)   {}
        @Override public void windowDeactivated(WindowEvent e) {}
        @Override public void actionPerformed(ActionEvent e)   {}
    }
}