package game.monologues;

import edu.monash.fit2099.engine.actors.Actor;
import java.util.List;

/**
 * Represents any classes that are capable of speaking to the console using SpeakAction.
 */
public interface Speakable {

	/**
	 * Returns a collection of monologues the actor can speak.
	 * @param actor The actor being spoken to.
	 * @return A list of monologues that can be spoken by 'this' instance.
	 */
	List<Monologue> getMonologues(Actor actor);
}
