package utill;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import autocomplete.core.SentenceContainer;

public class LenguistikTokenizer {
	
	public static List<String> sentenceTokrnizer(String text) {
		List<String> res = new ArrayList<>();
		StringTokenizer sentenceTokenizer =  new StringTokenizer(text, "\n.");
		while (sentenceTokenizer.hasMoreElements()) {
			String token = sentenceTokenizer.nextToken();
			String prevToken = null;
			String currSentence = "";
			if (!(token.equals(".") || token.equals("\n"))) {
				currSentence.concat(token);
			} else {
				if (!currSentence.equals("")) {
					if (token.equals(".")) {
						res.add(currSentence);
						currSentence = "";
					} else if (prevToken.equals("\n")) {
						res.add(currSentence);
						currSentence = "";
					}
				}
			}
			prevToken = token;
		}
		return res;
	}
	
	public static String[] wordTokrnizer(String sentence) {
		List<String> res = new ArrayList<>(); 
		String inWordTokens = "-\"\'*#@$";
		StringTokenizer wordTokenizer =  new StringTokenizer(sentence, " \t,\\/!:?~()<>{};",false);
		while (wordTokenizer.hasMoreElements()) {
			String word = wordTokenizer.nextToken();
			StringTokenizer inWordTokenizer =  new StringTokenizer(word, inWordTokens);
			
		}
		return null;
	}

}
