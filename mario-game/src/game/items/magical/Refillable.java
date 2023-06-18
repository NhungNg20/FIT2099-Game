package game.items.magical;

import game.drinkables.DrinkLiquid;

/**
 * An interface representing refillable objects
 */
public interface Refillable {
    /**
     * To refill a refillable object with a particular liquid
     * @param liquid a particular object of type DrinkLiquid
     */
    void refill(DrinkLiquid liquid);

    /**
     * To obtain the name of the refill object
     * @return the name of the refill object as String
     */
    String getRefillName();
}
