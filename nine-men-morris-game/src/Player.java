public abstract class Player {
    private char symbol;

    public Player(char symbol) {
        this.symbol = symbol;
    }

    public Player(){}

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }
}
