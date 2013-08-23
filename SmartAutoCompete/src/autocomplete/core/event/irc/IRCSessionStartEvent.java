package autocomplete.core.event.irc;

import autocomplete.core.event.Event;

/**
 * A type of {@link IRCSessionEvent} indicates that a session had started.
 * 
 * @see IRCSessionEvent
 * @see Event
 */
public class IRCSessionStartEvent extends IRCSessionEvent {
	
	
	/* ---- Constructors ---- */

	/**
	 * Constructor.
	 * 
	 * @param sessionMessage The message of the session.
	 */
	public IRCSessionStartEvent(String sessionMessage) {
		super(sessionMessage);
	}

}
