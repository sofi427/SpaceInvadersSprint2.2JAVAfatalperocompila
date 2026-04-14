package model.composite;

import java.util.ArrayList;
import java.util.List;

public interface Component {
    List<Component> getChildren();

    default ArrayList<Square> getSquares() {
        ArrayList<Square> result = new ArrayList<>();
        for (Component child : getChildren()) {
            result.addAll(child.getSquares());
        }
        return result;
    }
}
