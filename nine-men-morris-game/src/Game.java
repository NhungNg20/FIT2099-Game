import java.util.Scanner;

public abstract class Game {
    protected static final int NUMBER_OF_MEN_PER_PLAYER = 4;
    protected static final String PLAYER_ONE = "One";
    protected static final String PLAYER_TWO = "Two";
    protected static final int LAST_THREE_MEN = 3;
    protected static final int NUMBER_OF_MEN_AT_START = 0;
    protected final Board board;
    protected final Player playerOne;
    protected final Player playerTwo;
    protected final Display display;
    protected final Scanner input;

    public Game(Board board) {
        this.board = board;
        display = new Display();
        input = new Scanner(System.in);
        playerOne = new Person();
        playerTwo = new Person();
    }

    public abstract void play();

    public abstract void phaseOne();

    public abstract void phaseTwo();

    boolean playMove(Movable move) {
        try {
            move.execute();
            return true;
        } catch (PositionOccupiedException e) {
            System.out.println("Position occupied, please choose an empty position.");
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    void setUp() {
        playerOne.setSymbol(chooseSymbol(PLAYER_ONE));
        playerTwo.setSymbol(chooseSymbol(PLAYER_TWO));
    }


    private char chooseSymbol(String playerNumber) {
        display.print(board, NUMBER_OF_MEN_AT_START, NUMBER_OF_MEN_AT_START, PLAYER_ONE, PLAYER_TWO);
        System.out.println();
        System.out.println("----------------- Game: Set Up ------------------");
        System.out.println("Instruction: Choose a character to represent your Men");
        System.out.println("Options:");
        System.out.println("Select a character for Player " + playerNumber + " in range [A - Z]:");
        while (true) {
            String chosenChar = input.next();
            if (Character.isLetter(chosenChar.charAt(0))) {
                return chosenChar.charAt(0);
            }
            System.out.println("Invalid character, please choose a single letter between A to Z.");
        }
    }

    /**
     * Method to check for a mill
     *
     * @param newPosition The position the player move a Man to
     * @param player      The player being checked for
     * @return boolean
     */
    protected boolean checkForMill(Position newPosition, Player player) {
        Position up = newPosition.getNeighbours().get(Direction.UP);
        Position farUp = null;
        if (up != null) {
            farUp = up.getNeighbours().get(Direction.UP);
        }

        Position down = newPosition.getNeighbours().get(Direction.DOWN);
        Position farDown = null;
        if (down != null) {
            farDown = down.getNeighbours().get(Direction.DOWN);
        }

        Position left = newPosition.getNeighbours().get(Direction.LEFT);
        Position farLeft = null;
        if (left != null) {
            farLeft = left.getNeighbours().get(Direction.LEFT);
        }

        Position right = newPosition.getNeighbours().get(Direction.RIGHT);
        Position farRight = null;
        if (right != null) {
            farRight = right.getNeighbours().get(Direction.RIGHT);
        }

        if (left != null && right != null) {
            if (left.getMan() != null && right.getMan() != null) {
                return left.getMan().getPlayer().equals(player) && right.getMan().getPlayer().equals(player);
            }
        }
        if (up != null && down != null) {
            if (up.getMan() != null && down.getMan() != null) {
                return up.getMan().getPlayer().equals(player) && down.getMan().getPlayer().equals(player);
            }
        }
        if (up != null && farUp != null) {
            if (up.getMan() != null && farUp.getMan() != null) {
                return up.getMan().getPlayer().equals(player) && farUp.getMan().getPlayer().equals(player);
            }
        }
        if (down != null && farDown != null) {
            if (down.getMan() != null && farDown.getMan() != null) {
                return down.getMan().getPlayer().equals(player) && farDown.getMan().getPlayer().equals(player);
            }
        }
        if (left != null && farLeft != null) {
            if (left.getMan() != null && farLeft.getMan() != null) {
                return left.getMan().getPlayer().equals(player) && farLeft.getMan().getPlayer().equals(player);
            }
        }
        if (right != null && farRight != null) {
            if (right.getMan() != null && farRight.getMan() != null) {
                return right.getMan().getPlayer().equals(player) && farRight.getMan().getPlayer().equals(player);
            }
        }
        return false;
    }
}
