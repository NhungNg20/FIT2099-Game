package game.items.magical;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import game.Status;
import game.actions.ConsumeAction;
import game.drinkables.DrinkLiquid;
import game.items.Consumable;

import java.util.ArrayList;
import java.util.Stack;

/**
 * A class representing a bottle item
 */
public class Bottle extends Item implements Consumable, Refillable {
    /**
     * A stack representing the layers of water
     */
    private final Stack<DrinkLiquid> waterLayers;

    /**
     * Constructor.
     * The bottle can store liquid therefore given this status
     * The bottle can be consumed therefore given this status
     */
    public Bottle() {
        super("Bottle", 'b', false);
        this.waterLayers = new Stack<>();
        this.addCapability(Status.ItemEffect.STORE_LIQUID);
        addAction(new ConsumeAction(this));
    }

    /**
     * To be able to refill the bottle with a liquid
     * @param liquid the liquid being filled into the bottle
     */
    @Override
    public void refill(DrinkLiquid liquid) {
        this.waterLayers.push(liquid);
    }

    /**
     * To get the name of the refill item
     * @return the name of the bottle as String
     */
    @Override
    public String getRefillName() {
        return "Bottle";
    }

    /**
     * To consume the water in the bottle
     * First water in the waterLayer is returned
     * @param actor Actor which is consuming the item
     */
    @Override
    public void consume(Actor actor) {
        if (!waterLayers.isEmpty()) {
            waterLayers.pop().consume(actor);
        }
    }

    @Override
    public void resetEffect(Actor actor) {}

    @Override
    public String toString() {
        return "Bottle" + waterLayers;
    }
}
