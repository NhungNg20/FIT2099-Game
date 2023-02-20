package game.grounds.highgrounds;

import edu.monash.fit2099.engine.actions.*;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.JumpAction;
import game.grounds.highgrounds.HighGround;

public class Wall extends HighGround {

	public Wall() {
		super("Wall",'#');
	}

	/**
	 * Override this to give unique JumpAction based on ground type
	 *
	 * @param direction
	 * @param location
	 * @return
	 */
	@Override
	public Action getJumpAction(String direction, Location location,String hotkey) {
		return new JumpAction(20,80, direction, location, hotkey);
	}

	@Override
	public boolean blocksThrownObjects() {
		return true;
	}

	@Override
	public String toString() {
		return "Wall";
	}

}
