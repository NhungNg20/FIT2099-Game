package game.items;

import edu.monash.fit2099.engine.items.Item;
import game.Status;

/**
 * Represents a key the player can use to un-handcuff Princess Peach.
 */
public class Key extends Item {

	/***
	 * Constructor. Creates a key item.
	 */
	public Key() {
		super("Key", 'k', true);
		this.addCapability(Status.UNLOCK_HANDCUFFS);    // The Key allows actors to unlock handcuffs
	}
}
