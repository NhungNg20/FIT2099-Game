package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.*;
import game.Status;
import game.actions.attacks.AttackAction;
import game.actions.attacks.GenAttackAction;

/**
 * The AttackBehaviour class represents a behaviour to attack an actor
 */
public class AttackBehaviour implements Behaviour {

    /**
     * Constructor for an attack behaviour
     */
    public AttackBehaviour() {}

    /**
     * To attack the player in actor's surroundings
     * if the target is found, attack the target
     * @param actor the Actor acting
     * @param map the GameMap containing the Actor
     * @return the attack on player as Action, null if player is not in its surroundings
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        for (Exit exit : map.locationOf(actor).getExits()) {
            Actor target = exit.getDestination().getActor();
            if (null != target && target.hasCapability(Status.HOSTILE_TO_ENEMY)) {
                return new GenAttackAction(target, exit.getName());
            }
        }
        return null;
    }
}
