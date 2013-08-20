package autocomplete.core.completer;

import java.util.List;

import utill.collection.WordRank;
import autocomplete.core.event.Event;

/**
 * This is an interface for all the types of completers in the project.
 */
public interface Completer {
	
	/**
	 * Train the completer.
	 * @param text the train data.
	 */
	public void train(List<String> text);
	
	/**
	 * @param lastWords the last n words.
	 * @param prefix the prefix of the current word.
	 * @param resNum the number of results to return.
	 * @return a Set of k completion word proposals.
	 */
	public List<WordRank> complete(List<String> lastWords, String prefix, int resNum);
	
	/**
	 * This method handles all the events that the completer gets.
	 * @param e The event needed to be handled.
	 */
	public void handleEvent(Event e);
	
}
