package autocomplete.core.event.irc;

import autocomplete.core.event.Event;
import autocomplete.io.parse.irc.IRCSentence;

public class IRCEvent implements Event {
	
	private IRCSentence sentence;

	public IRCEvent(IRCSentence sentence) {
		this.sentence=sentence;
	}
	
	public IRCSentence getSentence() {
		return sentence;
	}
	
}
