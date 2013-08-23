package utill.collection;

/**
 * This class is use to obtain the information of a word and its occurrences together.
 * 
 * @see Comparable
 */
public class WordRank implements Comparable<WordRank> {

	
	/* ---- Data Members ---- */
	
	/** The word. */
	private String word;
	
	/** The number of occurrences.  */
	private int counter;
	
	
	/* ---- Constructors ---- */
	
	/**
	 * Constructor.
	 * 
	 * @param word The word.
	 */
	public WordRank(String word) {
		this.word = word;
		this.counter = 0;
	}
	
	
	/* ---- Public Methods ---- */

	/**
	 * Increase the number of occurrences by one.
	 */
	public void inc() {
		counter++;
	}
	
	/**
	 * Increase the number of occurrences by amount.
	 * 
	 * @param amount the number to increase with.
	 */
	public void inc(int amount) {
		counter += amount;
	}

	/**
	 * Decrease the number of occurrences by one.
	 */
	public void dec() {
		counter--;
	}
	
	/**
	 * Decrease the number of occurrences by amount.
	 * 
	 * @param amount the number to decrease with.
	 */
	public void dec(int amount) {
		counter -= amount;
	}
	
	
	/* ---- Implemented Methods ---- */

	/**
	 * compare the counter of two {@link WordRank}
	 * @param o the {@link WordRank} to compare with.
	 * @return -1 if smaller
	 * 			0 if equal
	 * 			1 if bigger
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(WordRank o) {
		if (counter < o.getCounter()) {
			return -1;
		} else if (counter == o.getCounter()) {
			return 0;
		} else {
			return 1;
		}
	}
	
	/**
	 * The format:
	 * [word]-[counter] 
	 * @return The object as {@link String}. 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return word + "-" + counter;
	}
	
	
	/* ---- Getters --- */

	/**
	 * A getter for {@link WordRank#word}.
	 * 
	 * @return The word.
	 */
	public String getWord() {
		return word;
	}

	/**
	 * A getter for {@link WordRank#counter}.
	 * 
	 * @return The number of occurrences.
	 */
	public int getCounter() {
		return counter;
	}

}
