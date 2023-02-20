package game.actors.enemies;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.Status;
import game.monologues.Monologue;

import java.util.List;

/**
 * A Flying Koopa is a Koopa that can always travel over HighGrounds (e.g. trees, walls).
 */
public class FlyingKoopa extends Koopa {

	/**
	 * Constructor for a Flying Koopa. Same as Koopa but can always travel over HighGrounds.
	 *
	 * @param map The current map of this Flying Koopa
	 */
	public FlyingKoopa(GameMap map) {
		super("Flying Koopa", 'F', 150, map);
		this.addCapability(Status.TRAVEL_OVER_HIGH_GROUND);
	}

	@Override
	public List<Monologue> getMonologues(Actor actor) {
		List<Monologue> monologues = super.getMonologues(this);
		monologues.add(new Monologue("Pam pam pam!"));
		return monologues;
	}
}
