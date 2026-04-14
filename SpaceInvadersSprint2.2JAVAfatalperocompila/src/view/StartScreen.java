package view;

import model.Board;
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


    public StartScreen() {
        setTitle("Pantalla de selección");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 700, 471);
        Image bgImage = new ImageIcon(
            getClass().getClassLoader().getResource("img/FondoInicio.png")
        ).getImage();
        contentPane = new JPanel(null) {
			private static final long serialVersionUID = 1L;
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
        
        //Aniadimos el observer a la startscreen
        Board.getMyBoard().addObserver(this);
    }

    //Componentes (subtitulo e informacion, botones de seleccion y de play) 
    
    private JLabel getLblSubtitle() {
        if (lblSubtitle == null) {
            lblSubtitle = new JLabel("Pulsa WASD para moverte | M para cambiar el arma | ESPACIO para disparar!");
            lblSubtitle.setBounds(60, 135, 580, 22);
            lblSubtitle.setHorizontalAlignment(SwingConstants.CENTER);
            lblSubtitle.setForeground(Color.WHITE);
            lblSubtitle.setFont(new Font("Courier New", Font.PLAIN, 11));
        }
        return lblSubtitle;
    }

    private JLabel getLblPressPlay() {
        if (lblPressPlay == null) {
            lblPressPlay = new JLabel("¡Elige tu nave! Por defecto: VERDE");
            lblPressPlay.setBounds(200, 155, 300, 20);
            lblPressPlay.setHorizontalAlignment(SwingConstants.CENTER);
            lblPressPlay.setForeground(Color.LIGHT_GRAY);
            lblPressPlay.setFont(new Font("Courier New", Font.ITALIC, 17));
        }
        return lblPressPlay;
    }

    private JLabel getLblSelectShip() {
        if (lblSelectShip == null) {
            lblSelectShip = new JLabel("Pulsa Play para jugar");
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


    //La vista reacciona cuando al terminar de inicializar el tablero le notifica READY
    //Update quita el observer de startscreen, crea la gamescreen, la pone visible y focaliza en ella. Despues deja de visualizar startscreen. 
    public void update(Observable o, Object arg) {
            Board.getMyBoard().deleteObserver(this);
            GameScreen gameScreen = new GameScreen();
            gameScreen.setVisible(true);
            this.setVisible(false);
    }


    //Controller
    private class StartController implements ActionListener {
    	private String selectedShip = "Green";  //Verde como predeterminado por si no se selecciona antes de elegir player
        private StartController() {}

        public void actionPerformed(ActionEvent e) { //Elecciones dde player e inicio del juego con play
            Object button = e.getSource();
            if (button == btnGreen) {
                selectedShip = "Green";
                highlightSelection();
            } else if (button == btnBlue) {
                selectedShip = "Blue";
                highlightSelection();
            } else if (button == btnRed) {
                selectedShip = "Red";
                highlightSelection();
            } else if (button == btnCheck) {
                highlightSelection();
            } else if (button == startButton) {
                launchGame();}
        }
        private void highlightSelection() { //Remarcar la elegida con ++blabla++
            btnGreen.setText(selectedShip.equals("Green") ? "++VERDE++" : "VERDE");
            btnBlue.setText(selectedShip.equals("Blue")   ? "++AZUL++"  : "AZUL");
            btnRed.setText(selectedShip.equals("Red")     ? "++ROJA++"  : "ROJA");
        }
        //Inicializa el tablero, genera al jugador y lo coloca en el tablero e inicia el timer de refresco/actualizacion del tablero.
        private void launchGame() {                                         
            Board.getMyBoard().initializeBoard(selectedShip);  
        }
    }
}