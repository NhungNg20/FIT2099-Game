package game.items;

import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Location;

public class Fire extends Item {

    /**
     * The maximum lifetime of a fire.
     */
    private static final int MAX_LIFETIME = 3;

    /**
     * The damage dealt by the fire to any actor.
     */
    private static final int FIRE_DAMAGE = 20;

    /**
     * The number of turns the fire has been alive for.
     */
    private int lifetime;

    /**
     * Constructs a fire item.
     */
    public Fire() {
        super("Fire", 'v', false);
        this.lifetime = 0;
    }

    @Override
    public void tick(Location currentLocation) {
        // The fire extinguishes itself after its lifetime
        if (Fire.MAX_LIFETIME == this.lifetime) {
            currentLocation.removeItem(this);
        }

        // The fire hurts whoever stands on it
        if (currentLocation.containsAnActor()) {
            currentLocation.getActor().hurt(Fire.FIRE_DAMAGE);
        }

        this.lifetime++;
    }
}
