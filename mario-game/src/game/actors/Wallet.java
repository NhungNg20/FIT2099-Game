package game.actors;

/**
 * Wallet class for Player. Simple deposit, deduct and get balance features
 */
public class Wallet {
    private int balance;

    public Wallet(){
        this.balance = 0;
    }
    public Wallet(int initialBalance){
        this.balance = initialBalance;
    }
    public int getBalance(){
        return balance;
    }

    /**
     * Increases wallet's balance.
     * @param amount The integer amount to be deposited.
     **/
    public void deposite(int amount){
        balance += amount;
    }

    /**
    * Deducts from wallet's balance.
    * @param amount The integer amount to be deducted.
    * returns false if amount is greater than balance, else true
    **/
    public void deduct(int amount){
        balance -= amount;
    }
}
