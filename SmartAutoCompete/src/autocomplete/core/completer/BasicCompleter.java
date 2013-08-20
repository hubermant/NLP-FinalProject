package autocomplete.core.completer;

import java.util.List;
import java.util.StringTokenizer;

import utill.collection.WordRank;
import autocomplete.core.SentenceContainer;
import autocomplete.core.event.Event;
import autocomplete.core.wordbank.WordsSuggestion;

public class BasicCompleter implements Completer {
	
	
	protected int ngram;
	
	protected WordsSuggestion wordBank;
	
	public BasicCompleter(int ngram) {
		this.ngram = ngram;
		wordBank = new WordsSuggestion();
	}
	@Deprecated
	public void train(String text) {
		StringTokenizer sentenceTokenizer =  new StringTokenizer(text, "\n.",false);
		while (sentenceTokenizer.hasMoreElements()) {
			String sentence = sentenceTokenizer.nextToken();
			StringTokenizer wordTokenizer =  new StringTokenizer(sentence, " \t,\\/-!:?\"~()<>{};*#",false);
			SentenceContainer sentenceContainer = new SentenceContainer(ngram);
			while (wordTokenizer.hasMoreElements()) {
				String word = wordTokenizer.nextToken();
				List<String> prevWords = sentenceContainer.getNgram();
				wordBank.put(prevWords, word);
				sentenceContainer.addWord(word);
			}
		}

	}
	
	@Override
	public void train(List<String> sentence) {
		SentenceContainer sentenceContainer = new SentenceContainer(ngram);
		for (String word: sentence){
			List<String> prevWords = sentenceContainer.getNgram();
			wordBank.put(prevWords, word);
			sentenceContainer.addWord(word);
		}
	}

	@Override
	public List<WordRank> complete(List<String> lastWords, String prefix, int resNum) {
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
		// This is a basic completer.
	}
}
