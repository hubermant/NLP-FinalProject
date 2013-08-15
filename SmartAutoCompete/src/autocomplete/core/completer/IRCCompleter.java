package autocomplete.core.completer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utill.collection.WordRank;
import autocomplete.core.SentenceContainer;
import autocomplete.core.event.Event;
import autocomplete.core.event.WordCompletedEvent;
import autocomplete.core.event.irc.IRCSessionCloseEvent;
import autocomplete.core.event.irc.IRCSessionStartEvent;

public class IRCCompleter extends LearningCompleter {
	
	private Map<String,Integer> wordsInSession;
	private int sessionFactor;
	

	public IRCCompleter(int ngram, int resNum, int sessionFactor) {
		super(ngram,resNum);
		this.sessionFactor=sessionFactor;
	}


	@Override
	public List<WordRank> complete(List<String> lastWords, String prefix) {
		SentenceContainer sentenceContainer = new SentenceContainer(ngram);
		sentenceContainer.setLastWords(lastWords);
		List<String> lastNWords = sentenceContainer.getNgram();
		List<WordRank> sugestions = wordBank.getByFeetchersAndPrefix(lastNWords, prefix);
		if(sugestions.size() > resNum) {
			return sugestions.subList(0, resNum);
		} else {
			return sugestions;
		}
	}

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


	private void handleIRCSessionStartEvent(IRCSessionStartEvent e) {
		wordsInSession = new HashMap<String,Integer>();
		
	}


	private void handleIRCSessionClosedEvent(IRCSessionCloseEvent e) {
		for(String word : wordsInSession.keySet()) {
			wordBank.removeAmount(word, wordsInSession.get(word));
		}
		wordsInSession.clear();
		
	}


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
