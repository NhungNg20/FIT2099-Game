package game.actors.friendlies;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import game.Status;
import game.behaviours.Behaviour;
import game.behaviours.SpeakBehaviour;
import game.monologues.Speakable;
import java.util.ArrayList;
import java.util.List;

public abstract class Friendly extends Actor implements Speakable {

	/**
	 * The non-speaking behaviours the friendly acts out.
	 */
	protected List<Behaviour> behaviours;

	/**
	 * The SpeakBehaviour is separate from the other behaviours since it must be chosen every second turn (according
	 * to the requirements).
	 */
	protected final SpeakBehaviour speakBehaviour;

	/**
	 * Signals whether the speakBehaviour should be chosen for this turn.
	 */
	protected boolean spokeLastTurn;

	/**
	 * Signals whether to perform a reset on the next game turn.
	 */
	protected boolean resetFlag;

	/**
	 * Constructor.
	 *
	 * @param name        the name of the Actor
	 * @param displayChar the character that will represent the Actor in the display
	 * @param hitPoints   the Actor's starting hit points
	 */
	public Friendly(String name, char displayChar, int hitPoints) {
		super(name, displayChar, hitPoints);
		this.addCapability(Status.SHIELDED);    // All friendlies are shielded and cannot be attacked
		this.resetFlag = false;
		this.behaviours = new ArrayList<>();
		this.speakBehaviour = new SpeakBehaviour(this);
		this.spokeLastTurn = false;
	}

	@Override
	public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
		// Friendlies speak every second turn
		if (!this.spokeLastTurn) {
			this.spokeLastTurn = true;
			return this.speakBehaviour.getAction(this, map);
		}
		this.spokeLastTurn = false;
		for (Behaviour behaviour : this.behaviours) {
			Action action = behaviour.getAction(this, map);
			if (null != action)
				return action;
		}
		return null;
	}
}
