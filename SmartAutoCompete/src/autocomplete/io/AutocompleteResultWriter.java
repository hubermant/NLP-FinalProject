package autocomplete.io;

import java.io.IOException;
import java.io.OutputStreamWriter;

import autocomplete.core.completer.Completer;

/**
 * This class is a result writer for the completion process.
 * 
 * The result format:
 * He{y} th{e} f{ood} {is} o{n} th{e} ta{ble}
 * 
 * The letters in the "{}" have completed by the completer.
 * 
 *  @see Completer
 */
public class AutocompleteResultWriter {
	
	
	/* ---- Data Members ---- */
	
	/** The left completion constant separator. */
	public static final String COMPLETION_LEFT_SEP = "{";
	
	/** The right completion constant separator. */
	public static final String COMPLETION_RIGHT_SEP = "}";
	
	/** The output Stream. */
	private OutputStreamWriter out;
	
	
	/* ---- Constructors ---- */
	
	/**
	 * Constructor.
	 * 
	 * @param out The output stream.
	 */
	public AutocompleteResultWriter(OutputStreamWriter out) {
		this.out = out;
	}
	
	
	/* ---- Public Methods ---- */
	
	/**
	 * Use the method {@link AutocompleteResultWriter#write(String, String)} and catch all the exceptions.
	 * 
	 * @param word The whole word.
	 * @param prefix The typed prefix.
	 */
	public void silentWrite(String word, String prefix) {
        try {
        	this.write(word, prefix);
        }
        catch (IOException e) {
            System.err.println ("Write Operation Failed");
            e.printStackTrace ();
            throw new RuntimeException(e);
        }
	}
	
	/**
	 * write the prefix and the rest of the word beween the seperators.
	 * 
	 * @param word The whole word.
	 * @param prefix The typed prefix.
	 * @throws IOException
	 */
	public void write(String word, String prefix) throws IOException {
		String suffix= extractCompletedSuffix(word, prefix);
		prefix = this.sanitize(prefix);
		suffix  = this.sanitize(suffix);
		out.write(prefix + COMPLETION_LEFT_SEP + suffix + COMPLETION_RIGHT_SEP + " ");
	}
	
	/**
	 * write "\n" to the out stream and flush it.
	 * 
	 * @throws IOException
	 */
	public void markNewLine() throws IOException {
		out.write("\n");
		out.flush();
	}
	
	/**
	 * Flush and close the stream.
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException {
		out.flush();
		out.close();
	}
	
	
	/* ---- Private Methods ---- */
	
	/**
	 * Escaping.
	 * 
	 * @param str the string need escaping.
	 * @return
	 */
	private String sanitize(String str) {
		str = str.replace(COMPLETION_LEFT_SEP,  
				COMPLETION_LEFT_SEP + COMPLETION_LEFT_SEP);
		str = str.replace(COMPLETION_RIGHT_SEP,  
				COMPLETION_RIGHT_SEP + COMPLETION_RIGHT_SEP);
		return str;
	}

	/**
	 * This method extract the completed sufix from the word.
	 * 
	 * @param word The whole word.
	 * @param prefix The typed prefix.
	 * @return The completed sufix.
	 */
	private String extractCompletedSuffix(String word, String prefix) {
		if (word.startsWith(prefix)) {
			return word.substring(prefix.length());
		} else {
			throw new RuntimeException("Invalid prefix (word: " + word + 
										" prefix:" + prefix + ")");
		} 
	}
}
