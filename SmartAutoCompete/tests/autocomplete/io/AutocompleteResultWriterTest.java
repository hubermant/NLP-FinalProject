package autocomplete.io;

import static org.junit.Assert.*;
import static autocomplete.io.AutocompleteResultWriter.COMPLETION_LEFT_SEP;
import static autocomplete.io.AutocompleteResultWriter.COMPLETION_RIGHT_SEP;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;

import org.junit.Test;

public class AutocompleteResultWriterTest {

	public String result(String prefix, String suffix) {
		return prefix + COMPLETION_LEFT_SEP + suffix + COMPLETION_RIGHT_SEP;
	}
	
	@Test
	public void testSimpleResult() throws IOException {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		OutputStreamWriter out = new OutputStreamWriter(stream);
		AutocompleteResultWriter acrw = new AutocompleteResultWriter(out);
		
		acrw.write("foobar", "foo");
		out.flush();
		assertEquals(result("foo", "bar") , new String(stream.toByteArray()));
		
		stream.reset();
		
		acrw.write("foobar", "f");
		out.flush();
		assertEquals(result("f", "oobar"), new String(stream.toByteArray()));
	}
	
	@Test
	public void testNoCompletion() throws IOException {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		OutputStreamWriter out = new OutputStreamWriter(stream);
		AutocompleteResultWriter acrw = new AutocompleteResultWriter(out);
		
		acrw.write("foobar", "foobar");
		out.flush();
		assertEquals("foobar", new String(stream.toByteArray()));
	}
	
	@Test
	public void testSanitize() throws IOException {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		OutputStreamWriter out = new OutputStreamWriter(stream);
		AutocompleteResultWriter acrw = new AutocompleteResultWriter(out);
		
		String badWord = "fxx" + COMPLETION_LEFT_SEP + "zz";
		acrw.write(badWord, "fxx");
		out.flush();
		assertEquals(result("fxx", COMPLETION_LEFT_SEP + COMPLETION_LEFT_SEP + "zz"), new String(stream.toByteArray()));
		
		stream.reset();
		
		acrw.write(badWord, "fxx");
		out.flush();
		assertEquals(result("fxx" + COMPLETION_LEFT_SEP + COMPLETION_LEFT_SEP, "zz"), new String(stream.toByteArray()));
	}
	

}
