import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class RealGame extends Game {
    private static final String CHOOSE_ANOTHER_MAN_COMMAND = "X";
    private static final String TOGGLE_HINT_COMMAND = "H";
    private Player turn;
    private final List<Man> currentMen; // All the men currently in the game

    public RealGame(Board board) {
        super(board);
        turn = playerOne;
        currentMen = new ArrayList<>();
    }

    @Override
    public void play() {
        setUp();
        phaseOne();
        phaseTwo();
    }

    @Override
    public void phaseOne() {
        int countNumberOfMenPlaced = 0;
        while (countNumberOfMenPlaced < NUMBER_OF_MEN_PER_PLAYER * 2) {
            display.print(board, checkNoOfMen(playerOne), checkNoOfMen(playerTwo),
                    String.valueOf(playerOne.getSymbol()), String.valueOf(playerTwo.getSymbol()));
            System.out.println();
            System.out.println("---------------- Game: Phase One ----------------");
            System.out.println("Turn: Player " + turn.getSymbol());
            System.out.println("Instruction: Place all 9 Men on a number on the board.");
            System.out.println("Options:");
            System.out.println("Select " + Engine.QUIT_COMMAND + " to Quit");
            System.out.println("Select an unoccupied number for your Man");
            Man newMan = new Man(turn);

            while (true) {
                String chosenPosition = input.next();
                if (!Engine.checkForQuitAccidents(chosenPosition)) {
                    continue;
                }
                try {
                    Position position = board.getPositions().get(Integer.parseInt(chosenPosition) - 1);
                    if (playMove(new PlaceMove(position, newMan))) {
                        currentMen.add(newMan);
                        if (checkForMill(position, turn)) {
                            removeManAfterMill();
                        }
                        turn = turn == playerOne ? playerTwo : playerOne;
                        countNumberOfMenPlaced++;
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("Please choose an empty position within between 1 and 24.");
                }
            }
        }
    }

    @Override
    public void phaseTwo() {
        while (true) {
            display.print(board, checkNoOfMen(playerOne), checkNoOfMen(playerTwo),
                    String.valueOf(playerOne.getSymbol()), String.valueOf(playerTwo.getSymbol()));
            System.out.println();
            if (checkEndOfGame(playerOne) || checkEndOfGame(playerTwo)) {
                return;
            }
            System.out.println("---------------- Game: Phase Two ----------------");
            System.out.println("Turn: Player " + turn.getSymbol());
            System.out.println("Instruction: Move your Men to form a Mill.");
            System.out.println("Options:");
            System.out.println("Select " + Engine.QUIT_COMMAND + " to Quit");
            System.out.println("Select a Man to move (e.g., 13) ");
            Position currentPosition = chooseManToMove();

            System.out.println("Select " + CHOOSE_ANOTHER_MAN_COMMAND + " to choose another Man");
            System.out.println("Select " + TOGGLE_HINT_COMMAND + " for a hint for possible positions to slide to");
            System.out.println(checkNoOfMen(turn) > LAST_THREE_MEN ?
                    "Select a direction for Man " + currentPosition.getNumber() + " to jump to: [U: UP, D: DOWN, L: LEFT, R: RIGHT]" :
                    "Only 3 Men left! Select any number for Man " + currentPosition.getNumber() + " to JUMP to (e.g., 13)");
            moveManToPosition(currentPosition);
        }
    }

    /**
     * Help player move a man to another position
     *
     * @param currentPosition where the man to move is currently at
     */
    private void moveManToPosition(Position currentPosition) {
        while (true) {
            Movable move;
            Position chosenManNewPosition;
            String chosenMove = input.next();

            if (!Engine.checkForQuitAccidents(chosenMove)) {
                continue;
            }

            // Check if player wants to choose another man
            if (chosenMove.equalsIgnoreCase(CHOOSE_ANOTHER_MAN_COMMAND)) {
                break;
            // Check if player wants to see hints
            } else if (chosenMove.equalsIgnoreCase(TOGGLE_HINT_COMMAND)) {
                if (checkNoOfMen(turn) == 3) {
                    List<Integer> possiblePlaces = board.getPositions().stream()
                            .filter(position -> position.getMan() == null)
                            .map(Position::getNumber)
                            .collect(Collectors.toList());
                    System.out.println("Possible positions to jump to " + possiblePlaces);
                } else {
                    List<Direction> possibleDirections = currentPosition.getNeighbours().keySet().stream()
                            .filter(key -> currentPosition.getNeighbours().get(key).getMan() == null)
                            .collect(Collectors.toList());
                    System.out.println("Possible positions to slide to: " + possibleDirections);
                }
                continue;
            }

            // Define an appropriate move
            try {
                if (checkNoOfMen(turn) > LAST_THREE_MEN) {
                    Direction direction = switch (chosenMove.toUpperCase()) {
                        case "U" -> Direction.UP;
                        case "D" -> Direction.DOWN;
                        case "L" -> Direction.LEFT;
                        case "R" -> Direction.RIGHT;
                        default -> null;
                    };
                    move = new SlideMove(currentPosition, direction);
                    chosenManNewPosition = currentPosition.getNeighbours().get(direction);
                } else {
                    chosenManNewPosition = board.getPositions().get(Integer.parseInt(chosenMove) - 1);
                    move = new JumpMove(currentPosition, chosenManNewPosition);
                }
            } catch (Exception e) {
                System.out.println("Please move to a valid position.");
                continue;
            }

            // Play the move and check if a mill was formed
            if (playMove(move)) {
                if (checkForMill(chosenManNewPosition, turn)) {
                    removeManAfterMill();
                }
                turn = turn == playerOne ? playerTwo : playerOne;
                return;
            }
        }
    }

    /**
     * Help player choose a man to move
     *
     * @return Position of the chosen man
     */
    private Position chooseManToMove() {
        while (true) {
            String chosenMan = input.next();
            if (!Engine.checkForQuitAccidents(chosenMan)) {
                continue;
            }
            try {
                Position currentPosition = board.getPositions().get(Integer.parseInt(chosenMan) - 1);
                if (currentPosition.getMan() != null && currentPosition.getMan().getPlayer().equals(turn)) {
                    return currentPosition;
                } else {
                    System.out.println("Please choose a position that belongs to YOU.");
                }
            } catch (Exception e) {
                System.out.println("Please choose a position between 1 and 24.");
            }
        }
    }

    private int checkNoOfMen(Player player) {
        return (int) currentMen.stream().filter(man -> man.getPlayer().equals(player)).count();
    }

    /**
     * Checking if game is (one player cannot move anymore or only has 2 Men left)
     *
     * @param player the player getting checked
     * @return boolean
     */
    private boolean checkEndOfGame(Player player) {
        AtomicInteger noOfAvailableNeighbours = new AtomicInteger();
        board.getPositions().stream().filter(position -> {
            if (position.getMan() != null) {
                return position.getMan().getPlayer().equals(player);
            }
            return false;
        }).forEach(position -> position.getNeighbours().values()
                .forEach(neighbour -> noOfAvailableNeighbours.addAndGet(neighbour.getMan() == null ? 1 : 0)));

        if (noOfAvailableNeighbours.get() == 0 || checkNoOfMen(player) < LAST_THREE_MEN) {
            System.out.println("GAME OVER. Player " + (player == playerOne ? playerTwo.getSymbol() : playerOne.getSymbol()) + " won.");
            return true;
        }
        return false;
    }

    /**
     * Method to remove an opponent's man after forming a mill
     * Will also check if all opponent's Men are part of a mill,
     * if so, any Man can be removed
     */
    private void removeManAfterMill() {
        Player opponent = turn == playerOne ? playerTwo : playerOne;
        boolean allOpponentsPositionsFormMill = checkPossiblePositionsToRemove(opponent).isEmpty();
        System.out.println("You have made a mill.");
        System.out.println(allOpponentsPositionsFormMill ?
                "Select any man from your opponent to remove (e.g., 13)" :
                "Select a man from your opponent to remove that's not part of a mill (e.g., 13)");
        while (true) {
            Position positionToRemove;
            String chosenMan = input.next();
            try {
                positionToRemove = board.getPositions().get(Integer.parseInt(chosenMan) - 1);
            } catch (Exception e) {
                System.out.println("Please choose a valid number of a Man.");
                continue;
            }

            if (!allOpponentsPositionsFormMill) {
                if (positionToRemove.getMan() != null && positionToRemove.getMan().getPlayer().equals(opponent)) {
                    if (!checkForMill(positionToRemove, opponent)) {
                        currentMen.remove(positionToRemove.getMan());
                        playMove(new RemoveMove(positionToRemove));
                        break;
                    } else {
                        System.out.println("Please choose an opponent's man not part of a mill.");
                    }
                } else {
                    System.out.println("Please choose a position occupied by a man of the opponent.");
                }
            } else {
                currentMen.remove(positionToRemove.getMan());
                playMove(new RemoveMove(positionToRemove));
                break;
            }
        }
    }

    /**
     * Method to check the number of men available to remove
     *
     * @param opponent opponent
     * @return a list of all possible positions to remove man from
     */
    private List<Position> checkPossiblePositionsToRemove(Player opponent) {
        return board.getPositions().stream().filter(position -> {
            if (position.getMan() != null) {
                if (position.getMan().getPlayer() == opponent) {
                    return !checkForMill(position, opponent);
                }
            }
            return false;
        }).collect(Collectors.toList());
    }
}
