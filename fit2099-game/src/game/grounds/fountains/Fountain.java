package game.grounds.fountains;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.Status;
import game.actions.RefillAction;
import game.drinkables.DrinkLiquid;

/**
 * An abstract class representing a fountain
 */
public abstract class Fountain extends Ground {
    /**
     * The name of the fountain
     */
    private String name;

    /**
     * Constructor.
     *
     * @param displayChar character to display for this type of terrain
     */
    public Fountain(String name, char displayChar) {
        super(displayChar);
        this.name = name;
    }

    /**
     * To return the water of the fountain
     * @return a water of type DrinkLiquid
     */
    public abstract DrinkLiquid getWater();

    /**
     * To get actions to be performed on fountain.
     * If actor stands on the fountain and can store/drink water,
     * they can refill the bottle with the fountain water
     * @param actor the Actor acting
     * @param location the current Location
     * @param direction the direction of the Ground from the Actor
     * @return actions can be performed on a Fountain from an actor
     */
    @Override
    public ActionList allowableActions(Actor actor, Location location, String direction) {
        ActionList actions = super.allowableActions(actor, location, direction);
        //whether the actor can store/drink water
        if (actor.hasCapability(Status.ItemEffect.STORE_LIQUID) && location.containsAnActor()) {
            //whether the actor is standing on the fountain
            if (location.getActor().equals(actor)) {
                actions.add(new RefillAction(this));
            }
        }
        return actions;
    }

    @Override
    public String toString() {
        return this.name + " Fountain";
    }

}
