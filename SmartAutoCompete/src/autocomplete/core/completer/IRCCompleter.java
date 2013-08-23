package autocomplete.core.completer;

import java.util.HashMap;
import java.util.Map;

import autocomplete.core.event.Event;
import autocomplete.core.event.WordCompletedEvent;
import autocomplete.core.event.irc.IRCSessionCloseEvent;
import autocomplete.core.event.irc.IRCSessionStartEvent;
import autocomplete.io.parse.irc.IRCSentence;

/**
 * This completer is extending the {@link LearningCompleter} to complete IRC conversations.
 * 
 * This completer handles the events:<br>
 * {@link IRCSessionStartEvent}
 * {@link IRCSessionCloseEvent}
 * {@link WordCompletedEvent}
 * 
 * @see LearningCompleter
 * @see Completer
 * @see IRCSentence
 */
public class IRCCompleter extends LearningCompleter {
	
	
	/* ---- Data Members ---- */
	
	/** A {@link Map} of the words that appeared in the current session and the number of times they appeared. */
	private Map<String,Integer> wordsInSession;
	
	/** The multiply factor of the words in the session. */
	private int sessionFactor;
	
	
	/* ---- Constructors ---- */

	/**
	 * Constructor.
	 * 
	 * @param ngram The number of words in the back that the 
	 * 				completer consider in the completion process.
	 * @param sessionFactor The multiply factor of the words in the session.
	 */
	public IRCCompleter(int ngram, int sessionFactor) {
		super(ngram);
		this.sessionFactor=sessionFactor;
	}


	/* ---- Implemented Methods ---- */
	
	/**
	 * This method get the events that pass to the completer,
	 * and call the correct handler:
	 * {@link IRCSessionStartEvent} --> {@link IRCCompleter#handleIRCSessionStartEvent(IRCSessionStartEvent)}
	 * {@link IRCSessionCloseEvent} --> {@link IRCCompleter#handleIRCSessionClosedEvent(IRCSessionCloseEvent)}
	 * {@link WordCompletedEvent}   --> {@link IRCCompleter#handleWordCompletedEvent(WordCompletedEvent)}
	 * 
	 * @param e
	 * @see autocomplete.core.completer.LearningCompleter#handleEvent(autocomplete.core.event.Event)
	 */
	@Override
	public void handleEvent(Event e) {
		super.handleEvent(e);
		if (e instanceof WordCompletedEvent) {
			handleWordCompletedEvent((WordCompletedEvent) e);
		} else if (e instanceof IRCSessionCloseEvent) {
			handleIRCSessionClosedEvent((IRCSessionCloseEvent) e);
		} else if (e instanceof IRCSessionStartEvent) {
			handleIRCSessionStartEvent((IRCSessionStartEvent) e);
		}
	}

	
	/* ---- Private Methods ---- */

	/**
	 * Handle the {@link IRCSessionStartEvent}, and creates the wordInSession {@link Map}.
	 *  
	 * @param e The event indicates that there is a new session.
	 */
	private void handleIRCSessionStartEvent(IRCSessionStartEvent e) {
		wordsInSession = new HashMap<String,Integer>();
		
	}


	/**
	 * Handle the {@link IRCSessionCloseEvent}, and clear the wordInSession {@link Map}, 
	 * and recalculate the probabilities. 
	 * 
	 * @param e The event indicates that the session has closed.
	 */
	private void handleIRCSessionClosedEvent(IRCSessionCloseEvent e) {
		for(String word : wordsInSession.keySet()) {
			wordBank.removeAmount(word, wordsInSession.get(word));
		}
		wordsInSession.clear();
		
	}


	/**
	 * Handle the {@link WordCompletedEvent}, add it to the wordinSession {@link Map}, 
	 * and recalculate this word probabilities.
	 * 
	 * @param e The event indicates that the word had completed.
	 */
	private void handleWordCompletedEvent(WordCompletedEvent e) {
		String word = e.getWord();
		if (!wordsInSession.containsKey(word)) {
			wordBank.putAmount(word, sessionFactor);
			wordsInSession.put(word, sessionFactor);
		} else {
			wordBank.putAmount(word, 1);
			wordsInSession.put(word, wordsInSession.get(word)+1);
		}
	}
}
