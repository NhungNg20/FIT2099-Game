package game.actors.enemies;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.behaviours.Behaviour;
import game.monologues.Monologue;
import java.util.ArrayList;
import java.util.List;

/**
 * The PiranhaPlant enemy cannot move and attacks the player when adjacent.
 */
public class PiranhaPlant extends Enemy {

	/**
	 * Constructs a new instance of a Piranha Plant.
	 *
	 * @param map The GameMap the Piranha Plant is located on.
	 */
	public PiranhaPlant(GameMap map) {
		super("Piranha Plant", 'Y', 150, map);
	}

	@Override
	public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
		if (this.resetFlag) {
			this.increaseMaxHp(50);
			this.resetFlag = false;
			return new DoNothingAction();
		}

		for (Behaviour behaviour : this.getBehaviours()) {
			Action action = behaviour.getAction(this, map);
			if (null != action)
				return action;
		}

		return new DoNothingAction();
	}

	@Override
	protected IntrinsicWeapon getIntrinsicWeapon() {
		return new IntrinsicWeapon(90, "chomps");
	}

	@Override
	public List<Monologue> getMonologues(Actor actor) {
		List<Monologue> monologues = new ArrayList<>();
		monologues.add(new Monologue("Slsstssthshs~! (Never gonna say goodbye~)"));
		monologues.add(new Monologue("Ohmnom nom nom nom."));
		return monologues;
	}

}
