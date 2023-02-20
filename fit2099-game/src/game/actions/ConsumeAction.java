package game.actions;

import edu.monash.fit2099.engine.actions.*;
import edu.monash.fit2099.engine.actors.*;
import edu.monash.fit2099.engine.items.*;
import edu.monash.fit2099.engine.positions.*;
import game.items.*;
import jdk.jshell.execution.*;

public class ConsumeAction extends Action {

    private final Consumable consumable;

    /**
     * An action which initiates the effects of a consumable item. Instantiate with the consumable
     * @param consumable
     */
    public ConsumeAction(Consumable consumable) {
        this.consumable = consumable;
    }

    /**
     * Consumable item is consumed. calls ConsumableItem.consume() where unique behaviours are implemented.
     * Remove item from Location if it is on the ground.
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return description of executed action
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        map.locationOf(actor).removeItem((Item) consumable);        // if there, remove consumable from actor location.
        if (!actor.getInventory().contains((Item)consumable)) {     // if item not in inventory, i.e is on ground. add to inventory
            actor.addItemToInventory((Item)consumable);
        }
        consumable.consume(actor);
        return menuDescription(actor);
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " consumes " + consumable ;
    }

}
