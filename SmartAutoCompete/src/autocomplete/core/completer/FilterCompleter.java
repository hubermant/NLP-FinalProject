package autocomplete.core.completer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import utill.collection.WordRank;
import autocomplete.core.event.Event;
import autocomplete.core.event.NewWordEvent;

/**
 * This is a wrapper completer, it is used to not suggest the same word twice.
 *
 * This completer handles the events:<br>
 * {@link NewWordEvent}.
 * 
 * @see Completer
 */
public class FilterCompleter implements Completer {
	
	
	/* ---- Data Members ---- */
	
	/** The completer that will actually do all the completion. */
	private Completer inner;
	
	/** A {@link Set} containing all the last words that the completer suggested. */
	private Set<String> previousSuggestions;
	
	
	/* ---- Constructors ---- */
	
	/**
	 * Constructor.
	 * 
	 * @param inner The completer that will actually do all the completion.
	 */
	public FilterCompleter(Completer inner) {
		this.inner = inner;
		this.previousSuggestions = new HashSet<String>();
	}
	
	/* ---- Implemented Methods ---- */

	/**
	 * This method train the inner completer.
	 * 
	 * @param sentence The sentence that the completer will train on. 
	 * @see autocomplete.core.completer.Completer#train(java.util.List)
	 */
	@Override
	public void train(List<String> sentence) {
		inner.train(sentence);
	}

	/**
	 * This method use the inner completer to suggest what is the word that currently being typed
	 * by using the prefix and the lastWords.
	 * This method does not return the same suggestion twice.
	 * 
	 * @param lastWords The last words of the sentence.
	 * @param prefix The prefix of the current word.
	 * @param resNum The number of results to return.
	 * @return A {@link List} of suggested words.
	 * @see autocomplete.core.completer.Completer#complete(java.util.List, java.lang.String, int)
	 */
	@Override
	public List<WordRank> complete(List<String> lastWords, String prefix, int resNum) {
		List<WordRank> res = inner.complete(lastWords, prefix, 10000);
		List<WordRank> newRes = new ArrayList<WordRank>();
		
		
		for (WordRank rank : res) {
			if (!previousSuggestions.contains(rank.getWord())) {
				previousSuggestions.add(rank.getWord());
				newRes.add(rank);
				
				if (newRes.size() == resNum) {
					break;
				}
			}
		}
		
		return newRes;
	}

	/**
	 * This method handle the {@link NewWordEvent}, and than pass the event to the inner completer.
	 * 
	 * @param e
	 * @see autocomplete.core.completer.Completer#handleEvent(autocomplete.core.event.Event)
	 */
	@Override
	public void handleEvent(Event e) {
		if (e instanceof NewWordEvent) {
			previousSuggestions.clear();
		}
		
		inner.handleEvent(e);
	}
}
