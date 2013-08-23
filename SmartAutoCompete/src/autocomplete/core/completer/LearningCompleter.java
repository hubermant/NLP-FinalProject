package autocomplete.core.completer;

import autocomplete.core.event.EndSentenceEvent;
import autocomplete.core.event.Event;

/**
 * This completer extends the {@link BasicCompleter}, but unlike the {@link BasicCompleter} 
 * this completer evolve and learn over time.
 * 
 * This completer handles the events:<br>
 * {@link EndSentenceEvent}
 * 
 * @see Completer
 * @see BasicCompleter
 */
public class LearningCompleter extends BasicCompleter {
	
	
	/* ---- Constructors ---- */

	/**
	 * Constructor.
	 * 
	 * @param ngram The number of last words.
	 */
	public LearningCompleter(int ngram) {
		super(ngram);
	}
	
	/* ---- Implemented Methods ---- */
	
	/**
	 * This method call the correct handler for the events pass to the completer.
	 * {@link EndSentenceEvent} --> {@link LearningCompleter#handleEndSentenceEvent(EndSentenceEvent)}
	 * 
	 * @param e
	 * @see autocomplete.core.completer.BasicCompleter#handleEvent(autocomplete.core.event.Event)
	 */
	@Override
	public void handleEvent(Event e) {
		super.handleEvent(e);
		if (e instanceof EndSentenceEvent) {
			handleEndSentenceEvent((EndSentenceEvent) e);
		}
	}

	
	/* ---- Private Methods ---- */
	
	/**
	 * Handle the {@link EndSentenceEvent}.
	 * Train the completer on the last sentence.
	 * 
	 * @param e The Event indicates the sentence has ended.
	 */
	private void handleEndSentenceEvent(EndSentenceEvent e) {
		this.train(e.getSentence());
	}
	
}
