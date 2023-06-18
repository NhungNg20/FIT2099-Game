import java.util.HashMap;
import java.util.Map;

public class Position {
    private final int number;
    private final Map<Direction, Position> neighbours;
    private Man man;

    public Position(int number) {
        this.number = number;
        this.neighbours = new HashMap<>();
    }

    public Map<Direction, Position> getNeighbours() {
        return neighbours;
    }

    public void setNeighbour(Direction direction, Position position) {
        neighbours.put(direction, position);
    }

    public void setMan(Man man) {
        this.man = man;
    }

    public int getNumber() {
        return number;
    }

    public Man getMan() {
        return man;
    }

    public void removeMan() {
        this.man = null;
    }
}
