package autocomplete.io.parse.irc;

public class IRCSentence {
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
	
	@Override
	public String toString() {
		String relv = "";
		if(isRelevent){
			relv = "<"+ messageWriter +">";
		} else {
			relv = "*";
		}
		return "[" + houre + ":" + minutes +"] " + relv + " " + message;
	}
	
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

	private String parseTime(String line) {
		houre = Integer.parseInt(line.substring(1,3));
		minutes=Integer.parseInt(line.substring(4,6));
		return (String) line.substring(8);
	}

	protected int houre;
	
	public int getHoure() {
		return houre;
	}

	public void setHoure(int houre) {
		this.houre = houre;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public String getMessageWriter() {
		return messageWriter;
	}

	public void setMessageWriter(String messageWriter) {
		this.messageWriter = messageWriter;
	}

	public boolean isRelevent() {
		return isRelevent;
	}

	public void setRelevent(boolean isRelevent) {
		this.isRelevent = isRelevent;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	protected int minutes;
	
	protected String messageWriter;
				
	protected boolean isRelevent;
	
	protected String message;

}
