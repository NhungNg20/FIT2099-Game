package game.items;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.Status;
import game.actions.ConsumeAction;
import game.items.Consumable;

/**
 * A class to represent a Fire Flower
 */
public class FireFlower extends Item implements Consumable {
    /**
     * The action of consume this item
     */
    private final ConsumeAction consumeAction;
    /**
     * The maximum age of a fire flower
     */
    private static final int MAX_AGE = 20;
    /**
     * The age counter for a fire flower
     */
    private int age;

    /**
     * Constructor.
     */
    public FireFlower() {
        super("Fire Flower",'f',false);
        consumeAction = new ConsumeAction(this);
        addAction(consumeAction);
        setAge(0);
    }

    /**
     * Setting the age of the fire flower
     * @param age age counter of fire flower as int
     */
    private void setAge(int age) {
        this.age = age;
    }

    /**
     * To decrease the age counter of fire flower in every turn.
     * Once it reaches dies (i.e age = 0), it will be removed
     * @param currentLocation The location of the actor carrying this Item.
     * @param actor The actor carrying this Item.
     */
    @Override
    public void tick(Location currentLocation, Actor actor) {
        age--;
        if (age <= 0) {
            resetEffect(actor);
        }
    }

    /**
     * To consume the fire flower
     * Actor can only consume this once and is able to fire attack
     * @param actor Actor which is consuming the item
     */
    @Override
    public void consume(Actor actor) {
        removeAction(this.consumeAction);
        actor.addCapability(Status.FIRE_ATTACK);
        setAge(MAX_AGE);
    }

    @Override
    public void resetEffect(Actor actor) {
        actor.removeItemFromInventory(this);
    }
}
