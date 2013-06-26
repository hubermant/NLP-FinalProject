package autocomplete.core;

import autocomplete.core.event.EndSentenceEvent;
import autocomplete.core.event.Event;

public class LearningCompleter extends BasicCompleter {
	
	
	/* ---- Constructors ---- */

	public LearningCompleter(int ngram, int resNum) {
		super(ngram, resNum);
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
