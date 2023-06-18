import java.util.ArrayList;

public class Display {
    public void print(Board board, int playerOneMen, int playerTwoMen, String playerOneSymbol, String playerTwoSymbol) {
        ArrayList<Position> positions = board.getPositions();

        System.out.println("--------------- NINE MEN'S MORRIS ---------------");
        System.out.printf("Player %s: %s / 9 Men             Player %s: %s / 9 Men",
                playerOneSymbol, playerOneMen, playerTwoSymbol, playerTwoMen);
        System.out.println(" ");
        System.out.println(" " + getDisplayOfPosition(positions.get(0)) +
                " ------------------- " + getDisplayOfPosition(positions.get(1)) +
                " ------------------- " + getDisplayOfPosition(positions.get(2))
        );
        System.out.println(" |                      |                      |");
        System.out.println(" |                      |                      |");
        System.out.println(" |         " + getDisplayOfPosition(positions.get(3)) +
                "---------- " + getDisplayOfPosition(positions.get(4)) +
                "---------- " + getDisplayOfPosition(positions.get(5)) +
                "        |"
        );
        System.out.println(" |         |            |            |         |");
        System.out.println(" |         |     " + getDisplayOfPosition(positions.get(6)) +
                " --- " + getDisplayOfPosition(positions.get(7)) +
                " --- " + getDisplayOfPosition(positions.get(8)) +
                "    |         |"
        );
        System.out.println(" |         |     |             |     |         |");
        System.out.println("" + getDisplayOfPosition(positions.get(9)) +
                " ------ " + getDisplayOfPosition(positions.get(10)) +
                " - " + getDisplayOfPosition(positions.get(11)) +
                "          " + getDisplayOfPosition(positions.get(12)) +
                " - " + getDisplayOfPosition(positions.get(13)) +
                " ------ " + getDisplayOfPosition(positions.get(14))
                );
        System.out.println(" |         |     |             |     |         |");
        System.out.println(" |         |     " + getDisplayOfPosition(positions.get(15)) +
                " -- " + getDisplayOfPosition(positions.get(16)) +
                " -- " + getDisplayOfPosition(positions.get(17)) +
                "   |         |"
        );
        System.out.println(" |         |            |            |         |");
        System.out.println(" |         " + getDisplayOfPosition(positions.get(18)) +
                "--------- " + getDisplayOfPosition(positions.get(19)) +
                "--------- " + getDisplayOfPosition(positions.get(20)) +
                "       |"
        );
        System.out.println(" |                      |                      |");
        System.out.println(" |                      |                      |");
        System.out.println(" " + getDisplayOfPosition(positions.get(21)) +
                " ------------------ " + getDisplayOfPosition(positions.get(22)) +
                " ------------------ " + getDisplayOfPosition(positions.get(23))
        );
    }

    private String getDisplayOfPosition(Position position) {
        String result = position.getNumber() + "";
        if (position.getMan() == null) {
            result += "_";
        } else {
            result += position.getMan().getPlayer().getSymbol();
        }
        return result;
    }
}
