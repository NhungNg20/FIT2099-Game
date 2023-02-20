package game.drinkables;

import edu.monash.fit2099.engine.actors.Actor;

/**
 * A class representing healing water
 */
public class HealingWater extends DrinkLiquid {
    /**
     * The amount of increase HP for an actor
     */
    private final int HP_HEAL = 50;

    /**
     * Constructor
     */
    public HealingWater() {
        super("Healing");
    }

    /**
     * To consume the healing water.
     * Actor's HP will be increased
     * @param actor Actor which is consuming the water
     */
    @Override
    public void consume(Actor actor) {
        actor.heal(HP_HEAL);
    }
}
