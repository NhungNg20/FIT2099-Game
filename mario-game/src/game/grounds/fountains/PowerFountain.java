package game.grounds.fountains;

import game.drinkables.PowerWater;
import game.drinkables.DrinkLiquid;

/**
 * A class representing the Power Fountain
 */
public class PowerFountain extends Fountain {
    /**
     * Constructor.
     */
    public PowerFountain() {
        super("Power",'A');
    }

    /**
     * To get endless Power water at the Power fountain
     * @return a Power water of type DinkLiquid
     */
    @Override
    public DrinkLiquid getWater() {
        return new PowerWater();
    }
}
