import java.util.ArrayList;

public class Board {
    private static final int NUMBER_OF_POSITIONS = 24;
    private final ArrayList<Position> positions;
    public Board() {
        this.positions = new ArrayList<>();
        setUpPositions();
    }

    private void setUpPositions() {
        for (int i = 1; i <= NUMBER_OF_POSITIONS; i++) {
            positions.add(new Position(i));
        }
        setUpNeighbours();
    }

    public ArrayList<Position> getPositions() {
        return positions;
    }

    /**
     * Setter for neighbours of positions on the board
     */
    private void setUpNeighbours() {
        positions.get(0).setNeighbour(Direction.DOWN, positions.get(9));
        positions.get(0).setNeighbour(Direction.RIGHT, positions.get(1));

        positions.get(1).setNeighbour(Direction.DOWN, positions.get(4));
        positions.get(1).setNeighbour(Direction.RIGHT, positions.get(2));

        positions.get(2).setNeighbour(Direction.LEFT, positions.get(1));
        positions.get(2).setNeighbour(Direction.DOWN, positions.get(14));

        positions.get(3).setNeighbour(Direction.DOWN, positions.get(10));
        positions.get(3).setNeighbour(Direction.RIGHT, positions.get(4));

        positions.get(4).setNeighbour(Direction.UP, positions.get(1));
        positions.get(4).setNeighbour(Direction.DOWN, positions.get(7));
        positions.get(4).setNeighbour(Direction.LEFT, positions.get(3));
        positions.get(4).setNeighbour(Direction.RIGHT, positions.get(5));

        positions.get(5).setNeighbour(Direction.DOWN, positions.get(13));
        positions.get(5).setNeighbour(Direction.LEFT, positions.get(4));

        positions.get(6).setNeighbour(Direction.DOWN, positions.get(11));
        positions.get(6).setNeighbour(Direction.RIGHT, positions.get(7));

        positions.get(7).setNeighbour(Direction.UP, positions.get(4));
        positions.get(7).setNeighbour(Direction.LEFT, positions.get(6));
        positions.get(7).setNeighbour(Direction.RIGHT, positions.get(8));

        positions.get(8).setNeighbour(Direction.LEFT, positions.get(7));
        positions.get(8).setNeighbour(Direction.DOWN, positions.get(12));

        positions.get(9).setNeighbour(Direction.UP, positions.get(0));
        positions.get(9).setNeighbour(Direction.DOWN, positions.get(21));
        positions.get(9).setNeighbour(Direction.RIGHT, positions.get(10));

        positions.get(10).setNeighbour(Direction.UP, positions.get(3));
        positions.get(10).setNeighbour(Direction.DOWN, positions.get(18));
        positions.get(10).setNeighbour(Direction.LEFT, positions.get(9));
        positions.get(10).setNeighbour(Direction.RIGHT, positions.get(11));

        positions.get(11).setNeighbour(Direction.UP, positions.get(6));
        positions.get(11).setNeighbour(Direction.DOWN, positions.get(15));
        positions.get(11).setNeighbour(Direction.LEFT, positions.get(10));

        positions.get(12).setNeighbour(Direction.UP, positions.get(8));
        positions.get(12).setNeighbour(Direction.DOWN, positions.get(17));
        positions.get(12).setNeighbour(Direction.RIGHT, positions.get(13));

        positions.get(13).setNeighbour(Direction.UP, positions.get(5));
        positions.get(13).setNeighbour(Direction.DOWN, positions.get(20));
        positions.get(13).setNeighbour(Direction.LEFT, positions.get(12));
        positions.get(13).setNeighbour(Direction.RIGHT, positions.get(14));

        positions.get(14).setNeighbour(Direction.UP, positions.get(2));
        positions.get(14).setNeighbour(Direction.DOWN, positions.get(23));
        positions.get(14).setNeighbour(Direction.LEFT, positions.get(13));

        positions.get(15).setNeighbour(Direction.UP, positions.get(11));
        positions.get(15).setNeighbour(Direction.RIGHT, positions.get(16));

        positions.get(16).setNeighbour(Direction.DOWN, positions.get(19));
        positions.get(16).setNeighbour(Direction.LEFT, positions.get(15));
        positions.get(16).setNeighbour(Direction.RIGHT, positions.get(17));

        positions.get(17).setNeighbour(Direction.UP, positions.get(12));
        positions.get(17).setNeighbour(Direction.LEFT, positions.get(16));

        positions.get(18).setNeighbour(Direction.UP, positions.get(10));
        positions.get(18).setNeighbour(Direction.RIGHT, positions.get(19));

        positions.get(19).setNeighbour(Direction.UP, positions.get(16));
        positions.get(19).setNeighbour(Direction.DOWN, positions.get(22));
        positions.get(19).setNeighbour(Direction.LEFT, positions.get(18));
        positions.get(19).setNeighbour(Direction.RIGHT, positions.get(20));

        positions.get(20).setNeighbour(Direction.UP, positions.get(13));
        positions.get(20).setNeighbour(Direction.LEFT, positions.get(19));

        positions.get(21).setNeighbour(Direction.UP, positions.get(9));
        positions.get(21).setNeighbour(Direction.RIGHT, positions.get(22));

        positions.get(22).setNeighbour(Direction.UP, positions.get(19));
        positions.get(22).setNeighbour(Direction.LEFT, positions.get(21));
        positions.get(22).setNeighbour(Direction.RIGHT, positions.get(23));

        positions.get(23).setNeighbour(Direction.UP, positions.get(14));
        positions.get(23).setNeighbour(Direction.LEFT, positions.get(22));
    }
}
