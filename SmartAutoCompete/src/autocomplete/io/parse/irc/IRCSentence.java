package autocomplete.io.parse.irc;

/**
 * This class is used to parse an contain all the information from an IRC sentence.
 */
public class IRCSentence {
	
	
	/* ---- Data Members ---- */
	
	/** The houre of the message. */
	protected int hour;
	
	/** The minutes of the message. */
	protected int minutes;
	
	/** The writer of the message. */
	protected String messageWriter;
	
	/** Is the message is sent by some one, ore it is a system message. */
	protected boolean isRelevent;
	
	/** The message content. */
	protected String message;
	
	
	/* ---- Constructors ---- */
	
	/**
	 * Constructor.
	 * This constructor parse the irc sentence and set all the members.
	 * 
	 * @param line A line of IRC.
	 */
	public IRCSentence(String line) {
		line = parseTime(line);
		isRelevent=!line.startsWith("*");
		if (isRelevent) {
			if (!line.startsWith("<")) {
				messageWriter = null;
				isRelevent= false;
			}  else {
				line = parseWriter(line);
			}
		} else {
			messageWriter=null;
			line = line.substring(2);
		}
		message = line;
	}
	
	
	/* ---- Private Methods ---- */
	
	/**
	 * Parse the writer from the message, and remove it from the message.
	 * 
	 * @param line The message.
	 * @return the line without the writer.
	 */
	private String parseWriter(String line) {
		messageWriter="";
		int i = 1;
		if (line.charAt(0)!='<' ) {
			System.out.println(line);
		}
		for(;line.charAt(i) != '>';i++) {
			messageWriter += line.charAt(i);
		}
		return line.substring(i+2);
	}

	/**
	 * Parse the time from the message, and remove it from the message.
	 * 
	 * @param line The message.
	 * @return The line without the time.
	 */
	private String parseTime(String line) {
		hour = Integer.parseInt(line.substring(1,3));
		minutes=Integer.parseInt(line.substring(4,6));
		return (String) line.substring(8);
	}

	
	/* ---- Getters --- */
	
	/**
	 * A getter for {@link IRCSentence#hour}
	 * 
	 * @return the hour the message was writen.
	 */
	public int getHour() {
		return hour;
	}

	/**
	 * A getter for {@link IRCSentence#}
	 * 
	 * @return the minutes the message was writen.
	 */
	public int getMinutes() {
		return minutes;
	}

	/**
	 * A getter for {@link IRCSentence#messageWriter}
	 * 
	 * @return the writer of the message.
	 */
	public String getMessageWriter() {
		return messageWriter;
	}

	/**
	 * A getter for {@link IRCSentence#isRelevent}
	 * 
	 * @return whether or not the the message is relevent.
	 */
	public boolean isRelevent() {
		return isRelevent;
	}

	/**
	 * A getter for {@link IRCSentence#message}
	 * 
	 * @return The content of the message.
	 */
	public String getMessage() {
		return message;
	}
	
	
	/* ---- Implemented Methods ---- */
	
	@Override
	public String toString() {
		String relv = "";
		if(isRelevent){
			relv = "<"+ messageWriter +">";
		} else {
			relv = "*";
		}
		return "[" + hour + ":" + minutes +"] " + relv + " " + message;
	}
	

}
