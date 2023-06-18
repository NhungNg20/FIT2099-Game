package game.grounds.highgrounds;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.JumpAction;
import game.actions.WarpAction;
import game.actors.enemies.PiranhaPlant;
import game.resets.Resettable;

public class WarpPipe extends HighGround implements Resettable {

	private WarpPipe pipePair;
	private Location myLocation;
	private boolean genPiranha = true; // gen piranha on 2nd turn of *current* map. Only generated on tick()

	/**
	 * Signals whether to perform a reset on the next game turn.
	 */
	private boolean resetFlag;

	/**
	 *
	 **/
	public WarpPipe() {
		super("Warp Pipe", 'C');
		this.registerInstance();
		this.resetFlag = false;
	}

	public WarpPipe(Location myLocation) {
		super("Warp Pipe", 'C');
		this.myLocation = myLocation;
		this.registerInstance();
		this.resetFlag = false;
	}

	public Location getLocation() {
		return myLocation;
	}

	public void setPair(WarpPipe pair) {
		this.pipePair = pair;
	}

	/**
	 * Overrides HighGround allowable actions. Gets HighGround allowable actions and adds WarpAction if pipe is paired
	 * and actor is standing on this location.
	 *
	 * @param actor The actor to find allowable actions for
	 * @param myLocation  location of warp pipe
	 * @param direction  direction of WarpPipe wrt. actor
	 * @return Actions the actor may take
	 */
	@Override
	public ActionList allowableActions(Actor actor, Location myLocation, String direction) {
		ActionList actions = super.allowableActions(actor, myLocation, direction);
		if (pipePair != null) {    // is a paired pipe
			Actor presentActor = myLocation.getActor();
			if (presentActor == actor) {               // actor standing on pipe
				actions.add(new WarpAction(pipePair.getLocation(), "Down the pipe!","w"));
			}
		}
		return actions;
	}

	/**
	 * Override this to give unique JumpAction based on ground type
	 *
	 * @param direction jump direction
	 * @param location  location of warp pipe
	 * @return new jump action to warp pipe location
	 */
	@Override
	public Action getJumpAction(String direction, Location location, String hotkey) {
		return new JumpAction(0, 100, direction, location, hotkey);
	}
	/**
	 * Tick implements logic of PiranhaPlant generation
	 *
	 * @param location  location of warp pipe
	 */
	@Override
	public void tick(Location location) {

		// Resetting the WarpPipe restores its ability to generate PiranhaPlants
		if (this.resetFlag) {
			this.genPiranha = true;
			this.resetFlag = false;
		}
		// WarpPipes will generate PiranhaPlants on the second game turn or after a reset
		if (genPiranha ){
			if (!location.containsAnActor()) {						// if location free, gen piranha
				location.addActor(new PiranhaPlant(location.map()));
			}
			genPiranha = false;										// set flag false to stop future generation
		}
	}

	@Override
	public boolean blocksThrownObjects() {
		return true;
	}

	@Override
	public String toString() {
		return "Warp Pipe";
	}

	@Override
	public void resetInstance() {
		this.resetFlag = true;
	}
}
