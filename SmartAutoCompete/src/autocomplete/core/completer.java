package autocomplete.core;

import java.util.Set;

/**
 * This is an interface for all the types of completers in the project.
 */
public interface completer {
	
	/**
	 * Train the completer.
	 * @param text the train data.
	 */
	public void train(String[] text);
	
	/**
	 * @param lastWords the last n words.
	 * @param prefix the prefix of the current word.
	 * @return a Set of k completion word proposals.
	 */
	public Set<String> complete(String[] lastWords, String prefix);
}
