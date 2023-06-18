package game.grounds.fountains;

import game.drinkables.HealingWater;
import game.drinkables.DrinkLiquid;

/**
 * A class representing the Health Fountain
 */
public class HealthFountain extends Fountain {
    /**
     * Constructor.
     */
    public HealthFountain() {
        super("Health",'H');
    }

    /**
     * To get the endless Healing water of the Health fountain
     * @return a Healing water of type DrinkLiquid
     */
    @Override
    public DrinkLiquid getWater() {
        return new HealingWater();
    }

}
