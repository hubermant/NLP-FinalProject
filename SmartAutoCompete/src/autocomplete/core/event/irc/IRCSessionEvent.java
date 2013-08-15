package autocomplete.core.event.irc;

import autocomplete.core.event.Event;

public class IRCSessionEvent implements Event {
	private String sessionMessage;
	public IRCSessionEvent(String sessionMessage) {
		this.sessionMessage=sessionMessage;
	}
	public String getSessionMessage() {
		return sessionMessage;
	}
}
