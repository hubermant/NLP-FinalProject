package utill.collection;

public class WordRank implements Comparable<WordRank> {

	/* ---- Data Members ---- */
	
	public WordRank(String word) {
		this.word = word;
		counter = 0;
	}

	private String word;
	
	private int counter;

	public void inc() {
		counter++;
	}


	@Override
	public int compareTo(WordRank o) {
		if (word.equals(o))
		
		return 0;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

}
