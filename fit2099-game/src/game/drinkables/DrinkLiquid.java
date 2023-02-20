package game.drinkables;
import edu.monash.fit2099.engine.actors.Actor;
import game.items.Consumable;

/**
 * An abstract class representing a liquid to drink
 */
public abstract class DrinkLiquid implements Consumable {
    /**
     * The name of the liquid
     */
    private String name;

    /**
     * Constructor
     * @param name the name of the liquid
     */
    public DrinkLiquid(String name) {
        this.name = name;
    }

    @Override
    public void resetEffect(Actor actor) {}

    @Override
    public String toString() {
        return this.name + " water";
    }

}
