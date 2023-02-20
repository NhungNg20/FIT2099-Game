package game.items;

import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Location;
import game.resets.ResetManager;
import game.resets.Resettable;

/**
 * The Coin class represents a coin item the player can collect and trade with.
 */
public class Coin extends Item implements Resettable {

    /**
     * The coin's monetary value.
     */
    private final int value;

    /**
     * Signals whether to perform a reset on the next game tick.
     */
    private boolean resetFlag;

    /**
     * Constructor. Initializes a new Coin object with the given value.
     * Registers the instance for a global reset.
     * @param coinValue The monetary value of the coin.
     */
    public Coin(int coinValue) {
        super("Coin", '$', true);  // portability
        this.registerInstance();    // Register coin for global reset
        this.resetFlag = false;
        value = coinValue;
    }

    /**
     * Constructor. Initializes a new Coin object with the given value at the given location.
     * Registers the instance for a global reset.
     * @param coinValue The monetary value of the coin.
     * @param location The coin's Location object.
     */
    public Coin(int coinValue, Location location) {
        super("Coin", '$', true);
        this.registerInstance();    // Register coin for global reset
        this.resetFlag = false;
        this.value = coinValue;
    }

    /**
     * Retrieves the coin's value.
     * @return The coin's monetary value.
     */
    public int getValue() {
        return value;
    }

    @Override
    public void tick(Location currentLocation) {
        if (this.resetFlag) {
            // All coins on the ground are removed during a reset
            currentLocation.removeItem(this);
            this.resetFlag = false;
        }
    }

    @Override
    public void resetInstance() {
        this.resetFlag = true;
    }
}
