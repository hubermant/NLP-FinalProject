package autocomplete.core;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.buffer.CircularFifoBuffer;

/**
 * This class is a container for a sentence.
 * It is used to cut the n-gram from the sentence.
 */
public class SentenceContainer {
	
	/* ---- Data Members ---- */
	
	/** The number of previews words */
	private int ngram;
	
	/** A circular buffer contains the last words. */
	private CircularFifoBuffer wordBufer;
	
	/** The last word. */
	private String lastWord;
	
	/** A constant for the start symbol. */
	public static final String START_SYMBOL = "__START__";
	
	
	/* ---- Constructors ---- */
	
	/**
	 * Constructor.
	 * 
	 * @param ngram The number of preview words.
	 */
	public SentenceContainer(int ngram) {
		this.ngram = ngram;
		wordBufer = new CircularFifoBuffer(ngram);
		for (int i = 0; i < ngram - 1; i++) {
			wordBufer.add(START_SYMBOL);
		}
		lastWord = "";
	}
	
	
	/* ---- Getters and Setters --- */
	
	/**
	 * This is a getter for the {@link SentenceContainer#lastWord}.
	 * 
	 * @return The last word.
	 */
	public String getLastWord() {
		return lastWord;
	}
	
	/**
	 * Return the current n-gram of the sentence.
	 * 
	 * @return A {@link List} of all the last words.
	 */
	@SuppressWarnings("unchecked")
	public List<String> getNgram() {
		String[] res = new String[ngram];
		res = (String[]) wordBufer.toArray(res);
		List<String> lastNWords = Arrays.asList(res);
		return lastNWords;
	}
	
	/**
	 * Add a {@link Character} to the last word.
	 * @param c
	 */
	public void addCharecter(String c) {
		lastWord.concat(c);
	}
	
	/**
	 * Add the last word to the buffer and empty it.
	 */
	public void wordEnded() {
		wordBufer.add(lastWord);
		lastWord = "";
	}
	
	/**
	 * Add the given words to the buffer.
	 * 
	 * @param words a {@link List} of words.
	 */
	public void setLastWords(List<String> words) {
		for (String word : words) {
			wordBufer.add(word);
		}
	}
	
	/**
	 * This method add a word to the sentence.
	 * 
	 * @param word The added word.
	 */
	public void addWord(String word) {
		wordBufer.add(word);
	}
	
	/**
	 * Set the last word.
	 * 
	 * @param prefix The last word prefix.
	 */
	public void setPrefix(String prefix) {
		lastWord = prefix;
	}
	
}
