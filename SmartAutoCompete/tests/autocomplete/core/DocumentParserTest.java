package autocomplete.core;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;

import org.junit.Test;

import autocomplete.core.completer.BasicCompleter;
import autocomplete.core.completer.Completer;
import autocomplete.core.completer.LearningCompleter;
import autocomplete.io.parse.DocumentParser;

public class DocumentParserTest {
	
	@Test
	public void testBasicCompleter() throws IOException {
		Reader trainReader = new FileReader("resources/Full text of  Alice's Adventures in Wonderland.txt");
		Reader testReader = new FileReader("resources/cybersla.txt");
		OutputStreamWriter writer = new FileWriter("resources/res-basic.txt");
		
		Completer completer = new BasicCompleter(1); 
		DocumentParser parser = new DocumentParser(completer, 3);
		
		parser.train(trainReader);
		parser.complete(testReader, writer);
	}
	@Test
	public void testLearningCompleter() throws IOException {
		Reader trainReader = new FileReader("resources/Full text of  Alice's Adventures in Wonderland.txt");
		Reader testReader = new FileReader("resources/cybersla.txt");
		OutputStreamWriter writer = new FileWriter("resources/res-learning.txt");
		
		Completer completer = new LearningCompleter(1); 
		DocumentParser parser = new DocumentParser(completer, 3);
		
		parser.train(trainReader);
		parser.complete(testReader, writer);
	}
}
