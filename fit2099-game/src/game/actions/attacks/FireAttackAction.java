package game.actions.attacks;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.items.Fire;

/**
 * A class representing an attack action with fire
 */
public class FireAttackAction extends AttackAction {
    /**
     * Constructor.
     * @param target The actor being attacked
     * @param direction The direction of the attack
     */
    public FireAttackAction(Actor target, String direction) {
        super(target,direction);
    }

    /**
     * To run the attack on an actor with fire
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return the outcome of the attack as String
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        map.locationOf(target).addItem(new Fire());

        if (!target.isConscious() | invincibleActor(actor)) {
            super.dropItems(target,map);
            map.removeActor(target);
            return actor + " flames " + target + " TO DEATH!";
        }
        return actor + " sets " + target + " on fire!";
    }

    @Override
    public String menuDescription(Actor actor) {
        return super.menuDescription(actor) + " with fire!";
    }
}
