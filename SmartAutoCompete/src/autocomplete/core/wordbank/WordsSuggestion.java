package autocomplete.core.wordbank;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import utill.collection.WordRank;

public class WordsSuggestion {
	
	/* ---- Data Members ---- */
	
	private Map<List<String>,SortedMap<String, WordRank>> featuresToWords;
	
	private Map<String, Integer> wordToFrequency;
	
	
	/* ---- Constructors ---- */
	
	public WordsSuggestion() {
		featuresToWords = new HashMap<List<String>, SortedMap<String,WordRank>>();
		wordToFrequency = new HashMap<String, Integer>();
	}
	
	
	/* ---- Public Methods ---- */
	
	public void put(List<String> features, String word) {
		if (!wordToFrequency.containsKey(word)) {
			wordToFrequency.put(word, 0);
		}
		wordToFrequency.put(word, wordToFrequency.get(word) + 1);
		if (!featuresToWords.containsKey(features)) {
			featuresToWords.put(features, new TreeMap<String, WordRank>());
		}
		if (!featuresToWords.get(features).containsKey(word)) {
			featuresToWords.get(features).put(word, new WordRank(word));
		}
		WordRank wordRank = featuresToWords.get(features).get(word);
		wordRank.inc();
	}
	
	public List<WordRank> get(List<String> features, String prefix, int resultNum) {
		if (featuresToWords.containsKey(features)) {
			SortedMap<String,WordRank> filteredMap = filterPrefix(featuresToWords.get(features), prefix);
			return null;
		} else {
			//TODO: need to think what to do in case where the features never appeared before. 
			return null;
		}
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
