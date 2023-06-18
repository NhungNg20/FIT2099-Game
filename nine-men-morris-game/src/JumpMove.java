public class JumpMove implements Movable {
    private Position initialPosition;
    private Position destinationPosition;

    public JumpMove(Position initialPosition, Position destinationPosition) {
        this.initialPosition = initialPosition;
        this.destinationPosition = destinationPosition;
    }

    @Override
    public void execute() throws PositionOccupiedException {
        if (destinationPosition.getMan() == null) {
            destinationPosition.setMan(initialPosition.getMan());
            initialPosition.removeMan();
        } else {
            throw new PositionOccupiedException();
        }
    }
}
