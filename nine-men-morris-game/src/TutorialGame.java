import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TutorialGame extends Game {
    // Phase One Inputs
    private static final int[] PHASE_ONE_POSITIONS =
            {7, 9, 12, 13, 17, 6, 11, 21, 1, 2, 3, 20, 18, 23, 24, 22, 19, 5};
    // Phase Two Inputs
    private static final String MAN_TO_SLIDE = "17";
    private static final String DIRECTION_TO_SLIDE = "L";
    private static final String POSITION_TO_JUMP_FROM = "3";
    private static final String POSITION_TO_JUMP_TO = "7";
    private static final String POSITION_TO_REMOVE_FOR_WIN = "9";
    private static final int[] PHASE_TWO_POSITIONS_TO_REMOVE =
            {7, 6, 11, 21, 1, 2, 20, 18, 23, 24, 22, 10};
    private final Player tutorialMainPlayer;

    public TutorialGame(Board board) {
        super(board);
        tutorialMainPlayer = playerOne;
    }

    @Override
    public void play() {
        System.out.println("---------Welcome to Nine Men's Morris Tutorial-------\n");
        System.out.println("""
                We will start by choosing the symbol that will represent\s
                each player's men on the board.
                """);
        setUp();
        phaseOne();
        phaseTwo();
        display.print(board, 4, 2,
                String.valueOf(playerOne.getSymbol()), String.valueOf(playerTwo.getSymbol()));
        System.out.println("GAME OVER. Player " + tutorialMainPlayer.getSymbol() + " Won \n");
        System.out.println("Player " + tutorialMainPlayer.getSymbol() + " won as they had more than 3 Men left and able to move \n" +
                "while their opponent only had 2 men left. \n");
        System.out.println("Tutorial Over.");
    }

    @Override
    public void phaseOne() {
        // Shows the main play of phase one, aka, placing Man on the board
        display.print(board, 0, 0,
                String.valueOf(playerOne.getSymbol()), String.valueOf(playerTwo.getSymbol()));
        System.out.println("\n---------------- Tutorial: Phase One ----------------");
        System.out.println("Turn: Player " + tutorialMainPlayer.getSymbol());
        System.out.println("""
                Instruction: After choosing the symbol for our men, we move to Phase One where each player\s
                will take turns placing 9 men on positions of their choice on the board.
                """);
        System.out.println("""
                NOTE: Players may also want to align 3 of their men horizontally or vertically to form a Mill.
                If a Mill is form, the player can eliminate one of their opponent's man.
                """);
        System.out.println();
        System.out.println("Options:");
        System.out.println("Select " + Engine.QUIT_COMMAND + " to Quit");
        System.out.println("Select an unoccupied number for your Man:");
        validateUserInput(String.valueOf(PHASE_ONE_POSITIONS[0]));

        // Fast-forward the tutorial to phase two by placing the rest of Man
        Player turn = tutorialMainPlayer;
        for (int number: PHASE_ONE_POSITIONS) {
            Position position = board.getPositions().get(number - 1);
            Man newMan = new Man(turn);
            playMove(new PlaceMove(position, newMan));
            turn = turn == playerOne ? playerTwo : playerOne;
        }
    }

    @Override
    public void phaseTwo() {
        // Shows the main play of phase two, aka, sliding Man
        display.print(board, NUMBER_OF_MEN_PER_PLAYER, NUMBER_OF_MEN_PER_PLAYER,
                String.valueOf(playerOne.getSymbol()), String.valueOf(playerTwo.getSymbol()));
        System.out.println("\n---------------- Tutorial: Phase Two ----------------");
        System.out.println("Turn: Player " + tutorialMainPlayer.getSymbol());
        System.out.println("""
                Instruction: After placing all our men on the board, we move to Phase Two where each player\s
                will take turns sliding one man to an unoccupied position. The players' aim is to align 3 of their men\s
                horizontally or vertically to form a Mill, after which they can eliminate one of their opponent's man.\s
                """);
        System.out.println("Select " + Engine.QUIT_COMMAND + " to Quit");
        System.out.println("Select a Man to move");
        String chosenPosition = validateUserInput(MAN_TO_SLIDE);
        Position initialPosition = board.getPositions().get(Integer.parseInt(chosenPosition) - 1);

        System.out.println("Select a direction for Man "
                + MAN_TO_SLIDE + " to jump to: [U: UP, D: DOWN, L: LEFT, R: RIGHT]");
        validateUserInput(DIRECTION_TO_SLIDE);
        playMove(new SlideMove(initialPosition, Direction.LEFT));

        // Show a mill example
        formingMillExample("2");

        // Show a jump example
        jumpExample();
    }

    /**
     * Show player what happens when jumping is enabled
     */
    public void jumpExample() {
        // Fast-forward the tutorial so jumping is allowed by reducing to 3 men per player
        List<Integer> listOfPositionsToRemove = Arrays.stream(PHASE_TWO_POSITIONS_TO_REMOVE)
                .boxed()
                .collect(Collectors.toList());
        board.getPositions().forEach(position -> {
                    if (position.getMan() != null && listOfPositionsToRemove.contains(position.getNumber())) {
                        playMove(new RemoveMove(position));
                    }
                });

        display.print(board, NUMBER_OF_MEN_PER_PLAYER, NUMBER_OF_MEN_PER_PLAYER,
                String.valueOf(playerOne.getSymbol()), String.valueOf(playerTwo.getSymbol()));
        System.out.println("\n---------------- Tutorial: Phase Two ----------------");
        System.out.println("Turn: Player " + tutorialMainPlayer.getSymbol());
        System.out.println("""
                Instruction: Once a player only has 3 men left, they are allowed to jump to any unoccupied position.\s
                If a player only has 2 men left or they are unable to move, they lose.\s
                """);
        System.out.println("Select " + Engine.QUIT_COMMAND + " to Quit");
        System.out.println("Select a Man to move");

        String chosenPosition = validateUserInput(POSITION_TO_JUMP_FROM);
        Position initialPosition = board.getPositions().get(Integer.parseInt(chosenPosition) - 1);

        System.out.println("Only 3 Men left! Select any number for Man " + chosenPosition + " to JUMP to");
        String chosenDestination = validateUserInput(POSITION_TO_JUMP_TO);
        Position destinationPosition = board.getPositions().get(Integer.parseInt(chosenDestination) - 1);
        playMove(new JumpMove(initialPosition, destinationPosition));

        // Show a game winning mill
        formingMillExample(POSITION_TO_REMOVE_FOR_WIN);
    }

    /**
     * Show player what happens after a mill is formed
     *
     * @param validInput the predefined input of the tutorial
     */
    private void formingMillExample(String validInput) {
        System.out.println("You have made a mill.");
        System.out.println("Select a man from your opponent to remove that's not part of a mill.");

        String chosenManToRemove = validateUserInput(validInput);
        Position positionToRemove = board.getPositions().get(Integer.parseInt(chosenManToRemove) - 1);
        playMove(new RemoveMove(positionToRemove));
    }

    /**
     * To verify the predefined input of the tutorial against user's actual input
     * @param validInput predefined input of the tutorial
     * @return user's validated input
     */
    private String validateUserInput(String validInput) {
        System.out.printf("Tutorial valid input: %s\n", validInput);
        while (true) {
            String choice = input.next();
            if (Engine.checkForQuitAccidents(choice)) {
                if (validInput.equalsIgnoreCase(choice)) {
                    return choice;
                }
                System.out.println("Incorrect input, please enter " + validInput);
            }
        }
    }
}
