package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.Status;

/**
 * An action which allows a player to unlock the handcuffs on another actor.
 */
public class UnlockHandcuffsAction extends Action {

	/**
	 * The handcuffed actor.
	 */
	private final Actor handcuffedActor;

	/**
	 * Constructs an action to remove the handcuffs on the given actor.
	 * @param handcuffedActor The actor whose handcuffs are to be removed.
	 */
	public UnlockHandcuffsAction(Actor handcuffedActor) {
		this.handcuffedActor = handcuffedActor;
	}

	@Override
	public String execute(Actor actor, GameMap map) {
		// Check if the actor executing can remove handcuffs
		if (actor.hasCapability(Status.UNLOCK_HANDCUFFS)) {
			this.handcuffedActor.removeCapability(Status.HANDCUFFED);
			return "Removed the handcuffs of " + this.handcuffedActor;
		}
		return actor + " cannot remove handcuffs!";
	}

	@Override
	public String menuDescription(Actor actor) {
		return "Unlock " + this.handcuffedActor + "'s handcuffs";
	}
}
