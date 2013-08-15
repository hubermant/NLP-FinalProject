package autocomplete.io.parse.irc;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;

import org.junit.Test;

import autocomplete.core.completer.Completer;
import autocomplete.core.completer.IRCCompleter;

public class IRCParserTest {
	
	@Test
	public void testIRCParser() throws IOException {
		Reader trainReader1 = new FileReader("resources/##chemistry.20100116.log");
		Reader trainReader2 = new FileReader("resources/#wikipedia.20090731.log");
		Reader trainReader3 = new FileReader("resources/#wikipedia-en.20100103.log");
		Reader testReader = new FileReader("resources/#wikipedia-en.20100117.log");
		OutputStreamWriter writer = new FileWriter("resources/res-irc.txt");
		
		Completer completer = new IRCCompleter(1, 3, 4); 
		IRCParser parser = new IRCParser(completer);
		System.out.println("start training");
		parser.train(trainReader1);
		parser.train(trainReader2);
		parser.train(trainReader3);
		System.out.println("done training");
		parser.complete(testReader, writer);
	}

}
