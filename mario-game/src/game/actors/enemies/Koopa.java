package game.actors.enemies;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.Status;
import game.actions.attacks.FireAttackAction;
import game.actions.attacks.GenAttackAction;
import game.behaviours.FollowBehaviour;
import game.behaviours.WanderBehaviour;
import game.items.magical.SuperMushroom;
import game.monologues.Monologue;
import java.util.ArrayList;
import java.util.List;

/**
 * The Koopa class represents a Koopa actor
 */
public class Koopa extends Enemy {

	/**
	 * Constructor for a Koopa. Giving it an ability to go dormant
	 *
	 * @param map The current map of this Koopa
	 */
	public Koopa(GameMap map) {
		super("Koopa", 'K', 100, map);
		this.getBehaviours().add(new WanderBehaviour());
		this.addItemToInventory(new SuperMushroom());
	}

	/**
	 * Constructor for a Koopa to be used by subclass FlyingKoopa.
	 * @param name The Koopa variant's name.
	 * @param displayChar The Koopa variant's display character.
	 * @param hitPoints The Koopa variant's hit points.
	 * @param map The current map of this Koopa.
	 */
	public Koopa(String name, char displayChar, int hitPoints, GameMap map) {
		super(name, displayChar, hitPoints, map);
		this.getBehaviours().add(new WanderBehaviour());
		this.addItemToInventory(new SuperMushroom());
	}

	/**
	 * To construct Koopa's own unique intrinsic weapon
	 *
	 * @return Koopa's intrinsic weapon
	 */
	@Override
	protected IntrinsicWeapon getIntrinsicWeapon() {
		return new IntrinsicWeapon(30, "punch");
	}

	/**
	 * To retrieve allowed actions to be performed on Koopa
	 *
	 * @param otherActor The actor that might be performing attack
	 * @param direction  String representing the direction of the other Actor
	 * @param map        The current GameMap
	 * @return The list of allowable actions on Koopa as ActionList
	 */
	@Override
	public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
		ActionList actions = new ActionList();
		if (!this.hasCapability(Status.DORMANT) | otherActor.hasCapability(Status.DESTROY_SHELL)) {
			if (otherActor.hasCapability(Status.FIRE_ATTACK) & !otherActor.hasCapability(Status.DESTROY_SHELL)) {
				return super.allowableActions(otherActor,direction,map);
			}
			actions.add(new GenAttackAction(this, direction));
		}
		return actions;
	}

	/**
	 * To automatically select an action to perform in current turn.
	 * if Koopa is in dormant state, i.e unconscious, return a nothing action
	 *
	 * @param actions    collection of possible Actions for this Actor
	 * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
	 * @param map        the map containing the Actor
	 * @param display    the I/O object to which messages may be written
	 * @return the Action to be performed
	 */
	@Override
	public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
		Actor target = this.findTarget(this, map);  // Get the target of an attack
		this.getBehaviours().add(1, new FollowBehaviour(target));
		Action action = super.playTurn(actions, lastAction, map, display);  // reset happens in super
		if (!hasCapability(Status.DORMANT)) {
			return action;
		}
		return new DoNothingAction();
	}

	/**
	 * Remove hp from koopa. If hp is reduced to 0, enter dormant state and heal by 1pt
	 *
	 * @param points number of hitpoints to deduct.
	 */
	@Override
	public void hurt(int points) {
		super.hurt(points);
		if (!isConscious()) {
			this.addCapability(Status.DORMANT);
			super.setDisplayChar('D');
			heal(1);            // heal by 1 -. remains conscious and will die after 1 hit
		}
	}

	@Override
	public List<Monologue> getMonologues(Actor actor) {
		List<Monologue> monologues = new ArrayList<>();
		monologues.add(new Monologue("Never gonna make you cry!"));
		monologues.add(new Monologue("Koopi koopi koopii~!"));
		return monologues;
	}
}
