package autocomplete.core.event.irc;

import autocomplete.core.event.Event;
import autocomplete.io.parse.irc.IRCSentence;

/**
 * A Parent class for all the IRC {@link Event}s.
 */
public class IRCEvent implements Event {
	
	
	/* ---- Data Members ---- */
	
	/** The parsed IRC sentence. */
	private IRCSentence sentence;

	
	/* ---- Constructors ---- */
	
	/**
	 * Constructor.
	 * 
	 * @param sentence The IRC sentence.
	 */
	public IRCEvent(IRCSentence sentence) {
		this.sentence=sentence;
	}
	

	/* ---- Getters --- */
	
	/**
	 * A getter for the {@link IRCEvent#sentence}.
	 * @return The sentence.
	 */
	public IRCSentence getSentence() {
		return sentence;
	}
	
}
