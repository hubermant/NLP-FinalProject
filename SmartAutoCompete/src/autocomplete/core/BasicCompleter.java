package autocomplete.core;

import java.util.List;
import java.util.StringTokenizer;

import utill.collection.WordRank;
import autocomplete.core.wordbank.WordsSuggestion;

public class BasicCompleter implements Completer {
	
	
	private int ngram;
	
	private int resNum;
	
	private WordsSuggestion wordBank;
	
	public BasicCompleter(int ngram, int resNm) {
		this.ngram = ngram;
		this.resNum = resNm;
		wordBank = new WordsSuggestion();
	}

	@Override
	public void train(String text) {
		StringTokenizer sentenceTokenizer =  new StringTokenizer(text, "\n.",false);
		while (sentenceTokenizer.hasMoreElements()) {
			StringTokenizer wordTokenizer =  new StringTokenizer(text, " \t,\\/-!:?\"~()<>{};*#",false);
			SentenceContainer sentenceContainer = new SentenceContainer(ngram);
			while (wordTokenizer.hasMoreElements()) {
				String word = wordTokenizer.nextToken();
				List<String> prevWords = sentenceContainer.getNgram();
				wordBank.put(prevWords, word);
			}
		}
	}

	@Override
	public List<WordRank> complete(String[] lastWords, String prefix) {
		SentenceContainer sentenceContainer = new SentenceContainer(ngram);
		sentenceContainer.setLastWords(lastWords);
		List<String> lastNWords = sentenceContainer.getNgram();
		return wordBank.get(lastNWords, prefix, resNum);
	}
	
	

}
