package autocomplete.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.List;

import utill.LenguistikTokenizer;
import utill.collection.WordRank;
import autocomplete.io.AutocompleteResultWriter;

public class DocumentParser implements CorpusParser {
	
	private Completer completer;
	
	public DocumentParser(Completer completer) {
		this.completer = completer;
	}
	
	public void train(Reader reader) throws IOException {
		BufferedReader bufReader = new BufferedReader(reader);
		char[] charBuf = new char[10000];
		int readAmount = bufReader.read(charBuf);
		String chunk = "";
		while (readAmount != -1) {
			String readChunk = new String(charBuf);
			chunk+=readChunk;
			List<String> sentences = LenguistikTokenizer.sentenceTokrnizer(chunk);
			for(int i=0; i < sentences.size()-1; i++) {
				completer.train(LenguistikTokenizer.wordTokrnizer((sentences.get(i))));
			}
			charBuf = new char[10000];
			readAmount = bufReader.read(charBuf);
			chunk = sentences.get(sentences.size()-1);
		}
		List<String> sentences = LenguistikTokenizer.sentenceTokrnizer(chunk);
		for(int i=0; i < sentences.size(); i++) {
			completer.train(LenguistikTokenizer.wordTokrnizer(sentences.get(i)));
		}
		bufReader.close();
	}
	
	
	public void complete(Reader reader, OutputStreamWriter out) throws IOException {
		AutocompleteResultWriter res = new AutocompleteResultWriter(out);
		BufferedReader bufReader = new BufferedReader(reader);
		char[] charBuf = new char[10000];
		int readAmount = bufReader.read(charBuf);
		String chunk = "";
		while (readAmount != -1) {
			String readChunk = new String(charBuf);
			chunk+=readChunk;
			List<String> sentences = LenguistikTokenizer.sentenceTokrnizer(chunk);
			for(int i=0; i < sentences.size()-1; i++) {
				completeLine(sentences.get(i), res);
			}
			charBuf = new char[10000];
			readAmount = bufReader.read(charBuf);
			chunk = sentences.get(sentences.size()-1);
		}
		List<String> sentences = LenguistikTokenizer.sentenceTokrnizer(chunk);
		for(int i=0; i < sentences.size(); i++) {
			completeLine(sentences.get(i), res);
		}
			
		// Close streams
		reader.close();
		res.close();
	}
	
	
	private void completeLine(String sentence, AutocompleteResultWriter res) throws IOException {
		List<String> words = LenguistikTokenizer.wordTokrnizer(sentence);
		
		// For each word in the sentence
		for (int i=0; i < words.size(); i++) {
			String word = words.get(i);
			List<String> sentencePrefix = words.subList(0, i);
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
		res.markNewLine();
		// Read another line
	}
}
