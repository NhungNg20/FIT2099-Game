package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.*;
import game.Status;
import game.Utils;

/**
 * The JumpAction class represents a jump executed by an actor
 */
public class JumpAction extends Action {

    /**
     * Fall damage of on the target ground
     */
    private final int fallDamage;

    /**
     * Probability of jump success on the target ground
     */
    private final int jumpSuccess; // jump success probability [0,100]

    /**
     * location of the target ground
     */
    private final Location groundLocation;

    /**
     * Direction of jump
     */
    private final String direction;

    /**
     * Hotkey of the jump direction
     */
    private final String hotKey;


    /**
     * Constructor for a jump action
     * @param fallDamage fall damage of on the target ground
     * @param jumpSuccess probability of jump success on the target ground
     * @param direction - direction of jump
     * @param location location of the target ground
     */
    public JumpAction(int fallDamage, int jumpSuccess, String direction, Location location, String hotKey) {
        this.fallDamage = fallDamage;
        this.jumpSuccess = jumpSuccess;
        this.direction = direction;
        this.groundLocation = location;
        this.hotKey = hotKey;
    }

    /**
     * To perform the jump action
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return The outcome of the jump as String
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        int randSample = Utils.randInt(100);
        //if actor ate a Super Mushroom, actor can jump freely
        if (actor.hasCapability(Status.ItemEffect.SUPERMUSH)) {
            randSample = 0;
        }
        if (randSample <= this.jumpSuccess) {
            map.moveActor(actor, groundLocation);
            return actor + " successfully jumped on " + groundLocation.getGround();
        } else {
            actor.hurt(fallDamage);
            return actor + " fell, minus " + this.fallDamage + " HP ";
        }
    }

    /**
     * To retrieve the coordinates of the current ground
     * @return the location coordinate as String
     */
    public String getCoordinate(){
        return "(" + groundLocation.x() + "," + groundLocation.y() + ")";
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " jumps on " + groundLocation.getGround() + " " + getCoordinate() + " at " + direction;
    }
    /**
     * Returns this Action's hotkey.
     * @return the hotkey
     */
    @Override
    public String hotkey() {
        return hotKey;
    }
}
