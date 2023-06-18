
public class PlaceMove implements Movable {
    private final Man man;
    private final Position destination;

    public PlaceMove(Position destination, Man man) {
        this.destination = destination;
        this.man = man;
    }

    @Override
    public void execute() throws PositionOccupiedException {
        if (destination.getMan() == null) {
            destination.setMan(man);
        } else {
            throw new PositionOccupiedException();
        }
    }
}
