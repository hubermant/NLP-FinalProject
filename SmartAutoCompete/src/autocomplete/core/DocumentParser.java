package autocomplete.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import utill.collection.WordRank;

import autocomplete.io.AutocompleteResultWriter;

public class DocumentParser implements CorpusParser {
	
	private Completer completer;
	
	public DocumentParser(Completer completer) {
		this.completer = completer;
	}
	
	public void train(Reader reader) throws IOException {
		BufferedReader bufReader = new BufferedReader(reader);  
		String line = bufReader.readLine();
		
		while(line != null) {
			completer.train(parseSentense(line));
			line = bufReader.readLine();
		}
		
		bufReader.close();
	}
	
	
	public void complete(Reader reader, OutputStreamWriter out) throws IOException {
		BufferedReader bufReader = new BufferedReader(reader);  
		AutocompleteResultWriter res = new AutocompleteResultWriter(out);
		String line = bufReader.readLine();
		
		while(line != null) {
			List<String> sentence = parseSentense(line);
			
			// For each word in the sentence
			for (int i=0; i < sentence.size(); i++) {
				String word = sentence.get(i);
				List<String> sentencePrefix = sentence.subList(0, i);
				boolean isCompleted = false;
				
				// For each letter in the word
				for (int j=0; j < word.length() +1 && !isCompleted; j++) {
					String wordPrefix = word.substring(0, j);
					List<WordRank> completions = completer.complete(sentencePrefix, 
																	wordPrefix);
					
					// Check all the completions
					for (WordRank suggestion : completions) {
						if(suggestion.getWord().equals(word)) {
							res.write(word, wordPrefix);
							isCompleted = true;
						}
					}
				}
				
				// Check if the word completed successfully.
				if (!isCompleted) {
					res.write(word, word);
				}
			}
			
			// Read another line
			line = bufReader.readLine();
		}
		
		// Close streams
		reader.close();
		res.close();
	}
	
	private List<String> parseSentense(String line) {
		List<String> words = new ArrayList<String>();
		StringTokenizer wordTokenizer =  new StringTokenizer(line, " \t,\\/-!:?\"~()_<>{};*#", false);
		while (wordTokenizer.hasMoreElements()) {
			words.add(wordTokenizer.nextToken());
		}
		return words;
		
	}
}
