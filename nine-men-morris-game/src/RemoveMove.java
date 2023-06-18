public class RemoveMove implements Movable {
    private final Position currentPosition;

    public RemoveMove(Position currentPosition) {
        this.currentPosition = currentPosition;
    }

    @Override
    public void execute() throws PositionOccupiedException {
        if (currentPosition.getMan() != null) {
            currentPosition.removeMan();
        } else {
            throw new PositionOccupiedException();
        }
    }
}
