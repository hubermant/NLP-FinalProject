package autocomplete.io;

import java.io.IOException;
import java.io.OutputStreamWriter;

public class AutocompleteResultWriter {
	
	public static final String COMPLETION_LEFT_SEP = "{";
	public static final String COMPLETION_RIGHT_SEP = "}";
	
	private OutputStreamWriter out;
	
	public AutocompleteResultWriter(OutputStreamWriter out) {
		this.out = out;
	}
	
	
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
	
	public void write(String word, String prefix) throws IOException {
		String suffix= extractCompletedSuffix(word, prefix);
		prefix = this.sanitize(prefix);
		suffix  = this.sanitize(suffix);
		out.write(prefix + COMPLETION_LEFT_SEP + suffix + COMPLETION_RIGHT_SEP + " ");
	}
	
	public void markNewLine() throws IOException {
		out.write("\n");
		out.flush();
	}
	
	public void close() throws IOException {
		out.flush();
		out.close();
	}
	private String sanitize(String str) {
		str = str.replace(COMPLETION_LEFT_SEP,  
				COMPLETION_LEFT_SEP + COMPLETION_LEFT_SEP);
		str = str.replace(COMPLETION_RIGHT_SEP,  
				COMPLETION_RIGHT_SEP + COMPLETION_RIGHT_SEP);
		return str;
	}

	private String extractCompletedSuffix(String word, String prefix) {
		if (word.startsWith(prefix)) {
			return word.substring(prefix.length());
		} else {
			throw new RuntimeException("Invalid prefix (word: " + word + 
										" prefix:" + prefix + ")");
		} 
	}
}
