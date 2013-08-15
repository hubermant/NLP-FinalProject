package autocomplete.core.event;

public class WordCompletedEvent implements Event {

	private String word;

	public WordCompletedEvent(String word) {
		this.setWord(word);
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

}
