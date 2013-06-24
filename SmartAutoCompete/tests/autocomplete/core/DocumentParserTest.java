package autocomplete.core;

import static org.junit.Assert.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;

import org.junit.Test;

public class DocumentParserTest {
	
	@Test
	public void test() throws IOException {
		Reader trainReader = new FileReader("resources/Full text of  Alice's Adventures in Wonderland.txt");
		Reader testReader = new FileReader("resources/Full text of  Alice's Adventures in Wonderland.txt");
		OutputStreamWriter writer = new FileWriter("resources/res.txt");
		
		Completer completer = new BasicCompleter(1, 3); 
		DocumentParser parser = new DocumentParser(completer);
		
		parser.train(trainReader);
		parser.complete(testReader, writer);
	}
}
