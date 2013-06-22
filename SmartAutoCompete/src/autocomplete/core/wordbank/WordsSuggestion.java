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
		
	}
	
	public List<WordRank> get(List<String> features, String prefix, int resultNum) {
		return null;
	}

}
