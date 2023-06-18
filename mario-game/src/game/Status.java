package game;

/**
 * Use this enum class to give `buff` or `debuff`.
 * It is also useful to give a `state` to abilities or actions that can be attached-detached.
 */
public enum Status {
	HOSTILE_TO_ENEMY, // use this status to be considered hostile towards enemy (e.g., to be attacked by enemy)
	TALL, // use this status to tell that current instance has "grown". Changes Player display char to uppercase
	DORMANT, //actor can go into dormant state
	FERTILE, //ground that can grow new sprouts
	DESTROY_SHELL, //capable of destroying shells
	FIRE_ATTACK, //capable of fire attacks
	HANDCUFFED,         // An actor with this status cannot perform any actions except speaking.
	UNLOCK_HANDCUFFS,   // An actor with this status can remove the HANDCUFFED status of another actor.
	SHIELDED,           // An actor with this status cannot be attacked.
	TRAVEL_OVER_HIGH_GROUND, // An actor with this status can travel over HighGrounds.
	POWER_UP; //power buff for intrinsic attacks

	public enum ItemEffect {   // Capability to indicate if a consumable item is having effect on actor
		SUPERMUSH, //capability of Super Mushroom
		POWERSTAR, //capability of Power Star
		STORE_LIQUID, //capability to store water
	}
}
