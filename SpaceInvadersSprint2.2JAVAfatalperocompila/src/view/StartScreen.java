package view;

import model.Board;
import model.player.AbstractPlayer;
import model.player.PlayerGenerator;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

@SuppressWarnings("deprecation")
public class StartScreen extends JFrame implements Observer {

    private static final long serialVersionUID = 1L;

    private JPanel  contentPane;
    private JButton btnGreen;
    private JButton btnBlue;
    private JButton btnRed;
    private JButton btnCheck;
    private JButton startButton;
    private JLabel  lblSubtitle;
    private JLabel  lblPressPlay;
    private JLabel  lblSelectShip;

    private StartController controller;

    // ── Entry point ───────────────────────────────────────────────────────────

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                StartScreen window = new StartScreen();
                window.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    // ── Constructor ───────────────────────────────────────────────────────────

    public StartScreen() {
        setTitle("Space Invaders – Pantalla de Inicio");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setBounds(100, 100, 700, 471);

        Image bgImage = new ImageIcon(
            getClass().getClassLoader().getResource("img/FondoInicio.png")
        ).getImage();

        contentPane = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        contentPane.setBackground(Color.BLACK);
        setContentPane(contentPane);

        contentPane.add(getBtnGreen());
        contentPane.add(getBtnBlue());
        contentPane.add(getBtnRed());
        contentPane.add(getStartButton());
        contentPane.add(getLblSubtitle());
        contentPane.add(getLblPressPlay());
        contentPane.add(getLblSelectShip());

        setLocationRelativeTo(null);

        Board.getMyBoard().addObserver(this);
    }

    // ── Componentes ───────────────────────────────────────────────────────────

    private JLabel getLblSubtitle() {
        if (lblSubtitle == null) {
            lblSubtitle = new JLabel("Pulsa A-S-W-D para moverte  |  M para cambiar el arma  |  ESPACIO para disparar!");
            lblSubtitle.setBounds(60, 135, 580, 22);
            lblSubtitle.setHorizontalAlignment(SwingConstants.CENTER);
            lblSubtitle.setForeground(Color.WHITE);
            lblSubtitle.setFont(new Font("Courier New", Font.PLAIN, 11));
        }
        return lblSubtitle;
    }

    private JLabel getLblPressPlay() {
        if (lblPressPlay == null) {
            lblPressPlay = new JLabel("Pulsa Play para jugar");
            lblPressPlay.setBounds(200, 155, 300, 20);
            lblPressPlay.setHorizontalAlignment(SwingConstants.CENTER);
            lblPressPlay.setForeground(Color.LIGHT_GRAY);
            lblPressPlay.setFont(new Font("Courier New", Font.ITALIC, 17));
        }
        return lblPressPlay;
    }

    private JLabel getLblSelectShip() {
        if (lblSelectShip == null) {
            lblSelectShip = new JLabel("¡Elige tu nave! Por defecto: VERDE");
            lblSelectShip.setBounds(210, 180, 285, 25);
            lblSelectShip.setHorizontalAlignment(SwingConstants.CENTER);
            lblSelectShip.setForeground(Color.WHITE);
            lblSelectShip.setFont(new Font("Courier New", Font.BOLD, 13));
        }
        return lblSelectShip;
    }

    private JButton getBtnGreen() {
        if (btnGreen == null) {
            btnGreen = new JButton("VERDE");
            btnGreen.setBounds(127, 243, 110, 40);
            btnGreen.setBackground(new Color(0, 200, 0));
            btnGreen.setForeground(Color.WHITE);
            btnGreen.setFont(new Font("Courier New", Font.BOLD, 13));
            btnGreen.setFocusPainted(false);
            btnGreen.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
            btnGreen.addActionListener(getController());
        }
        return btnGreen;
    }

    private JButton getBtnBlue() {
        if (btnBlue == null) {
            btnBlue = new JButton("AZUL");
            btnBlue.setBounds(288, 243, 120, 40);
            btnBlue.setBackground(new Color(0, 100, 255));
            btnBlue.setForeground(Color.WHITE);
            btnBlue.setFont(new Font("Courier New", Font.BOLD, 13));
            btnBlue.setFocusPainted(false);
            btnBlue.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
            btnBlue.addActionListener(getController());
        }
        return btnBlue;
    }

    private JButton getBtnRed() {
        if (btnRed == null) {
            btnRed = new JButton("ROJA");
            btnRed.setBounds(453, 243, 110, 40);
            btnRed.setBackground(new Color(220, 0, 0));
            btnRed.setForeground(Color.WHITE);
            btnRed.setFont(new Font("Courier New", Font.BOLD, 13));
            btnRed.setFocusPainted(false);
            btnRed.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            btnRed.addActionListener(getController());
        }
        return btnRed;
    }

    private JButton getStartButton() {
        if (startButton == null) {
            startButton = new JButton("PLAY");
            startButton.setBounds(275, 345, 150, 50);
            startButton.setBackground(new Color(10, 34, 56));
            startButton.setForeground(Color.WHITE);
            startButton.setFont(new Font("Courier New", Font.BOLD, 14));
            startButton.setFocusPainted(false);
            startButton.setHorizontalAlignment(SwingUtilities.CENTER);
            startButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
            startButton.addActionListener(getController());
        }
        return startButton;
    }

    public StartController getController() {
        if (controller == null) {
            controller = new StartController();
        }
        return controller;
    }

    // ── Observer ──────────────────────────────────────────────────────────────

    /**
     * Solo reacciona al mensaje "READY" que el modelo envía cuando el tablero
     * está completamente inicializado y el jugador colocado.
     * Cualquier otro mensaje (int[][], "WON", "LOST") se ignora aquí.
     */
    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof String && ((String) arg).equals("READY")) {
            Board.getMyBoard().deleteObserver(this);
            SwingUtilities.invokeLater(() -> {
                GameScreen gameScreen = new GameScreen();
                gameScreen.setVisible(true);
                gameScreen.requestFocusInWindow();
                this.setVisible(false);
                this.dispose();
            });
        }
    }

    // ── StartController ───────────────────────────────────────────────────────

    /**
     * El controller solo invoca métodos del modelo.
     * No toca observers ni notificaciones.
     */
    private class StartController implements ActionListener {

        private String selectedShip = "GREEN";

        private StartController() {}

        @Override
        public void actionPerformed(ActionEvent e) {
            Object button = e.getSource();

            if (button == btnGreen) {
                selectedShip = "GREEN";
                highlightSelection();
            } else if (button == btnBlue) {
                selectedShip = "BLUE";
                highlightSelection();
            } else if (button == btnRed) {
                selectedShip = "RED";
                highlightSelection();
            } else if (button == btnCheck) {
                highlightSelection();
            } else if (button == startButton) {
                launchGame();
            }
        }

        private void highlightSelection() {
            btnGreen.setText(selectedShip.equals("GREEN") ? "++VERDE++" : "VERDE");
            btnBlue.setText(selectedShip.equals("BLUE")   ? "++AZUL++"  : "AZUL");
            btnRed.setText(selectedShip.equals("RED")     ? "++ROJA++"  : "ROJA");
        }

        private void launchGame() {
            Board model = Board.getMyBoard();
            model.actBoard();                                          // reset + aliens
            AbstractPlayer player = PlayerGenerator.getGenerator().generate(selectedShip);
            model.actBoard(player);                                    // coloca jugador → notifica "READY"
            model.updateBoardEvery200ms();                             // arranca el timer
        }
    }
}