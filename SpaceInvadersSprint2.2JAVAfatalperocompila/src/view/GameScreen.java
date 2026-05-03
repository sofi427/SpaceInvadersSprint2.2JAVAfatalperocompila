package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Observable;
import java.util.Observer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
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
    //guardamos el ultimo estado pintado para detectar cambios(asi va mas rapida la actualzn.)
    private int[][]    lastMatrix = new int[60][100];
    private GameController gController;
    private Clip musicClip; //musica del juego


    public GameScreen() {
        pixelMatrix = new JLabel[60][100];
        setTitle("Space Invaders");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Image bgImage = new ImageIcon(
            getClass().getClassLoader().getResource("img/FondoJuego.png")
        ).getImage();

        contentPane = new JPanel(new BorderLayout()) {
            private static final long serialVersionUID = 1L;
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        contentPane.setOpaque(true);
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
        Board.getMyBoard().addObserver(this);
        startMusic();
    }


    private JPanel buildMatrixPanel() {
        matrixPanel = new JPanel(new GridLayout(60, 100, 0, 0));
        matrixPanel.setOpaque(false);
        int[][] lastMatrix = new int[60][100]; // inicializar a -1 en buildMatrixPanel

        for (int row = 0; row < 60; row++) {
            for (int col = 0; col < 100; col++) {
                JLabel lbl = new JLabel();
                lbl.setOpaque(false);
                pixelMatrix[row][col] = lbl;
                matrixPanel.add(lbl);
                lastMatrix[row][col] = -1; // fuerza pintar todas las celdas en el primer frame
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


    public void update(Observable o, Object arg) {
        if (arg instanceof String) {
            String msg = (String) arg;
            if (msg.equals("WON")) {
                showGameOverMessage("Has salvado a la humanidad! Premio o castigo?", Color.GREEN);
            } else if (msg.equals("LOST")) {
                showGameOverMessage("Has perdido. La invasion ha comenzado. Corre", Color.RED);
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
                int val = matrix[row][col];
                if (val == lastMatrix[row][col]) continue;
                lastMatrix[row][col] = val;

                if (val == 1) {
                    pixelMatrix[row][col].setOpaque(true);
                    pixelMatrix[row][col].setBackground(AbstractPlayer.getPlayer().getColor());
                } else if (val == 2) {
                    pixelMatrix[row][col].setOpaque(true);
                    pixelMatrix[row][col].setBackground(Color.MAGENTA);
                } else if (val == 3) {
                    pixelMatrix[row][col].setOpaque(true);
                    pixelMatrix[row][col].setBackground(Color.YELLOW);
                } else {
                    pixelMatrix[row][col].setOpaque(false);
                    pixelMatrix[row][col].setBackground(null);
                    pixelMatrix[row][col].repaint();
                }
            }
        }
        int shots= Board.getMyBoard().getRemainingShots();
        if (shots==-1) {
            statusLabel.setText(" WASD: mover | ESPACIO: disparar | M: cambiar arma | Disparos restantes: Infinitos!" );

        }
        else {
            statusLabel.setText(" WASD: mover | ESPACIO: disparar | M: cambiar arma | Disparos restantes: " + shots);
        }
    }

    private void showGameOverMessage(String msg, Color color) {
        JOptionPane.showMessageDialog(this, msg, "Game Over", JOptionPane.INFORMATION_MESSAGE);
        Board.getMyBoard().deleteObserver(this);
        Board.getMyBoard().StopGame();
        dispose();
        stopMusic();
        StartScreen start = new StartScreen();
        start.setVisible(true);
    }

    //controller
    private class GameController implements KeyListener, WindowListener, ActionListener {

        private GameController() {}

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_A) {
                Board.getMyBoard().movePlayerLeft();
            } else if (key == KeyEvent.VK_D) {
                Board.getMyBoard().movePlayerRight();
            } else if (key == KeyEvent.VK_W) {
                Board.getMyBoard().movePlayerUp();
            } else if (key == KeyEvent.VK_S) {
                Board.getMyBoard().movePlayerDown();
            } else if (key == KeyEvent.VK_SPACE) {
                Board.getMyBoard().shoot();
            } else if (key == KeyEvent.VK_M) {
                Board.getMyBoard().changePlayerStrategy();
            }
        }

        @Override public void keyTyped(KeyEvent e) {}
        @Override public void keyReleased(KeyEvent e) {}

        @Override
        public void windowClosing(WindowEvent e) { Board.getMyBoard().StopGame(); }
        @Override public void windowOpened(WindowEvent e)      {}
        @Override public void windowClosed(WindowEvent e)      {}
        @Override public void windowIconified(WindowEvent e)   {}
        @Override public void windowDeiconified(WindowEvent e) {}
        @Override public void windowActivated(WindowEvent e)   {}
        @Override public void windowDeactivated(WindowEvent e) {}
        @Override public void actionPerformed(ActionEvent e)   {}
    }


    //Empezar musica del juego
    private void startMusic() {
    	try {
        AudioInputStream audio = AudioSystem.getAudioInputStream(
            getClass().getClassLoader().getResource("img/musica.wav")
        );
        musicClip = AudioSystem.getClip();
        musicClip.open(audio);
        musicClip.loop(Clip.LOOP_CONTINUOUSLY);
        musicClip.start();
    	} catch (Exception e) {
        e.printStackTrace();
    	}
    }
    
 	//Parar musica del juego
    private void stopMusic() {
    	if (musicClip != null && musicClip.isRunning()) {
        musicClip.stop();
        musicClip.close();
    	}
    }

}