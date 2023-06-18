package game.grounds.highgrounds;

import edu.monash.fit2099.engine.actions.*;
import edu.monash.fit2099.engine.actors.*;
import edu.monash.fit2099.engine.positions.*;
import game.Status;
import game.grounds.Dirt;
import game.items.*;
import game.resets.*;

public abstract class HighGround extends Ground{
    private final String name;

    public HighGround(String name, char displayChar) {
        super(displayChar);
        this.name = name;
    }

    public abstract Action getJumpAction(String direction, Location myLocation,String hotkey);

    @Override
    public ActionList allowableActions(Actor actor, Location myLocation, String direction) {
        ActionList actions =  super.allowableActions(actor, myLocation, direction);
        if (canActorJumpTo(actor) & !myLocation.containsAnActor()) {
            String hotkey = getHotkey( actor,myLocation);
            actions.add(getJumpAction(direction, myLocation, hotkey));
        }
        return actions;
    }

    /**
     * Returns the hotkey of the MoveActorAction which would take Actor to this location.
     * @param actor
     * @param myLocation
     */
    private String getHotkey(Actor actor, Location myLocation){
        Location destination;

        for (Exit myExit : myLocation.getExits()) {
            destination = myExit.getDestination();
            if (destination.getActor() != actor) continue;
            for (Exit yourExit : destination.getExits()) {
                if (yourExit.getDestination() == myLocation) {
                    return yourExit.getHotKey();
                }
            }
        }
        return null;
    }
    /**
     * If this location contains an actor with POWERSTAR capability then HighGround is pulverised.
     * Modify location to contain Dirt type ground and adds coin to this location.
     *
     * @param myLocation the location of this high ground
     */
    private void checkStarPlayerOccupancy(Location myLocation){

        if (myLocation.containsAnActor()){
            Actor presentActor = myLocation.getActor();
            if (presentActor.hasCapability(Status.ItemEffect.POWERSTAR)){
                myLocation.setGround(new Dirt());
                myLocation.addItem(new Coin(5));
            }
        }
    }

    /**
     * Actor canjump to high ground if actor has POWERSTAR capability.
     * @param actor the Actor to check
     * @return true if the Actor has POWERSTAR capability
     */
    public boolean canActorJumpTo(Actor actor){
        return actor.hasCapability(Status.HOSTILE_TO_ENEMY) & !actor.hasCapability(Status.ItemEffect.POWERSTAR);
    }
    @Override
    public boolean canActorEnter(Actor actor){
        return actor.hasCapability(Status.TRAVEL_OVER_HIGH_GROUND);
    }
    @Override
    public void tick(Location location){
        super.tick(location);
        checkStarPlayerOccupancy(location);
    };
    @Override
    public String toString() {
        return name;
    }
}
