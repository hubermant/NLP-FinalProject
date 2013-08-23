package autocomplete.core.event.irc;

import autocomplete.core.event.Event;

/**
 * A parent Class for all the IRC session messages.
 *  
 *  @see Event
 */
public class IRCSessionEvent implements Event {
	
	
	/* ---- Data Members ---- */
	
	/** The session message. */
	private String sessionMessage;
	
	
	/* ---- Constructors ---- */
	
	/**
	 * Constructor.
	 * 
	 * @param sessionMessage The message of the session.
	 */
	public IRCSessionEvent(String sessionMessage) {
		this.sessionMessage=sessionMessage;
	}
	
	
	/* ---- Getters --- */
	
	/**
	 * A getter for the session message.
	 * @return {@link IRCSessionEvent#sessionMessage}
	 */
	public String getSessionMessage() {
		return sessionMessage;
	}
}
