package game.actors.enemies;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.actions.attacks.AttackAction;
import game.behaviours.FollowBehaviour;
import game.items.Fire;
import game.items.Key;
import game.monologues.Monologue;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the Bowser enemy.
 */
public class Bowser extends Enemy {

	/**
	 * The initial location of Bowser.
	 */
	private Location startLocation;

	/**
	 * Constructs the Bowser enemy.
	 *
	 * @param map The GameMap Bowser is located on.
	 */
	public Bowser(GameMap map) {
		super("Bowser", 'B', 500, map);
		this.addItemToInventory(new Key());
	}

	private boolean setsFireOn(Action action) {
		return action instanceof AttackAction;  // idk how to get rid of this usage
	}

	@Override
	public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
		if (null == this.startLocation)
			this.startLocation = map.locationOf(this);

		if (this.resetFlag) {
			// Bowser will return to his original position and regain max hp
			// NOTE: If another actor is at his orginal position they will be moved
			// NOTE: If Bowser is still at his original position he will not be moved
			if (!map.isAnActorAt(this.startLocation)) {
				map.moveActor(this, this.startLocation);
			} else if (this != map.getActorAt(this.startLocation)) {
				Location shiftedLocation;
				for (Exit exit : this.startLocation.getExits()) {
					shiftedLocation = exit.getDestination();
					if (!shiftedLocation.containsAnActor()) {
						map.moveActor(map.getActorAt(this.startLocation), shiftedLocation);
						break;
					}
				}
			}
			this.heal(this.getMaxHp());
			this.resetFlag = false;
			return new DoNothingAction();   // Return a blank action instead of null to follow LSP
		}

		Actor target = this.findTarget(this, map);  // Get the target of an attack
		this.getBehaviours().add(new FollowBehaviour(target));
		Action action = super.playTurn(actions, lastAction, map, display);  // Get the standard enemy action
		if (this.setsFireOn(action)) {
			// Set the ground on fire when attacking
			// Do this here instead of in AttackAction as it is specific to attacks by Bowser
			map.locationOf(target).addItem(new Fire()); //Fire item
		}

		return action;  // Execute the standard enemy action
	}

	@Override
	protected IntrinsicWeapon getIntrinsicWeapon() {
		return new IntrinsicWeapon(80, "punch");
	}

	@Override
	public List<Monologue> getMonologues(Actor actor) {
		List<Monologue> monologues = new ArrayList<>();
		monologues.add(new Monologue("What was that sound? Oh, just a fire."));
		monologues.add(new Monologue("Princess Peach! You are formally invited... to the creation of my new kingdom!"));
		monologues.add(new Monologue("Never gonna let you down!"));
		monologues.add(new Monologue("Wrrrrrrrrrrrrrrrryyyyyyyyyyyyyy!!!!"));
		return monologues;
	}
}
