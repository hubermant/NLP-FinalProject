package autocomplete.core;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;

public interface CorpusParser {

	/**
	 * Train the completer.
	 * @param reader: Reader to read train text from.
	 * @throws IOException
	 */
	public void train(Reader reader) throws IOException;
	
	/**
	 * Complete all the words against the text from the reader and write the result
	 * to the output stream
	 * 
	 * @param reader: Reader to read test text from.
	 * @param out: Output stream to write the results to.
	 * @throws IOException
	 */
	public void complete(Reader reader, OutputStreamWriter out) throws IOException;
}
