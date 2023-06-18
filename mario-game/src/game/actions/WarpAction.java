package game.actions;

import edu.monash.fit2099.engine.actions.*;
import edu.monash.fit2099.engine.actors.*;
import edu.monash.fit2099.engine.positions.*;

public class WarpAction extends Action {

    private MoveActorAction moveAction;
    private String direction;
    private String hotKey;
    private Location moveToLocation;

    /**
     * Constructor to create an Action that will warp the Actor to a Location in a given Direction, using
     * a given menu hotkey.
     * Creates a MoveActorAction and removes actor on warp location when executed.
     *
     * @param moveToLocation the destination of the move
     * @param direction the direction of the move (to be displayed in menu)
     * @param hotKey the menu hotkey for this move
     */
    public WarpAction(Location moveToLocation, String direction, String hotKey) {
        this.moveAction = new MoveActorAction(moveToLocation, direction, hotKey);
        this.direction = direction;
        this.hotKey = hotKey;
        this.moveToLocation = moveToLocation;
    }

    /**
     * Allow the Actor to warp.
     * kills any enemy at warp location.
     * Overrides Action.execute()
     *
     * @see Action#execute(Actor, GameMap)
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return a description of the Action suitable for the menu
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        if(moveToLocation.containsAnActor()){
            moveToLocation.map().removeActor(moveToLocation.getActor());
        }
        moveToLocation.getActor();
        moveAction.execute(actor, map);
        return menuDescription(actor);
    }

    /**
     * Returns a description of this movement suitable to display in the menu.
     *
     * @param actor The actor performing the action.
     * @return a String, e.g. "Player moves east"
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " warps " + direction;
    }

    /**
     * Returns this Action's hotkey.
     *
     * @return the hotkey
     */
    @Override
    public String hotkey() {
        return hotKey;
    }
}
