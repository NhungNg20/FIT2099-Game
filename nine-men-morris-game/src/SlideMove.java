public class SlideMove implements Movable {
    private Position initialPosition;
    private Direction direction;
    public SlideMove(Position initialPosition, Direction direction) {
        this.initialPosition = initialPosition;
        this.direction = direction;
    }

    @Override
    public void execute() throws PositionOccupiedException {
        Position destination = initialPosition.getNeighbours().get(direction);
        if (destination.getMan() == null) {
            destination.setMan(initialPosition.getMan());
            initialPosition.removeMan();
        } else {
            throw new PositionOccupiedException();
        }
    }
}
