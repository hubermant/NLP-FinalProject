package autocomplete.core.wordbank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import utill.collection.WordRank;

public class WordsSuggestion {
	
	/* ---- Data Members ---- */
	
	private Map<List<String>,SortedMap<String, WordRank>> featuresToWords;
	
	private Map<String, WordRank> wordToFrequency;
	
	private long totalNumberOfWords = 0;
	
	
	/* ---- Constructors ---- */
	
	public long getTotalNumberOfWords() {
		return totalNumberOfWords;
	}


	public WordsSuggestion() {
		featuresToWords = new HashMap<List<String>, SortedMap<String,WordRank>>();
		wordToFrequency = new HashMap<String, WordRank>();
	}
	
	
	/* ---- Public Methods ---- */
	
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
	
	public void putAmount(String word, int amount) {
		for (List<String> features : featuresToWords.keySet()) {
			if (featuresToWords.get(features).containsKey(word)) {
				putAmount(word, amount, features);
			}
		}
	}


	public void putAmount(String word, int amount, List<String> features) {
		for(int i = 0; i < amount; i++) {
			put(features, word);
		}
	}
	
	public void removeAmount(String word, int amount) {
		for (SortedMap<String, WordRank> map : featuresToWords.values()) {
			if (map.containsKey(word)) {
				map.get(word).dec(amount);
				wordToFrequency.get(word).dec(amount);
				totalNumberOfWords-=amount;
			}
		} 
	}
	
	public List<WordRank> get(List<String> features, String prefix, int resultNum) {
		List<WordRank> segustions = getByFeetchersAndPrefix(features, prefix); 
		if (segustions.size() < resultNum) {
			return segustions.subList(0, segustions.size());
		} else {
			return segustions.subList(0, resultNum);
		}
	}

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
	
	public Map<String, WordRank> getWordCountMap() {
		return wordToFrequency;
	}
	
	public SortedMap<String, WordRank> filterPrefix(SortedMap<String,WordRank> baseMap, String prefix) {
	    if(prefix.length() > 0) {
	        char nextLetter = (char) (prefix.charAt(prefix.length() -1) + 1);
	        String end = prefix.substring(0, prefix.length()-1) + nextLetter;
	        return baseMap.subMap(prefix, end);
	    }
	    return baseMap;
	}

}
