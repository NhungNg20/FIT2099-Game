import java.util.Scanner;

public final class Engine {
    private static final Scanner input = new Scanner(System.in);
    private static final String PLAY_GAME_COMMAND = "P";
    private static final String PLAY_TUTORIAL_COMMAND = "T";
    public static final String QUIT_COMMAND = "Q";
    private static final String YES_COMMAND = "Y";
    private static final String NO_COMMAND = "N";

    private Engine() {
        throw new IllegalStateException("This is the engine, it does not need to be created!");
    }

    public static void main(String[] args) {
        while (true) {
            System.out.println("------------------- MAIN MENU -------------------");
            System.out.println("Welcome to Nine Men's Morris");
            System.out.println("Select " + PLAY_GAME_COMMAND + " to play a game");
            System.out.println("Select " + PLAY_TUTORIAL_COMMAND + " for a tutorial");
            System.out.println("Select " + QUIT_COMMAND + " to exit");
            String chosenOption;
            do {
                chosenOption = input.next();
            } while (!checkForQuitAccidents(chosenOption));

            switch (chosenOption.substring(0, 1).toUpperCase()) {
                case PLAY_GAME_COMMAND -> {
                    Game game = new RealGame(new Board());
                    game.play();
                }
                case PLAY_TUTORIAL_COMMAND -> {
                    Game tutorial = new TutorialGame(new Board());
                    tutorial.play();
                }
            }
        }
    }

    /**
     * Method to prevent user from accidentally quitting
     * If it's a quit command, program will exit.
     * If user does not want to quit, e.g., accidentally clicking Q, this will return false
     * If it's a normal input, it will return true
     *
     * @param userInput user typed command
     * @return boolean
     */
    public static boolean checkForQuitAccidents(String userInput) {
        if (userInput.equalsIgnoreCase(Engine.QUIT_COMMAND)) {
            System.out.println("Are you sure you want to quit the game? [Y / N]");
            String choice = input.next().substring(0, 1);
            if (choice.equalsIgnoreCase(YES_COMMAND)) {
                System.out.println("ENDING NINE MEN'S MORRIS...");
                System.exit(0);
            } else return !choice.equalsIgnoreCase(NO_COMMAND);
        }
        return true;
    }
}