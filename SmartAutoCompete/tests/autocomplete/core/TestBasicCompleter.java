package autocomplete.core;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.junit.Test;

import autocomplete.core.completer.BasicCompleter;
import autocomplete.io.AutocompleteResultWriter;

import utill.collection.WordRank;

public class TestBasicCompleter {
	
	@Test
	public void test() throws IOException {
		BasicCompleter bComp = new BasicCompleter(1, 3);
		BufferedReader textReader = new BufferedReader(new FileReader("resources/Full text of  Alice's Adventures in Wonderland.txt"));
		String corpus = "";
		String line = textReader.readLine();
		int i = 0;
		while(line != null && i < 600) {
			corpus+=line + "\n";
			line = textReader.readLine();
			i++;
		}
		
		String test = "";
		while(line != null) {
			test += line + "\n";
			line = textReader.readLine();
		}
		bComp.train(corpus);
		
		System.out.println(test.length());
		int count = 0;
		StringTokenizer sentenceTokenizer =  new StringTokenizer(test, "\n.",false);
		AutocompleteResultWriter os = new AutocompleteResultWriter(new FileWriter("resources/res.txt"));
		while (sentenceTokenizer.hasMoreElements()) {
			String sentence = sentenceTokenizer.nextToken();
			List<String> sent = new ArrayList<>();
			StringTokenizer wordTokenizer =  new StringTokenizer(sentence, " \t,\\/-!:?\"~()_<>{};*#",false);
			while (wordTokenizer.hasMoreElements()) {
				String word = wordTokenizer.nextToken();
				boolean isCompleted = false;
				for (int j=0; j < word.length() +1 && !isCompleted; j++) {
					String prefix = word.substring(0, j);
					List<WordRank> complete = bComp.complete(sent, prefix);
					for (WordRank suggestion : complete) {
						if(suggestion.getWord().equals(word)) {
							os.write(word, prefix);
							isCompleted = true;
							count++;
						}
					}
				}
				if (!isCompleted) {
					count++;
					os.write(word, word);
				}
				sent.add(word);
				
			}
			os.markNewLine();
		}
		System.out.println(count);
		os.close();
		textReader.close();
	}

}
