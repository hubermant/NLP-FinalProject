package utill;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * This class is a use to divide text in to sentences and sentences in to words.
 */
public class LenguistikTokenizer {
	
	/**
	 * This method divide a text in to sentences.
	 * @param text The text needed to be divided.
	 * @return A {@link List} of sentences.
	 */
	public static List<String> sentenceTokrnizer(String text) {
		List<String> res = new ArrayList<>();
		StringTokenizer sentenceTokenizer =  new StringTokenizer(text, "\n."+(char)0x0c, true);
		String prevToken = null;
		String currSentence = "";
		while (sentenceTokenizer.hasMoreElements()) {
			String token = sentenceTokenizer.nextToken();
			if (!(token.equals(".") || token.equals("\n") || token.charAt(0) ==((char)0x0c))) {
				if (!currSentence.equals("")) {
					currSentence += " " + token;
				} else {
					currSentence += token;
				}
			} else {
				if (!currSentence.equals("")) {
					if (token.charAt(0) ==((char)0x0c)) {
						res.add(currSentence);
						currSentence = "";
					}
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
		if (!currSentence.equals("")) {
			res.add(currSentence);
		}
		return res;
	}
	
	/**
	 * This method divide a sentence in to a {@link List} of words.
	 * @param sentence The sentence needed to be divided.
	 * @return A {@link List} of words.
	 */
	public static List<String> wordTokrnizer(String sentence) {
		List<String> res = new ArrayList<>(); 
		String inWordTokens = "-\"\'*#@$_";
		StringTokenizer wordTokenizer =  new StringTokenizer(sentence, " \r\t,\\/!:?~()<>{};",false);
		while (wordTokenizer.hasMoreElements()) {
			String word = wordTokenizer.nextToken();
			StringTokenizer inWordTokenizer =  new StringTokenizer(word, inWordTokens, true);
			int totalTokenNumber = inWordTokenizer.countTokens();
			if (totalTokenNumber > 1) {
				int i=1;
				String currWord="";
				while (inWordTokenizer.hasMoreElements()) {
					String token = inWordTokenizer.nextToken();
					if (inWordTokens.contains(token)) {
						if (i != 1 && i < totalTokenNumber && !currWord.equals("")) {
							currWord += token;
						}
					} else {
						currWord += token;
					}
					i++;
				}
				if (!currWord.equals("")) {
					res.add(currWord);
				}
			} else {
				if (!inWordTokens.contains(word)) {
					res.add(word);
				}
			}
		}
		return res;
	}

}
