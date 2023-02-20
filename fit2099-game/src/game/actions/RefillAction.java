package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.Status;
import game.grounds.fountains.Fountain;
import game.drinkables.DrinkLiquid;
import game.items.magical.Refillable;

/**
 * A class to represent the action of refilling an actor's water storage
 */
public class RefillAction extends Action {

    /**
     * Water to fill bottle with
     */
    private final DrinkLiquid liquid;

    /**
     * Constructor.
     *
     * @param fountain fountain that produces water
     */
    public RefillAction(Fountain fountain) { //Bottle bottle
        this.liquid = fountain.getWater();
    }

    /**
     * To get fill the actor's water storage with water
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return indication that bottle is refilled as String
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        getStorage(actor).refill(liquid);
        return actor + " " +  getStorage(actor).getRefillName() + " is refilled";
    }

    /**
     * To obtain the storage item from an actor
     * @param actor the actor performing the action
     * @return a Refillable item
     */
    public Refillable getStorage(Actor actor) {
        for (Item item : actor.getInventory()) {
            if (item.hasCapability(Status.ItemEffect.STORE_LIQUID)) {
                return (Refillable) item;
            }
        }
        return null;
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " refills " + liquid.toString();
    }
}
