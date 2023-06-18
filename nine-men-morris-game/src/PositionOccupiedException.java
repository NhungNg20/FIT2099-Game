public class PositionOccupiedException extends Exception {
    public PositionOccupiedException() {
        super("This position is already occupied, please choose an empty.");
    }
}
