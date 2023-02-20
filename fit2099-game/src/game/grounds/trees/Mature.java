package game.grounds.trees;

import edu.monash.fit2099.engine.actions.*;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Location;
import game.Utils;
import game.actions.JumpAction;
import game.actors.enemies.FlyingKoopa;
import game.actors.enemies.Koopa;
import game.grounds.*;
import game.Status;
import game.resets.*;

/**
 * The Mature class represents a tree in the third stage of its lifecycle.
 */
public class Mature extends Tree {

    public Mature() {
        super("Mature",'T');
    }


    /**
     * Override from Tree superclass.
     * Tree can grow and drop items with each time step.
     * @param location - location of tree
     */
    @Override
    public void tick(Location location) {
        super.tick(location);   // tick operations for all trees. check if crushed by superstar user

        age++;
        if (age % 5 == 0) {
            grow(location);
        }
        if (Utils.randInt(100) <= 15) {
            if (!location.containsAnActor()) {
                if (0 < Utils.randInt(2)) {
                    location.addActor(new FlyingKoopa(location.map()));
                } else {
                    location.addActor(new Koopa(location.map()));
                }
            }
        }
        if (Utils.randInt(100) <= 20) {
            location.setGround(new Dirt());
        }
    }

    @Override
    public void grow(Location location) {
        int newSprout = 0;
        for (int i = 0; i <= location.getExits().size(); i++) {
            int a = Utils.randInt(location.getExits().size());
            Exit exit = location.getExits().get(a);
            Location destination = exit.getDestination();
            if (destination.getGround().hasCapability(Status.FERTILE) && newSprout <= 1) {
                destination.setGround(new Sprout());
                newSprout += 1;
            }
        }
    }

    /**
     * Override this method from HighGround interface to give unique JumpAction based on ground type
     *
     * @param direction - direction to jump in
     * @param myLocation  - destination location
     * @return new jump action
     */
    @Override
    public Action getJumpAction(String direction, Location myLocation,String hotkey) {
        return new JumpAction(30, 70, direction,myLocation,hotkey);
    }
}
