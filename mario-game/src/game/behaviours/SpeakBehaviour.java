package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.Utils;
import game.actions.SpeakAction;
import game.monologues.Monologue;
import game.monologues.Speakable;
import java.util.List;

/**
 * This is a behaviour for any actors that speak autonomously (i.e. not due to a player action).
 */
public final class SpeakBehaviour implements Behaviour {

	/**
	 * The actor acting this behaviour. The actor must implement Speakable.
	 */
	private final Speakable speakableActor;

	/**
	 * Constructor for SpeakBehaviour.
	 * Ensures that only actors implementing Speakable can have a SpeakBehaviour.
	 * If a non-Speakable actor is passed a compiler error should be raised due to incorrect parameter type
	 * @param actor The Speakable actor acting this behaviour.
	 */
	public SpeakBehaviour(Speakable actor) {
		this.speakableActor = actor;
	}

	@Override
	public Action getAction(Actor actor, GameMap map) {
		// Get SpeakAction from 'this' instance
		// In this case the actor is not speaking to another actor directly
		// NOTE: This can be modified to speak to an actor in the surroundings, as GameMap is provided.
		List<Monologue> monologues = this.speakableActor.getMonologues(actor);
		return new SpeakAction((Actor) this.speakableActor, null, monologues);
	}
}
