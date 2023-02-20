package game.items;

import edu.monash.fit2099.engine.actors.*;

public interface Consumable {

    /**
     * Consume item by removing ConsumeAction from items allowable actions. Toggle portability such that item can't be
     * dropped. Override in subclass to define custom effects. subclass must call this superclass method.
     *
     * @param actor Actor which is consuming the item
     */
    void consume(Actor actor);

    /**
     * Remove effects from actor.
     *
     * @param actor remove effect from actor
     */
    void resetEffect(Actor actor);
}
