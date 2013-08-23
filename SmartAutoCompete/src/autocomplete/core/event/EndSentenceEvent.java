package autocomplete.core.event;

import java.util.List;

/**
 * This class indicates the end of sentence event.
 * This Event is raised when a completer finish completing a sentence.
 * 
 * @see Event
 */
public class EndSentenceEvent implements Event {
	
	
	/* ---- Data Members ---- */
	
	/** The ended sentence. */
	List<String> sentence;

	
	/* ---- Constructors ---- */
	
	/**
	 * Default C'tor.
	 * 
	 * @param sentece The ended sentence.
	 */
	public EndSentenceEvent(List<String> sentence) {
		this.sentence = sentence;
	}

	
	/* ---- Getters --- */
	
	/**
	 * @return The ended sentence.
	 */
	public List<String> getSentence() {
		return sentence;
	}
	
}
