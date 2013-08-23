package autocomplete.core.event.irc;

import autocomplete.core.event.Event;

/**
 * A type of {@link IRCSessionEvent}, indicates a session has ended.
 * 
 * @see IRCSessionEvent
 * @see IRCEvent
 * @see Event
 */
public class IRCSessionCloseEvent extends IRCSessionEvent {
	

	/* ---- Constructors ---- */

	/**
	 * Constructor.
	 * 
	 * @param sessionMessage The message of the session.
	 */
	public IRCSessionCloseEvent(String sessionMessage) {
		super(sessionMessage);
	}

}
