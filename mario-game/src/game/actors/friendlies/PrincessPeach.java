package game.actors.friendlies;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import game.monologues.Monologue;
import game.Status;
import game.actions.UnlockHandcuffsAction;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents Princess Peach that the player is trying to save.
 */
public class PrincessPeach extends Friendly {

	/**
	 * Constructor. Creates Princess Peach.
	 */
	public PrincessPeach() {
		super("Princess Peach", 'P', 100);
		this.addCapability(Status.HANDCUFFED);  // Peach initially cannot perform any actions except speaking
	}

	@Override
	public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
		ActionList actions = new ActionList();
		// An actor who can unlock handcuffs can unlock Peach's handcuffs
		if (this.hasCapability(Status.HANDCUFFED) && otherActor.hasCapability(Status.UNLOCK_HANDCUFFS)) {
			actions.add(new UnlockHandcuffsAction(this));
		}
		return actions;
	}

	@Override
	public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
		// Peach speaks every second turn and does not move while handcuffed
		if (!this.hasCapability(Status.HANDCUFFED)) {
			// End the game. Need a better way to do this.
			for (Exit exit : map.locationOf(this).getExits()) {
				Actor player = exit.getDestination().getActor();
				if (null != player) {
					// Assume the actor is the player and remove them from the map to end the game
					map.removeActor(player);
				}
			}
			return this.speakBehaviour.getAction(this, map);
		}
		Action friendlyAction = super.playTurn(actions, lastAction, map, display);
		if (null != friendlyAction)
			return friendlyAction;
		return new DoNothingAction();
	}

	@Override
	public List<Monologue> getMonologues(Actor actor) {
		List<Monologue> monologues = new ArrayList<>();
		if (!this.hasCapability(Status.HANDCUFFED)) {
			monologues.add(new Monologue("You Win!"));
			return monologues;
		}
		monologues.add(new Monologue("Dear Mario, I'll be waiting for you..."));
		monologues.add(new Monologue("Never gonna give you up!"));
		monologues.add(new Monologue("Release me, or I will kick you!"));
		return monologues;
	}
}
