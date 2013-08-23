package autocomplete.core.completer;

import java.util.List;

import utill.collection.WordRank;
import autocomplete.core.event.Event;

/**
 * This is an interface for all the types of completers in the project.
 */
public interface Completer {
	
	/**
	 * Train the completer on the given sentence.
	 * 
	 * @param sentence The sentence that the completer will train on.
	 */
	public void train(List<String> sentence);
	
	/**
	 * This method suggest what is the word that currently being typed by using the prefix and the lastWords.
	 * 
	 * @param lastWords The last words of the sentence.
	 * @param prefix The prefix of the current word.
	 * @param resNum The number of results to return.
	 * @return A {@link List} of suggested words.
	 */
	public List<WordRank> complete(List<String> lastWords, String prefix, int resNum);
	
	/**
	 * This method handles all the events that the completer gets.
	 * 
	 * @param e The event needed to be handled.
	 */
	public void handleEvent(Event e);
	
}
