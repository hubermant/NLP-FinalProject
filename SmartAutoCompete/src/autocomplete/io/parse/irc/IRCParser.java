package autocomplete.io.parse.irc;

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
import autocomplete.core.event.WordCompletedEvent;
import autocomplete.core.event.irc.IRCEvent;
import autocomplete.core.event.irc.IRCSessionCloseEvent;
import autocomplete.core.event.irc.IRCSessionIdentEvent;
import autocomplete.core.event.irc.IRCSessionStartEvent;
import autocomplete.io.AutocompleteResultWriter;
import autocomplete.io.parse.CorpusParser;

public class IRCParser implements CorpusParser {
	
	private Completer completer;
	private int numberOfResults;

	public IRCParser(Completer completer, int numberOfResults) {
		this.completer=completer;
		this.numberOfResults=numberOfResults;
	}

	@Override
	public void train(Reader reader) throws IOException {
		BufferedReader bufReader = new BufferedReader(reader);
		String line;
		line = bufReader.readLine();
		
		while (line != null) {
			if (line.startsWith("[")) {
				IRCSentence sentence = new IRCSentence(line);
				if (sentence.isRelevent()){
					List<String> words = LenguistikTokenizer.wordTokrnizer(sentence.getMessage());
					completer.train(words);
				}
			}
			line = bufReader.readLine();
		}
	
		// Close streams
		reader.close();
	}

	@Override
	public void complete(Reader reader, OutputStreamWriter out)
			throws IOException {
		AutocompleteResultWriter res = new AutocompleteResultWriter(out);
		BufferedReader bufReader = new BufferedReader(reader);
		String line;
		line = bufReader.readLine();
		
		while (line != null) {
			if (line.startsWith("Session Start")) {
				completer.handleEvent(new IRCSessionStartEvent(line.substring(15)));
			} else if (line.startsWith("Session Close")) {
				completer.handleEvent(new IRCSessionCloseEvent(line.substring(15)));
			} else if (line.startsWith("Session Ident")) {
				completer.handleEvent(new IRCSessionIdentEvent(line.substring(15)));
			} else if (line.startsWith("[")) {
				IRCSentence sentence = new IRCSentence(line);
				
				if (sentence.isRelevent()) {
					completer.handleEvent(new IRCEvent(sentence));
					List<String> words = LenguistikTokenizer.wordTokrnizer(sentence.getMessage());
					completeLine(words, res);
					completer.handleEvent(new EndSentenceEvent(words));
				}
			}
			line = bufReader.readLine();
		}
	
		// Close streams
		reader.close();
		res.close();
	}
	
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
						completer.handleEvent(new WordCompletedEvent(word));
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
