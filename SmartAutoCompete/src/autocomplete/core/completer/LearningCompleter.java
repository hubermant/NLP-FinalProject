package autocomplete.core.completer;

import autocomplete.core.event.EndSentenceEvent;
import autocomplete.core.event.Event;

public class LearningCompleter extends BasicCompleter {
	
	
	/* ---- Constructors ---- */

	public LearningCompleter(int ngram) {
		super(ngram);
	}
	
	public void handleEvent(Event e) {
		if (e instanceof EndSentenceEvent) {
			handleEndSentenceEvent((EndSentenceEvent) e);
		}
	}

	private void handleEndSentenceEvent(EndSentenceEvent e) {
		this.train(e.getSentence());
	}
	
}
