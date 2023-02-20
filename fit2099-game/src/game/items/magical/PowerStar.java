package game.items.magical;

import edu.monash.fit2099.engine.actors.*;
import edu.monash.fit2099.engine.items.*;
import edu.monash.fit2099.engine.positions.*;
import game.*;
import game.actions.*;
import game.items.Consumable;

public class PowerStar extends Item implements Consumable {
    private static final String name = "Power Star";
    public static final int MAX_AGE = 10;
    private int ageCount;
    private final ConsumeAction consumeAction;

    public PowerStar() {
        super("Power Star", '*', false);
        consumeAction = new ConsumeAction(this);// save to be removed after consumption
        addAction(consumeAction);
        ageCount = MAX_AGE;
    }


    @Override
    public void tick(Location loc) { // star on ground
        ageCount--;
        if (ageCount > 0) {
            return;
        }
        loc.removeItem(this);
        System.out.printf("Startick: %d\n", ageCount);
    }

    /**
     *
     * @param currentLocation The location of the actor carrying this Item.
     * @param actor The actor carrying this Item.
     */
    @Override
    public void tick(Location currentLocation, Actor actor) { // star in inventory
        System.out.printf("Startick: %d\n", ageCount);
        ageCount--;
        if (ageCount <= 0) {
            resetEffect(actor);
        }

    }

    /**
     *
     * @param actor Actor which is consuming the item
     */
    @Override
    public void consume(Actor actor) {
        removeAction(this.consumeAction);
        this.addCapability(Status.ItemEffect.POWERSTAR);
        resetAgeCount();
    }
    // object removed from inventory to be garbage collectd
    public void resetEffect(Actor actor) {
        actor.removeItemFromInventory(this);
    }

    private void resetAgeCount() {
        ageCount = MAX_AGE;
    }

    @Override
    public String toString() {
        return String.format(name + " - %d/%d turns remaining", ageCount, MAX_AGE);
    }

}
