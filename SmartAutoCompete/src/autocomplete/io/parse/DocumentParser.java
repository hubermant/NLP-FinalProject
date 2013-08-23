package autocomplete.io.parse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.List;

import utill.LenguistikTokenizer;
import utill.collection.WordRank;
import autocomplete.core.completer.Completer;
import autocomplete.core.event.EndSentenceEvent;
import autocomplete.core.event.NewWordEvent;
import autocomplete.io.AutocompleteResultWriter;

/**
 * This is the parser for a basic texts.
 * 
 * @see CorpusParser
 */
public class DocumentParser implements CorpusParser {
	
	
	/* ---- Data Members ---- */
	
	/** The completer. */
	private Completer completer;
	
	/** The wanted number of results. */
	private int numberOfResults;

	
	/* ---- Constructors ---- */
	
	/**
	 * Constructor.
	 * 
	 * @param completer The completer to use.
	 * @param numberOfResults The wanted number of result from the completer.
	 */
	public DocumentParser(Completer completer, int numberOfResults) {
		this.completer = completer;
		this.numberOfResults = numberOfResults;
	}
	
	
	/* ---- Public Methods ---- */
	
	/**
	 * Train the completer from an regular text File.
	 * 
	 * @param reader A reader for the txt File
	 * @throws IOException
	 * @see autocomplete.io.parse.CorpusParser#train(java.io.Reader)
	 */
	@Override
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
	
	/**
	 * Simulate completion on the text file and write the results.
	 * 
	 * @param reader The text file.
	 * @param out The result writer.
	 * @throws IOException
	 * @see autocomplete.io.parse.CorpusParser#complete(java.io.Reader, java.io.OutputStreamWriter)
	 */
	@Override
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
				List<String> words = LenguistikTokenizer.wordTokrnizer(sentences.get(i));
				completeLine(words, res);
				completer.handleEvent(new EndSentenceEvent(words));
			}
			charBuf = new char[10000];
			readAmount = bufReader.read(charBuf);
			chunk = sentences.get(sentences.size()-1);
		}
		List<String> sentences = LenguistikTokenizer.sentenceTokrnizer(chunk);
		for(int i=0; i < sentences.size(); i++) {
			List<String> words = LenguistikTokenizer.wordTokrnizer(sentences.get(i));
			completeLine(words, res);
			completer.handleEvent(new EndSentenceEvent(words));
		}
			
		// Close streams
		reader.close();
		res.close();
	}
	
	
	/* ---- Private Methods ---- */
	
	/**
	 * Simulate the completion process on a given sentence, and write the results.
	 * 
	 * @param words The sentence.
	 * @param res The result writer.
	 * @throws IOException
	 */
	private void completeLine(List<String> words, AutocompleteResultWriter res) throws IOException {
		
		// For each word in the sentence
		for (int i=0; i < words.size(); i++) {
			completer.handleEvent(new NewWordEvent());
			String word = words.get(i);
			List<String> sentencePrefix = words.subList(0, i);
			boolean isCompleted = false;
			
			// For each letter in the word
			for (int j=0; j < word.length() +1 && !isCompleted; j++) {
				String wordPrefix = word.substring(0, j);
				List<WordRank> completions = completer.complete(sentencePrefix, 
																wordPrefix,
																numberOfResults);
				
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
