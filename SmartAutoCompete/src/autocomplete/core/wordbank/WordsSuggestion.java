package autocomplete.core.wordbank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import autocomplete.core.completer.Completer;

import utill.collection.WordRank;

/**
 * This class contains all the data the completer learns, 
 * the number of occurrences each word had, 
 * the number of occurrences of each word after a certain ngram,
 * and total number of words.
 * 
 * @see Completer
 * @see WordRank
 */
public class WordsSuggestion {
	
	
	/* ---- Data Members ---- */
	
	/** {@link Map} between the n-gram words to a sorted map of the word to there occurrences. */
	private Map<List<String>,SortedMap<String, WordRank>> featuresToWords;
	
	/** The number of time each word appeared. */
	private Map<String, WordRank> wordToFrequency;
	
	/** The total number of words. */
	private long totalNumberOfWords = 0;
	
	
	/* ---- Constructors ---- */

	/**
	 * Default constructor.
	 */
	public WordsSuggestion() {
		featuresToWords = new HashMap<List<String>, SortedMap<String,WordRank>>();
		wordToFrequency = new HashMap<String, WordRank>();
	}
	
	
	/* ---- Public Methods ---- */
	
	/**
	 * update all the occurrences after this word was seen after this specific n-gram.
	 *  
	 * @param features The n-gram.
	 * @param word The word that appeared after that n-gram.
	 */
	public void put(List<String> features, String word) {
		if (!wordToFrequency.containsKey(word)) {
			wordToFrequency.put(word, new WordRank(word));
		}
		wordToFrequency.get(word).inc();
		if (!featuresToWords.containsKey(features)) {
			featuresToWords.put(features, new TreeMap<String, WordRank>());
		}
		if (!featuresToWords.get(features).containsKey(word)) {
			featuresToWords.get(features).put(word, new WordRank(word));
		}
		WordRank wordRank = featuresToWords.get(features).get(word);
		wordRank.inc();
		totalNumberOfWords++;
	}
	
	/**
	 * This method update all the counters of this word amount times.
	 * 
	 * @param word The word needed to be updated.
	 * @param amount The amount of occurrences to update.
	 */
	public void putAmount(String word, int amount) {
		for (List<String> features : featuresToWords.keySet()) {
			if (featuresToWords.get(features).containsKey(word)) {
				putAmount(word, amount, features);
			}
		}
	}

	/**
	 * Update all the occurrences after this word was seen after this specific n-gram, amount times.
	 * 
	 * @param word The word that appeared after that n-gram.
	 * @param amount The amount of times to add to the occurrences.
	 * @param features The n-gram.
	 */
	public void putAmount(String word, int amount, List<String> features) {
		for(int i = 0; i < amount; i++) {
			put(features, word);
		}
	}
	
	/**
	 * This method is used after a session to reduce the number of occurrences.
	 * 
	 * @param word The word needed to be update.
	 * @param amount The amount need to be reduce.
	 */
	public void removeAmount(String word, int amount) {
		for (SortedMap<String, WordRank> map : featuresToWords.values()) {
			if (map.containsKey(word)) {
				map.get(word).dec(amount);
				wordToFrequency.get(word).dec(amount);
				totalNumberOfWords-=amount;
			}
		} 
	}
	
	/**
	 * This method return exactly resultNum suggestions for completion.
	 * 
	 * @param features The n-gram.
	 * @param prefix The current word prefix.
	 * @param resultNum The number of wanted result.
	 * @return Suggestions for the current word.
	 * 
	 * @deprecated This method is used only in the tests, the cut of the result number is now in the completer. 
	 */
	@Deprecated
	public List<WordRank> get(List<String> features, String prefix, int resultNum) {
		List<WordRank> segustions = getByFeetchersAndPrefix(features, prefix); 
		if (segustions.size() < resultNum) {
			return segustions.subList(0, segustions.size());
		} else {
			return segustions.subList(0, resultNum);
		}
	}

	/**
	 * filter the {@link SortedMap} for only the words start with the given prefix.
	 * 
	 * @param baseMap The base map.
	 * @param prefix The prefix that used to filter the map.
	 * @return A {@link Map} contains only words start with the given prefix.
	 */
	public SortedMap<String, WordRank> filterPrefix(SortedMap<String,WordRank> baseMap, String prefix) {
	    if(prefix.length() > 0) {
	        char nextLetter = (char) (prefix.charAt(prefix.length() -1) + 1);
	        String end = prefix.substring(0, prefix.length()-1) + nextLetter;
	        return baseMap.subMap(prefix, end);
	    }
	    return baseMap;
	}


	/**
	 * Return all the possible words given this n-gram and this prefix.
	 * 
	 * @param features The n-gram.
	 * @param prefix The word prefix.
	 * @return All the possible suggestions.
	 */
	public List<WordRank> getByFeetchersAndPrefix(List<String> features, String prefix) {
		ArrayList<WordRank> segustions = new ArrayList<>(wordToFrequency.values());
		if (featuresToWords.containsKey(features)) {
			SortedMap<String,WordRank> filteredMap = filterPrefix(featuresToWords.get(features), prefix);
			segustions = new ArrayList<>(filteredMap.values());
		} 
		Collections.sort(segustions);
		Collections.reverse(segustions);
		return segustions;
	}
	
	
	/* ---- Getters --- */
	
	/**
	 * This is a getter for the {@link WordsSuggestion#totalNumberOfWords}
	 * 
	 * @return The total number of words appeared.
	 */
	public long getTotalNumberOfWords() {
		return totalNumberOfWords;
	}
	
	/**
	 * This is a getter for the {@link WordsSuggestion#wordToFrequency}
	 * 
	 * @return A {@link Map} between each word to the number of occurrences.
	 */
	public Map<String, WordRank> getWordCountMap() {
		return wordToFrequency;
	}
	
}
