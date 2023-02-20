package game.drinkables;

import edu.monash.fit2099.engine.actors.Actor;
import game.Status;

/**
 * A class representing Power water
 */
public class PowerWater extends DrinkLiquid {

    /**
     * Constructor
     */
    public PowerWater() {
        super("Power");
    }

    /**
     * To allow an actor to consume Power water
     * Actor will receive a power buff for intrinsic attacks
     * @param actor Actor which is consuming the item
     */
    @Override
    public void consume(Actor actor) {
        actor.addCapability(Status.POWER_UP);
    }
}
