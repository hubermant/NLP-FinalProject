package autocomplete.io.parse.irc;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;

import org.junit.Test;

import autocomplete.core.completer.BasicCompleter;
import autocomplete.core.completer.Completer;
import autocomplete.core.completer.FilterCompleter;
import autocomplete.core.completer.IRCCompleter;
import autocomplete.core.completer.LearningCompleter;

public class IRCParserTest {
	
	//@Test
	public void testIRCParser() throws IOException {
		Reader trainReader1 = new FileReader("resources/##chemistry.20100116.log.irc");
		Reader trainReader2 = new FileReader("resources/#wikipedia.20090731.log.irc");
		Reader trainReader3 = new FileReader("resources/#wikipedia-en.20100103.log.irc");
		Reader testReader = new FileReader("resources/#wikipedia-en.20100117.log.irc");
		OutputStreamWriter writer = new FileWriter("results/res-irc.txt");
		
		Completer completer = new IRCCompleter(1, 4); 
		IRCParser parser = new IRCParser(completer, 3);
		System.out.println("start training");
		parser.train(trainReader1);
		parser.train(trainReader2);
		parser.train(trainReader3);
		System.out.println("done training");
		parser.complete(testReader, writer);
	}
	
	//@Test
	public void testIRCParserLearning() throws IOException {
		Reader trainReader1 = new FileReader("resources/##chemistry.20100116.log.irc");
		Reader trainReader2 = new FileReader("resources/#wikipedia.20090731.log.irc");
		Reader trainReader3 = new FileReader("resources/#wikipedia-en.20100103.log.irc");
		Reader testReader = new FileReader("resources/#wikipedia-en.20100117.log.irc");
		OutputStreamWriter writer = new FileWriter("results/res-irc-learning-1gram.txt");
		
		Completer completer = new LearningCompleter(1); 
		IRCParser parser = new IRCParser(completer, 3);
		System.out.println("start training");
		parser.train(trainReader1);
		parser.train(trainReader2);
		parser.train(trainReader3);
		System.out.println("done training");
		parser.complete(testReader, writer);
	}

	//@Test
	public void testIRCParserBaseline() throws IOException {
		Reader trainReader1 = new FileReader("resources/##chemistry.20100116.log.irc");
		Reader trainReader2 = new FileReader("resources/#wikipedia.20090731.log.irc");
		Reader trainReader3 = new FileReader("resources/#wikipedia-en.20100103.log.irc");
		Reader testReader = new FileReader("resources/#wikipedia-en.20100117.log.irc");
		OutputStreamWriter writer = new FileWriter("results/res-irc-baseline-1gram.txt");
		
		Completer completer = new BasicCompleter(1); 
		IRCParser parser = new IRCParser(completer, 3);
		System.out.println("start training");
		parser.train(trainReader1);
		parser.train(trainReader2);
		parser.train(trainReader3);
		System.out.println("done training");
		parser.complete(testReader, writer);
	}
	
	@Test
	public void testIRCParserIRCCompleterWithFilter() throws IOException {
		Reader trainReader1 = new FileReader("resources/##chemistry.20100116.log.irc");
		Reader trainReader2 = new FileReader("resources/#wikipedia.20090731.log.irc");
		Reader trainReader3 = new FileReader("resources/#wikipedia-en.20100103.log.irc");
		Reader testReader = new FileReader("resources/#wikipedia-en.20100117.log.irc");
		OutputStreamWriter writer = new FileWriter("results/res-irc-with-filter-1gram.txt");
		
		Completer completer = new FilterCompleter(new IRCCompleter(1, 4)); 
		IRCParser parser = new IRCParser(completer, 3);
		System.out.println("start training");
		parser.train(trainReader1);
		parser.train(trainReader2);
		parser.train(trainReader3);
		System.out.println("done training");
		parser.complete(testReader, writer);
	}

}
