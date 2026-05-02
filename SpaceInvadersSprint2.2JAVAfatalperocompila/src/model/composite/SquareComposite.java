package model.composite;

import java.util.ArrayList;

import model.Alien;
import model.AlienGroup;
import model.Board;
import model.player.AbstractPlayer;
import model.state.AlienState;
import model.state.EmptyState;
import model.state.SquareState;


public class SquareComposite implements Component {

    private ArrayList<Component> children; 


    public SquareComposite() {
        this.children = new ArrayList<>();
    }

    public void add(Component c) {
        children.add(c);
    } 

    public void remove(Component c) {
        children.remove(c);
    }


    
    @Override
    public void move(int dx, int dy) {
        

        ArrayList<Component> current = new ArrayList<>(this.children);

        ArrayList<Square> target = new ArrayList<>();
        ArrayList<SquareState> originStates = new ArrayList<>();
        ArrayList<SquareState> oldTargetStates = new ArrayList<>();
        ArrayList<String> results = new ArrayList<>();

        ArrayList<int[]> shotsToRemove = new ArrayList<>();
        
        // ---------- FASE 1: planificar (sin modificar el board) ----------
        for (Component comp : current) {
            Square from = (Square) comp;

            int nx = from.getPosX() + dx;
            int ny = from.getPosY() + dy;

            // límites
            if (nx < 0 || nx >= Board.getMyBoard().getWidth()
                    || ny < 0 || ny >= Board.getMyBoard().getHeight()) {
                return; // cancelas movimiento completo
            }

            Square dest = Board.getMyBoard().getSquare(nx, ny);

            SquareState origin = from.getState();
            SquareState oldDestState = dest.getState(); // guardo lo que había ANTES

            // Si el destino es una casilla del propio shot (otra parte del mismo composite),
            // entonces para colisión lo tratamos como vacío (se va a vaciar en la fase 2)
            SquareState effectiveTarget =
                    isInCurrentByPosition(current, dest) ? new EmptyState() : oldDestState;

            String result = origin.collideWith(effectiveTarget);

            if ("notmove".equals(result)) {
                return; // nadie se mueve
            }

            // evita que 2 casillas del shot intenten ir al mismo destino
            if (target.contains(dest)) {
                return;
            }

            target.add(dest);
            originStates.add(origin);
            oldTargetStates.add(oldDestState);
            results.add(result);
        }

        // ---------- FASE 2: vaciar orígenes ----------
        for (Component comp : current) {
            ((Square) comp).changeState(new EmptyState());
        }

        // ---------- FASE 3: aplicar resultados por casilla ----------
        for (int i = 0; i < target.size(); i++) {
            Square dest = target.get(i);
            String result = results.get(i);

            SquareState origin = originStates.get(i);
            SquareState oldDestState = oldTargetStates.get(i);

            switch (result) {

                case "move" -> {
                    dest.changeState(origin);
                }

                case "destroyboth" -> {
                    if (oldDestState instanceof AlienState) {
                            for (Alien a : AlienGroup.getAlienGroup().getAliens()) {
                                if (a.containsSquare(dest.getPosX(), dest.getPosY())) {
                                    for (Component c : new ArrayList<>(a.getSquareComposite().getSquares())) {
                                        Square sq = (Square) c;
                                        Board.getMyBoard().getSquare(sq.getPosX(), sq.getPosY()).changeState(new EmptyState());
                                    }
                                }
                            }
                            AlienGroup.getAlienGroup().removeAlienAt(dest.getPosX(), dest.getPosY());
                    }
                    shotsToRemove.add(new int[]{dest.getPosX(), dest.getPosY()});
                 
                }
                default -> { // Si aparece algo inesperado, por seguridad no hacemos nada. 
                }
           }
        }
            
        // ---------- FASE 4: aqui se añade las casillas destino al composite de this ----------
        this.children.clear();
        for (int i = 0; i < target.size(); i++) {
            if ("move".equals(results.get(i))) {
                this.children.add(target.get(i)); // Square es Component
            }
        }
     // ---------- FASE 5: destruir el shot si hubo colisión con alien (al final pq si no no se borra por algna razon)---------
        if (!shotsToRemove.isEmpty()) {
            if (!this.children.isEmpty()) {
                Square shotSq = (Square) this.children.get(0);
                AbstractPlayer.getPlayer().removeShotAt(shotSq.getPosX(), shotSq.getPosY());
            } else {
                AbstractPlayer.getPlayer().removeShotAt(shotsToRemove.get(0)[0], shotsToRemove.get(0)[1]);
            }
        }
    }

    private boolean isInCurrentByPosition(ArrayList<Component> current, Square dest) {
        for (Component c : current) {
            Square s = (Square) c;
            if (s.getPosX() == dest.getPosX() && s.getPosY() == dest.getPosY()) {
                return true;
            }
        }
        return false;
}


    public synchronized ArrayList<Component> getSquares() {
        return children;
    }


    public synchronized Square getCenterSquare() { // El método creo que está hecho pero cuidado porque alomejor al moverse no se actualiza

        int maxX = -1;
        int maxY = -1;
        int minX = -1;
        int minY = -1;

        for (Component c : children) {
            Square actSquare = (Square) c;
            int actX = actSquare.getPosX();
            int actY = actSquare.getPosY();
            if(maxX < actX || maxX == -1) { maxX = actX; }
            if(maxY < actY || maxY == -1) { maxY = actY; }
            if(minX > actX || minX== -1) { minX = actX; }
            if(minY > actY || minY == -1) { minY = actY;}
        }

        return Board.getMyBoard().getSquare((maxX+minX)/2, (maxY+minY)/2);
    }

    public void turnEmpty() {
        for (Component c : children) {
            Square s = (Square) c;
            s.changeState(new EmptyState());
        }
    }
    public void destroy() {
        turnEmpty();
        children.clear();
    }
}
