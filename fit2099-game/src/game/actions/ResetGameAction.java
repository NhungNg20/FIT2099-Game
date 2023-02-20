package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.resets.ResetManager;

/**
 * The ResetGameAction class represents an action for the player to reset the game.
 */
public class ResetGameAction extends Action {

    @Override
    public String execute(Actor actor, GameMap map) {
        // Execute the reset
        ResetManager.getInstance().run();
        return actor + " reset the game";
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " resets the game";
    }

    @Override
    public String hotkey() {
        return "r";
    }
}
