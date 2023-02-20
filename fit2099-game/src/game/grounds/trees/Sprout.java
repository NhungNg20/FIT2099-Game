package game.grounds.trees;

import edu.monash.fit2099.engine.actions.*;
import edu.monash.fit2099.engine.positions.Location;
import game.Utils;
import game.actions.JumpAction;
import game.actors.enemies.Goomba;
import game.grounds.*;
import game.resets.*;

import java.util.*;

/**
 * The Sprout class represents a tree in the first stage of its lifecycle.
 */
public class Sprout extends Tree {

    /**
     * Default constructor. Used by FancyGroundFactory in Application.
     */
    public Sprout() {
        super("Sprout", '+');
    }


    /**
     * Override from Tree superclass.
     * Tree can grow and drop items with each time step.
     * @param location The sprout's location
     */
    @Override
    public void tick(Location location) {
        super.tick(location);   // tick operations for all trees. check if crushed by superstar user

        age++;
        if (age == 10) {
            grow(location);
        } else if (Utils.randInt(100) <= 10){
            if (!location.containsAnActor()) {
                location.addActor(new Goomba(location.map()));
            }
        }
    }

    @Override
    public void grow(Location location) {
        location.setGround(new Sapling());
        super.grow(location);
    }

    /**
     * Override this method from HighGround interface to give unique JumpAction based on ground type
     *
     * @param direction - direction to jump in
     * @param myLocation  - tree location
     * @return JumpAction
     */
    @Override
    public Action getJumpAction(String direction, Location myLocation,String hotkey) {
        return new JumpAction(10, 90, direction, myLocation,hotkey);
    }
}
