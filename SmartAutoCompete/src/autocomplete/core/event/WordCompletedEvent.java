package autocomplete.core.event;

/**
 * This event indicate the a word completion has just ended.
 * 
 * @see Event
 */
public class WordCompletedEvent implements Event {

	
	/* ---- Data Members ---- */
	
	/** The word that was just completed. */
	private String word;

	
	/* ---- Constructors ---- */
	
	/**
	 * Constructor.
	 * 
	 * @param word The word that was just completed.
	 */
	public WordCompletedEvent(String word) {
		this.word = word;
	}
	
	/* ---- Getters and Setters --- */

	/**
	 * A getter for {@link WordCompletedEvent#word}.
	 * 
	 * @return The word that was just completed. 
	 */
	public String getWord() {
		return word;
	}


}
