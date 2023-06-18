package game.actors.enemies;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import game.Status;
import game.actions.attacks.FireAttackAction;
import game.actions.attacks.GenAttackAction;
import game.behaviours.AttackBehaviour;
import game.behaviours.Behaviour;
import game.behaviours.FollowBehaviour;
import game.behaviours.SpeakBehaviour;
import game.behaviours.WanderBehaviour;
import game.monologues.Speakable;
import game.resets.ResetManager;
import game.resets.Resettable;
import java.util.ArrayList;
import java.util.List;

/**
 * The abstract Enemy class represents an NPC hostile to the player.
 */
public abstract class Enemy extends Actor implements Resettable, Speakable {

	/**
	 * A collection of the enemy behaviours. Higher priority behaviours occur first in the list.
	 */
	private final List<Behaviour> behaviours = new ArrayList<>();

	/**
	 * The SpeakBehaviour is separate from the other behaviours since it must be chosen every second turn (according
	 * to the requirements).
	 */
	private final SpeakBehaviour speakBehaviour;

	/**
	 * Signals whether to perform a reset on the next game turn.
	 */
	protected boolean resetFlag;

	/**
	 * Signals whether the speakBehaviour should be chosen for this turn.
	 */
	private boolean spokeLastTurn;

	/**
	 * Constructs a new instance of an enemy. Registers the enemy as resettable with ResetManager.
	 *
	 * @param name        The enemy's name.
	 * @param displayChar The enemy's GameMap display character.
	 * @param hitPoints   The enemy's maximum health points.
	 */
	public Enemy(String name, char displayChar, int hitPoints, GameMap map) {
		super(name, displayChar, hitPoints);
		this.registerInstance();        // Register enemy for global reset
		this.resetFlag = false;
		this.behaviours.add(new AttackBehaviour());
		this.speakBehaviour = new SpeakBehaviour(this);
		this.spokeLastTurn = false;
	}

	/**
	 * Returns the enemy behaviours map.
	 *
	 * @return A Map from the priority of a behaviours to the enemy behaviour.
	 */
	public List<Behaviour> getBehaviours() {
		return behaviours;
	}

	/**
	 * To retrieve allowable actions on this enemy
	 *
	 * @param otherActor The actor that might be performing attack
	 * @param direction  String representing the direction of the other Actor
	 * @param map        The current GameMap
	 * @return The list of allowable actions on this enemy as ActionList
	 */
	@Override
	public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
		ActionList actions = super.allowableActions(otherActor, direction, map);
		if (otherActor.hasCapability(Status.HOSTILE_TO_ENEMY)) {
			if (otherActor.hasCapability(Status.FIRE_ATTACK)) {
				actions.add(new FireAttackAction(this, direction));
				return actions;
			}
			actions.add(new GenAttackAction(this, direction));
		}
		return actions;
	}

	/**
	 * To automatically select an action to perform in current turn.
	 *
	 * @param actions    collection of possible Actions for this Actor
	 * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
	 * @param map        the map containing the Actor
	 * @param display    the I/O object to which messages may be written
	 * @return the Action to be performed
	 */
	@Override
	public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
		// All standard enemies are killed during a reset
		if (this.resetFlag) {
			map.removeActor(this);
			this.resetFlag = false;
			return new DoNothingAction();
		}

		// Enemy must speak every second turn
		if (!this.spokeLastTurn) {
			this.spokeLastTurn = true;
			return this.speakBehaviour.getAction(this, map);
		} else {
			this.spokeLastTurn = false;
		}

		// Standard enemy behaviours
		for (Behaviour behaviour : behaviours) {
			Action action = behaviour.getAction(this, map);
			if (action != null)
				return action;
		}

		return new DoNothingAction();
	}

	/**
	 * To allow an enemy to attack the player in its surrounding
	 * if the player is in its surroundings, it can attack and follow
	 *
	 * @param actor This current enemy
	 * @param map   The map of this current enemy
	 * @return The target Actor.
	 */
	public Actor findTarget(Actor actor, GameMap map) {
		for (Exit exit : map.locationOf(actor).getExits()) {
			Actor target = exit.getDestination().getActor();
			if (target != null && target.hasCapability(Status.HOSTILE_TO_ENEMY)) {
				return target;
			}
		}
		return null;
	}

	@Override
	public void resetInstance() {
		this.resetFlag = true;
	}
}
