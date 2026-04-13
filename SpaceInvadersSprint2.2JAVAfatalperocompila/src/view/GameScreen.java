package view;

import model.Board;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
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
import javax.swing.SwingUtilities;

@SuppressWarnings("deprecation")
public class GameScreen extends JFrame implements Observer {

    private static final long serialVersionUID = 1L;

    private static final int MIN_PIX = 4;

    private static final Color COLOR_EMPTY  = Color.BLACK;
    private static final Color COLOR_PLAYER = Color.CYAN;
    private static final Color COLOR_ALIEN  = Color.GREEN;
    private static final Color COLOR_SHOT   = Color.YELLOW;

    private JPanel     contentPane;
    private JPanel     matrixPanel;
    private JLabel     statusLabel;
    private JLabel[][] pixelMatrix;
    private GameController gController;

    private int pixSize = 8;

    // ── Constructor ───────────────────────────────────────────────────────────

    public GameScreen() {
        pixelMatrix = new JLabel[Board.WIDTH][Board.LENGTH];

        setTitle("Space Invaders");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);

        contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(Color.BLACK);
        setContentPane(contentPane);

        contentPane.add(buildMatrixPanel(), BorderLayout.CENTER);
        contentPane.add(getStatusLabel(),   BorderLayout.SOUTH);

        gController = new GameController();
        addKeyListener(gController);
        addWindowListener(gController);
        setFocusable(true);
        requestFocusInWindow();

        setSize(Board.LENGTH * pixSize, Board.WIDTH * pixSize + 30);
        setLocationRelativeTo(null);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                recalcPixSize();
            }
        });

        Board.getMyBoard().addObserver(this);
    }

    // ── Paneles ───────────────────────────────────────────────────────────────

    private JPanel buildMatrixPanel() {
        matrixPanel = new JPanel(new GridLayout(Board.WIDTH, Board.LENGTH, 0, 0));
        matrixPanel.setBackground(Color.BLACK);
        updateMatrixPanelSize();

        for (int row = 0; row < Board.WIDTH; row++) {
            for (int col = 0; col < Board.LENGTH; col++) {
                JLabel lbl = new JLabel();
                lbl.setOpaque(true);
                lbl.setBackground(Color.BLACK);
                lbl.setPreferredSize(new Dimension(pixSize, pixSize));
                pixelMatrix[row][col] = lbl;
                matrixPanel.add(lbl);
            }
        }
        return matrixPanel;
    }

    private void updateMatrixPanelSize() {
        matrixPanel.setPreferredSize(
                new Dimension(Board.LENGTH * pixSize, Board.WIDTH * pixSize));
    }

    private JLabel getStatusLabel() {
        if (statusLabel == null) {
            statusLabel = new JLabel("  Pulsa WASD para moverte  |  ESPACIO para disparar  |  M para cambiar arma");
            statusLabel.setForeground(Color.GREEN);
            statusLabel.setBackground(Color.BLACK);
            statusLabel.setOpaque(true);
            statusLabel.setFont(new Font("Courier New", Font.BOLD, 12));
        }
        return statusLabel;
    }

    // ── Redimensionado ────────────────────────────────────────────────────────

    private void recalcPixSize() {
        int availW = contentPane.getWidth();
        int availH = contentPane.getHeight() - getStatusLabel().getHeight();
        if (availW <= 0 || availH <= 0) return;

        int byWidth  = availW / Board.LENGTH;
        int byHeight = availH / Board.WIDTH;
        int newPix   = Math.max(MIN_PIX, Math.min(byWidth, byHeight));

        if (newPix == pixSize) return;
        pixSize = newPix;

        Dimension cellDim = new Dimension(pixSize, pixSize);
        for (int row = 0; row < Board.WIDTH; row++) {
            for (int col = 0; col < Board.LENGTH; col++) {
                pixelMatrix[row][col].setPreferredSize(cellDim);
            }
        }
        updateMatrixPanelSize();
        matrixPanel.revalidate();
        matrixPanel.repaint();
    }

    // ── Observer ──────────────────────────────────────────────────────────────

    /**
     * Recibe notificaciones del modelo:
     *   String "WON"  → victoria
     *   String "LOST" → derrota
     *   int[][] → refresco visual normal
     *
     * "READY" lo gestiona StartScreen, aquí se ignora.
     */
    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof String) {
            String msg = (String) arg;
            if (msg.equals("WON")) {
                showGameOverMessage("¡Has salvado a la humanidad!", Color.GREEN);
            } else if (msg.equals("LOST")) {
                showGameOverMessage("Has perdido. La invasión ha comenzado.", Color.RED);
            }
            // "READY" y cualquier otro String se ignoran aquí
            return;
        }

        if (arg instanceof int[][]) {
            int[][] matrix = (int[][]) arg;
            SwingUtilities.invokeLater(() -> {
                refreshMatrix(matrix);
                updateInfo();
            });
        }
    }

    // ── Pintado ───────────────────────────────────────────────────────────────

    private void refreshMatrix(int[][] matrix) {
        for (int row = 0; row < Board.WIDTH; row++) {
            for (int col = 0; col < Board.LENGTH; col++) {
                pixelMatrix[row][col].setBackground(cellColor(matrix[row][col]));
            }
        }
    }

    /** Traduce código numérico de celda a Color. Sin switch: if/else if. */
    private Color cellColor(int code) {
        if      (code == Board.CELL_PLAYER) return COLOR_PLAYER;
        else if (code == Board.CELL_ALIEN)  return COLOR_ALIEN;
        else if (code == Board.CELL_SHOT)   return COLOR_SHOT;
        else                                return COLOR_EMPTY;
    }

    private void updateInfo() {
        if (Board.getMyBoard().getPlayer() != null) {
            var player = Board.getMyBoard().getPlayer();
            String weapon   = player.getCurrentStrategy().getShotTypeName();
            String ammoInfo = "";
            if (weapon.equals("ARROW"))   ammoInfo = " | Flecha: "   + player.getAmmoFecha();
            if (weapon.equals("DIAMOND")) ammoInfo = " | Diamante: " + player.getAmmoRombo();
            getStatusLabel().setText(
                    "  Arma: " + weapon + ammoInfo
                    + "  |  M= Cambiar arma   ESPACIO= disparar   WASD= Moverse");
        }
    }

    /**
     * Muestra el diálogo de fin de partida.
     * Al pulsar OK: desregistra observer, para el juego, cierra esta ventana
     * y vuelve a la StartScreen.
     */
    private void showGameOverMessage(String msg, Color color) {
        SwingUtilities.invokeLater(() -> {
            getStatusLabel().setText("  " + msg);
            getStatusLabel().setForeground(color);

            JOptionPane.showMessageDialog(this, msg, "Game Over", JOptionPane.INFORMATION_MESSAGE);

            Board.getMyBoard().deleteObserver(this);
            Board.getMyBoard().stopGame();
            dispose();

            SwingUtilities.invokeLater(() -> {
                StartScreen start = new StartScreen();
                start.setVisible(true);
            });
        });
    }

    public void colorOnePixel(int row, int col, Color color) {
        if (row >= 0 && row < Board.WIDTH && col >= 0 && col < Board.LENGTH) {
            pixelMatrix[row][col].setBackground(color);
        }
    }

    // ── GameController ────────────────────────────────────────────────────────

    /**
     * El controller solo llama a métodos del modelo.
     * Es el modelo (Board) quien decide qué notificar y cuándo.
     */
    private class GameController implements KeyListener, WindowListener, ActionListener {

        private GameController() {}

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
                Board.getMyBoard().movePlayerLeft();
            } else if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
                Board.getMyBoard().movePlayerRight();
            } else if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
                Board.getMyBoard().movePlayerUp();
            } else if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
                Board.getMyBoard().movePlayerDown();
            } else if (key == KeyEvent.VK_SPACE) {
                Board.getMyBoard().shoot();
            } else if (key == KeyEvent.VK_M) {
                Board.getMyBoard().changePlayerStrategy();
            }
        }

        @Override public void keyTyped(KeyEvent e)    {}
        @Override public void keyReleased(KeyEvent e) {}

        @Override
        public void windowClosing(WindowEvent e) { Board.getMyBoard().stopGame(); }
        @Override public void windowOpened(WindowEvent e)      {}
        @Override public void windowClosed(WindowEvent e)      {}
        @Override public void windowIconified(WindowEvent e)   {}
        @Override public void windowDeiconified(WindowEvent e) {}
        @Override public void windowActivated(WindowEvent e)   {}
        @Override public void windowDeactivated(WindowEvent e) {}
        @Override public void actionPerformed(ActionEvent e)   {}
    }
}