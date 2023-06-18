package game.actors.enemies;


import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.Utils;
import game.behaviours.FollowBehaviour;
import game.behaviours.WanderBehaviour;
import game.monologues.Monologue;
import java.util.ArrayList;
import java.util.List;

/**
 * A little fungus guy.
 */
public class Goomba extends Enemy {

	/**
	 * Constructor for a Goomba
	 *
	 * @param map The current map of actor
	 */
	public Goomba(GameMap map) {
		super("Goomba", 'g', 50, map);
		this.getBehaviours().add(new WanderBehaviour());
	}

	/**
	 * To construct Goomba's own unique intrinsic weapon
	 *
	 * @return Goomba's intrinsic weapon
	 */
	@Override
	public IntrinsicWeapon getIntrinsicWeapon() {
		return new IntrinsicWeapon(10, "kicks");
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
		Actor target = this.findTarget(this, map);  // Get the target of an attack
		this.getBehaviours().add(1, new FollowBehaviour(target));
		Action action = super.playTurn(actions, lastAction, map, display);

		if (Utils.randInt(100) > 10) {
			return action;
		} else {
			map.removeActor(this);
		}
		return new DoNothingAction();
	}

	@Override
	public List<Monologue> getMonologues(Actor actor) {
		List<Monologue> monologues = new ArrayList<>();
		monologues.add(new Monologue("Mugga mugga!"));
		monologues.add(new Monologue("Ugha ugha... (Never gonna run around and desert you...)"));
		monologues.add(new Monologue("Ooga-Chaka Ooga-Ooga!"));
		return monologues;
	}
}
