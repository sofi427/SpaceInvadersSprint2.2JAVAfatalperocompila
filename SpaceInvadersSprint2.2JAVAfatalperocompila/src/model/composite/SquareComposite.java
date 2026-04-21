package model.composite;

import java.util.ArrayList;
import java.util.List;
import model.Board;


public class SquareComposite implements Component {

    private ArrayList<Component> children; 

    public SquareComposite() {
        this.children = new ArrayList<>();
    }

    public void add(Component c) {
        children.add(c);
    }


    @Override
    public void move(int dx, int dy) {
        for(Component act: this.children){
            Square sq = (Square) act;
            int nx = sq.getPosX() + dx;
            int ny = sq.getPosY() + dy;
             if (nx < 0 || nx >= Board.getMyBoard().getWidth()
             || ny < 0 || ny >= Board.getMyBoard().getHeight()) return;
            
             // Aquí se tienen que comprobar las colisiones con otros objetos del tablero, pero eso se haría en el método move de cada clase concreta (Alien, Player...) y no aquí, porque SquareComposite es solo un contenedor de Squares, no tiene lógica de juego. Por lo tanto, aquí simplemente se llama al método move de cada componente hijo, y cada uno se encarga de comprobar sus propias colisiones y límites.
        }
    }

    @Override
    public List<Component> getChildren() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getChildren'");
    }

}
