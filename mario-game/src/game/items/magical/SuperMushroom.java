package game.items.magical;

import edu.monash.fit2099.engine.actors.*;
import edu.monash.fit2099.engine.items.*;
import game.*;
import game.actions.*;
import game.items.Consumable;

public class SuperMushroom extends Item implements Consumable {
    private final int HP_BOOST = 50;
    private final ConsumeAction consumeAction;

    public SuperMushroom() {
        super("Super Mushroom", '^', false);
        consumeAction = new ConsumeAction(this);// save to be removed after consumption
        addAction(consumeAction);
    }

    @Override
    public void consume(Actor actor) {
        removeAction(this.consumeAction);
        this.addCapability(Status.TALL);
        this.addCapability(Status.ItemEffect.SUPERMUSH);
        actor.increaseMaxHp(HP_BOOST);
//        togglePortability();                    // Only use with protable:true i constructor!
    }

    /**
     * Remove itself from actor's inventory. SuperMushroom effects now not included in actor capabilities
     *
     * @param actor remove mushroom effects from actor
     */
    @Override
    public void resetEffect(Actor actor) {
//        actor.increaseMaxHp(-HP_BOOST); // maxHp remains higher
        actor.removeItemFromInventory(this);
    }

}
