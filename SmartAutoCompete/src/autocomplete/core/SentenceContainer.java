package autocomplete.core;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.buffer.CircularFifoBuffer;

public class SentenceContainer {
	
	/* ---- Data Members ---- */
	
	private int ngram;
	
	private CircularFifoBuffer wordBufer;
	
	private String lastWord;
	
	public static final String START_SYMBOL = "__START__";
	
	public SentenceContainer(int ngram) {
		this.ngram = ngram;
		wordBufer = new CircularFifoBuffer(ngram);
		for (int i = 0; i < ngram - 1; i++) {
			wordBufer.add(START_SYMBOL);
		}
		lastWord = "";
	}
	
	
	/* ---- Getters and Setters --- */
	
	public String getLastWord() {
		return lastWord;
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getNgram() {
		String[] res = new String[ngram];
		res = (String[]) wordBufer.toArray(res);
		List<String> lastNWords = Arrays.asList(res);
		return lastNWords;
	}
	
	public void addCharecter(String c) {
		lastWord.concat(c);
	}
	
	public void wordEnded() {
		wordBufer.add(lastWord);
		lastWord = "";
	}
	
	public void setLastWords(String[] words) {
		for (String word : words) {
			wordBufer.add(word);
		}
	}
	
	public void addWord(String word) {
		wordBufer.add(word);
	}
	
	public void setPrefix(String prefix) {
		lastWord = prefix;
	}
	
}
