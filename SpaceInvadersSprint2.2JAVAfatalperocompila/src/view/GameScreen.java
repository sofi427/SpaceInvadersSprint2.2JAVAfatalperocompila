package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
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
    //barra de vida del FinalBoss
    private BossHealthBar bossHealthBar;
    private JPanel       bottomPanel;


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
        contentPane.add(buildBottomPanel(), BorderLayout.SOUTH);

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

    //Panel inferior donde esta la barra de vida del boss (oculta) + statusLabel
    private JPanel buildBottomPanel() {
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setOpaque(false);
        bossHealthBar = new BossHealthBar();
        bossHealthBar.setVisible(false); // oculta hasta que aparezca el boss
        bottomPanel.add(bossHealthBar);
        bottomPanel.add(getStatusLabel());
        return bottomPanel;
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
            refreshBossHealthBar();
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

    //Comprueba si el boss esta activo y actualiza la barra (la oculta si no lo esta)
    private void refreshBossHealthBar() {
        if (Board.getMyBoard().isFinalBossActive()) {
            int life    = Board.getMyBoard().getFinalBossLife();
            int maxLife = Board.getMyBoard().getFinalBossMaxLife();
            bossHealthBar.setLife(life, maxLife);
            if (!bossHealthBar.isVisible()) {
                bossHealthBar.setVisible(true);
                bottomPanel.revalidate();
            }
        } else {
            if (bossHealthBar.isVisible()) {
                bossHealthBar.setVisible(false);
                bottomPanel.revalidate();
            }
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


    //Barra de vida pixelada para el FinalBoss
    private static class BossHealthBar extends JPanel {
        private static final long serialVersionUID = 1L;
        private int life    = 15;
        private int maxLife = 15;
        //configuracion visual
        private static int bw   = 18; //ancho de cada bloque
        private static int bh   = 14; //alto de cada bloque
        private static int spacebl      = 2;  //separacion entre bloques
        private static int spx    = 12; 
        private static int sptop  = 18; //espacio para el texto "BOSS"
        private static int spbot  = 6;

        BossHealthBar() {
            setOpaque(true);
            setBackground(Color.BLACK);
            setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
            //altura suficiente para texto y los bloques
            int h = sptop + bh + spbot + 2;
            setPreferredSize(new Dimension(0, h));
        }

        public void setLife(int life, int maxLife) {
            if (life == this.life && maxLife == this.maxLife) return;
            this.life    = Math.max(0, life);
            this.maxLife = Math.max(1, maxLife);
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int width  = getWidth();
            
            //Texto del hp boss
            g.setColor(Color.WHITE);
            g.setFont(new Font("Courier New", Font.BOLD, 12));
            String label = "BOSS HP  " + life + " / " + maxLife;
            g.drawString(label, spx, 14);
            //Calculo de los bloques
            int available = width - spx * 2;
            int totalNeeded = maxLife * bw + (maxLife - 1) * spacebl;
            int startX;
            int cellW = bw;
            if (totalNeeded > available) {
                //si no cabe, reduce ancho de cada bloque proporcionalmente
                cellW = Math.max(2, (available - (maxLife - 1) * spacebl) / maxLife);
                totalNeeded = maxLife * cellW + (maxLife - 1) * spacebl;
            }
            startX = spx;
            int y  = sptop;
            //Color segun % de vida de mas a menos
            float ratio = (float) life / (float) maxLife;
            Color fillColor;
            if (ratio > 0.66f)      fillColor = new Color(50, 220, 50);   //verde
            else if (ratio > 0.33f) fillColor = new Color(255, 200, 0);   //amarillo
            else                    fillColor = new Color(220, 40, 40);   //rojo
            //Dibujar cada bloque
            for (int i = 0; i < maxLife; i++) {
                int bx = startX + i * (cellW + spacebl);
                if (i < life) {
                    //bloque relleno
                    g.setColor(fillColor);
                    g.fillRect(bx, y, cellW, bh);
                    //borde mas claro arriba/izda para efecto pixel
                    g.setColor(fillColor.brighter());
                    g.drawLine(bx, y, bx + cellW - 1, y);
                    g.drawLine(bx, y, bx, y + bh - 1);
                    //borde oscuro abajo/dcha
                    g.setColor(fillColor.darker());
                    g.drawLine(bx, y + bh - 1, bx + cellW - 1, y + bh - 1);
                    g.drawLine(bx + cellW - 1, y, bx + cellW - 1, y + bh - 1);
                } else {
                    //bloque vacio: marco gris oscuro
                    g.setColor(new Color(40, 40, 40));
                    g.fillRect(bx, y, cellW, bh);
                    g.setColor(new Color(80, 80, 80));
                    g.drawRect(bx, y, cellW - 1, bh - 1);
                }
            }
        }
    }

}
