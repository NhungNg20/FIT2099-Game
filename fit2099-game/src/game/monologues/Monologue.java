package game.monologues;

/**
 * Represents a single monologue an actor might speak.
 */
public class Monologue {

	/**
	 * The sentence spoken in the monologue. This may be changed to a collection of sentences.
	 */
	private final String sentence;

	/**
	 * Constructs a monologue with the given sentence.
	 * @param sentence The sentence spoken in the monologue.
	 */
	public Monologue(String sentence) {
		this.sentence = sentence;
	}

	/**
	 * Accessor for the monologue text.
	 * @return The monologue text as a string.
	 */
	public String getSentence() {
		return this.sentence;
	}
}
