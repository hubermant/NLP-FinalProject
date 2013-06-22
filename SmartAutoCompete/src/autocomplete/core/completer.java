package autocomplete.core;

import java.util.List;

import utill.collection.Pair;

/**
 * This is an interface for all the types of completers in the project.
 */
public interface Completer {
	
	/**
	 * Train the completer.
	 * @param text the train data.
	 */
	public void train(String text);
	
	/**
	 * @param lastWords the last n words.
	 * @param prefix the prefix of the current word.
	 * @return a Set of k completion word proposals.
	 */
	public List<Pair<String, Double>> complete(String[] lastWords, String prefix);
}
