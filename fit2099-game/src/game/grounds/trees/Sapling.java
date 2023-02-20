package game.grounds.trees;

import edu.monash.fit2099.engine.actions.*;
import edu.monash.fit2099.engine.positions.Location;
import game.Utils;
import game.actions.JumpAction;
import game.grounds.*;
import game.resets.*;
import game.items.Coin;

/**
 * The Sapling class represents a tree in the second stage of its lifecycle.
 */
public class Sapling extends Tree {

    /**
     * Default constructor. Used by FancyGroundFactory in Application.
     */
    public Sapling() {
        super("Sapling", 't');
    }

    /**
     * Override from Tree superclass.
     * Tree can grow and drop items with each time step.
     * @param location - location of sapling
     */
    @Override
    public void tick(Location location) {
        super.tick(location);   // tick operations for all trees. check if crushed by superstar user

        age++;
        if (age == 10) {
            grow(location);
        }
        if (Utils.randInt(100) <= 10) {
            location.addItem(new Coin(20));
        }
    }

    @Override
    public void grow(Location location) {
        location.setGround(new Mature());
        super.grow(location);
    }

    /**
     * Override this method from HighGround interface to give unique JumpAction based on ground type
     *
     * @param direction - direction to jump in
     * @param myLocation  - destination location
     */
    @Override
    public Action getJumpAction(String direction, Location myLocation,String hotkey) {
        return new JumpAction(20, 80, direction,myLocation,hotkey);
    }
}


