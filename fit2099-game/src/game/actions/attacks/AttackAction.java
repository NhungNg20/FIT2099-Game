package game.actions.attacks;


import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.*;
import edu.monash.fit2099.engine.items.Item;
import game.Status;

import java.util.ArrayList;

/**
 * An abstract class representing action for attacking other Actors.
 */
public abstract class AttackAction extends Action {

    /**
     * The Actor that is to be attacked
     */
    protected final Actor target;

    /**
     * The direction of incoming attack.
     */
    private final String direction;

    /**
     * Constructor.
     *
     * @param target The actor to attack
     */
    public AttackAction(Actor target, String direction) {
        this.target = target;
        this.direction = direction;
    }

    /**
     * To drop all items in target's inventory
     * @param actor The attacked target
     * @param map The map of the current target
     */
    protected void dropItems(Actor actor, GameMap map) {
        Action dropAction;
        for (Item item : new ArrayList<>(actor.getInventory())) {
            dropAction = item.getDropAction(actor);
            if (dropAction == null) {
                item.togglePortability(); //Consumed item are in inv. but not portable
                item.getDropAction(actor).execute(actor,map);
                item.togglePortability();
            } else
                dropAction.execute(actor,map);
        }

    }

    /**
     * To determine if an actor has Power Star
     * @param actor the actor performing action
     * @return whether the actor has Power Star as Boolean
     */
    public boolean invincibleActor(Actor actor) {
        return actor.hasCapability(Status.ItemEffect.POWERSTAR);
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " attacks " + target + " at " + direction;
    }
}
