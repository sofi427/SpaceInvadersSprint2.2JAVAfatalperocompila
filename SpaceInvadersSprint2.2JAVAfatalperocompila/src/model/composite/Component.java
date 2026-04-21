package model.composite;

import java.util.List;

public interface Component {
    List<Component> getChildren();

    public default void move(int x, int y){ }

    public default List<Square> getSquares() {
        List<Square> list = new java.util.ArrayList<>();
       for (Component act : getChildren()) {
            Square sq = (Square) act;
            list.add(sq);
        }
        return list;
    }
}
