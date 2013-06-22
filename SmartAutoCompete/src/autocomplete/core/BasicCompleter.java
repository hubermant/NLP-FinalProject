package autocomplete.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import utill.collection.Pair;

public class BasicCompleter implements Completer {
	
	
	private int ngram;
	
	private Map<List<String>,List<Pair<String, Integer>>> posibleWordsAndThierFrequncy;

	public BasicCompleter(int ngram) {
		this.ngram = ngram;
		posibleWordsAndThierFrequncy = new HashMap<List<String>, List<Pair<String,Integer>>>();
	}

	@Override
	public void train(String text) {
		StringTokenizer sentenceTokenizer =  new StringTokenizer(text, "\n.",false);
		while (sentenceTokenizer.hasMoreElements()) {
			StringTokenizer wordTokenizer =  new StringTokenizer(text, " \t,\\/-!:?\"",false);
			SentenceContainer sentenceContainer = new SentenceContainer(ngram);
			while (wordTokenizer.hasMoreElements()) {
				String word = wordTokenizer.nextToken();
				List<String> prevWords = sentenceContainer.getNgram();
				if(!posibleWordsAndThierFrequncy.containsKey(prevWords)) {
					posibleWordsAndThierFrequncy.put(prevWords, new ArrayList<Pair<String,Integer>>());
				}
				List<Pair<String, Integer>> list = posibleWordsAndThierFrequncy.get(prevWords);
				if (list.contains(o))
			}
		}
	}

	@Override
	public List<Pair<String, Double>> complete(String[] lastWords, String prefix) {
		SentenceContainer sentenceContainer = new SentenceContainer(ngram);
		sentenceContainer.setLastWords(lastWords);
		List<String> lastNWords = sentenceContainer.getNgram();
		List<Pair<String, Integer>> posibleWords = posibleWordsAndThierFrequncy.get(lastNWords);
		
		for (Pair<String, Integer> pair : posibleWords) {
			if (pair.first().startsWith(prefix)) {
				
			}
		}
		return null;
	}
	
	

}
