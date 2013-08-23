package autocomplete.core.completer;

import java.util.List;
import java.util.StringTokenizer;

import utill.collection.WordRank;
import autocomplete.core.SentenceContainer;
import autocomplete.core.event.Event;
import autocomplete.core.wordbank.WordsSuggestion;

/**
 * This is the most basic completer.
 * This completer just suggest the most likely word according to the training data only,
 * it dose not learn and adjust during it use.
 * 
 * It is used as a base line.
 */
public class BasicCompleter implements Completer {

	
	/* ---- Data Members ---- */
	
	/** The number of words in the back that the completer consider in the completion process. */
	protected int ngram;
	
	/** The container for all the data from the training. */
	protected WordsSuggestion wordBank;
	
	
	/* ---- Constructors ---- */
	
	/**
	 * The basic constructor.
	 * 
	 * @param ngram The number of words in the back that the 
	 * 				completer consider in the completion process.
	 */
	public BasicCompleter(int ngram) {
		this.ngram = ngram;
		this.wordBank = new WordsSuggestion();
	}
	
	
	/* ---- Implemented Methods ---- */
	
	/**
	 * This method train the completer on the given sentence.
	 * 
	 * @param sentence A sentence as a {@link List} of words, for the completer to train on. 
	 * @see autocomplete.core.completer.Completer#train(java.util.List)
	 */
	@Override
	public void train(List<String> sentence) {
		SentenceContainer sentenceContainer = new SentenceContainer(ngram);
		for (String word: sentence){
			List<String> prevWords = sentenceContainer.getNgram();
			wordBank.put(prevWords, word);
			sentenceContainer.addWord(word);
		}
	}

	/**
	 * This method suggest what is the word that currently being typed by using the prefix and the lastWords.
	 *    
	 * @param lastWords A {@link List} of the previews words of the sentence.
	 * @param prefix The known prefix of the current word.
	 * @param resNum The number of suggestions the method will return.
	 * @return Suggestions of the current word.
	 * @see autocomplete.core.completer.Completer#complete(java.util.List, java.lang.String, int)
	 */
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

	/**
	 * This method dose nothing since it is the basic completer.
	 * 
	 *  @param e The {@link Event} that is being pass to the completer.
	 * @see autocomplete.core.completer.Completer#handleEvent(autocomplete.core.event.Event)
	 */
	@Override
	public void handleEvent(Event e) {
		// This is a basic completer.
	}
	
	
	/* ---- Public Methods ---- */
	
	/**
	 * This method is used to train the completer on a complete text.
	 * 
	 * @param text A text for the completer to train on. 
	 * 
	 * @deprecated This method has been replaced with {@link BasicCompleter#train(List)}, 
	 * 				and currently being used in one of the tests. 
	 */
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
}
