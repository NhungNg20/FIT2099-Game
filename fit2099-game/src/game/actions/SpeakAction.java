package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.Utils;
import game.monologues.Monologue;

import java.util.List;


/**
 * A class for all speak actions.
 */
public final class SpeakAction extends Action {

    /**
     * The actor speaking. This exists so an actor can choose to be spoken to by another actor.
     */
    private final Actor actorSpeaking;

    /**
     * The actor being spoken to. null if no actor spoken to directly.
     */
    private final Actor actorSpokenTo;

    /**
     * The list of monologues to choose from.
     */
    private final List<Monologue> monologues;


    /**
     * Constructor.
     * @param speakingActor The actor speaking.
     * @param otherActor The actor being spoken to (if any, may be null).
     * @param monologues A list of monologues that can be spoken at random.
     */
    public SpeakAction(Actor speakingActor, Actor otherActor, List<Monologue> monologues) {
        this.actorSpeaking = speakingActor;
        this.actorSpokenTo = otherActor;
        this.monologues = monologues;
    }

    /**
     * Prints a sentence by returning it as the result to be printed.
     * @see edu.monash.fit2099.engine.positions.World
     * @param actor The actor speaking.
     * @param map The map the actor is on.
     * @return The sentence spoken.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        // Randomly return a sentence to be spoken to the console.
        String sentence = this.monologues.get(Utils.randInt(this.monologues.size())).getSentence();
        return "[" + this.actorSpeaking + "]: \"" + sentence + "\"";
    }

    @Override
    public String menuDescription(Actor actor) {
        return "Speak with " + this.actorSpokenTo;
    }
}