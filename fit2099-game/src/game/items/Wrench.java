package game.items;

import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.Status;

/**
 * The class Wrench represents a Wrench weapon
 */
public class Wrench extends WeaponItem {
    /**
     * Constructor for wrench.
     * Giving it an ability to destroy koopa shell
     */
    public Wrench() {
        super("Wrench", '?', 50, "boshes", 80);
        this.addCapability(Status.DESTROY_SHELL);
    }
}