package autocomplete.core.event.irc;

import autocomplete.core.event.Event;

/**
 * A Type of {@link IRCSessionEvent}, indicates the name of the new session.
 * 
 * @see IRCSessionEvent
 * @see Event
 */
public class IRCSessionIdentEvent extends IRCSessionEvent {

	
	/* ---- Constructors ---- */
	
	/**
	 * Constructor.
	 * 
	 * @param sessionMessage The message of the session (the name of the session).
	 */
	public IRCSessionIdentEvent(String sessionMessage) {
		super(sessionMessage);
	}

}
