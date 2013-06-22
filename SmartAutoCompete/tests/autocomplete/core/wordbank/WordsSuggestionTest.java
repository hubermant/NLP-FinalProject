package autocomplete.core.wordbank;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import utill.collection.WordRank;

public class WordsSuggestionTest {

	@Test
	public void testSimpleFlow() {
		WordsSuggestion ws = new WordsSuggestion();
		
		ws.put(Arrays.asList("foo", "bar"), "baz");
		ws.put(Arrays.asList("foo", "bar"), "baz");
		
		ws.put(Arrays.asList("foo", "bar"), "blah");
		
		List<WordRank> res = ws.get(Arrays.asList("foo", "bar"), "", 3);
		assertEquals(2, res.size());
		assertEquals("baz", res.get(0).getWord());
		assertEquals(2, res.get(0).getCounter());
		
		assertEquals("blah", res.get(1).getWord());
		assertEquals(1, res.get(1).getCounter());
	}
	
	@Test
	public void testUnkownFeatures() {
		WordsSuggestion ws = new WordsSuggestion();
		
		List<WordRank> res = ws.get(Arrays.asList("foo", "bar"), "", 3);
		assertEquals(0, res.size());
	}
	
	@Test
	public void testUnkownPrefix() {
		WordsSuggestion ws = new WordsSuggestion();
		
		ws.put(Arrays.asList("foo", "bar"), "baz");
		List<WordRank> res = ws.get(Arrays.asList("foo", "bar"), "zoo", 3);
		assertEquals(0, res.size());
	}
	
	@Test
	public void testRetrunBestResults() {
		WordsSuggestion ws = new WordsSuggestion();
		
		ws.put(Arrays.asList("foo", "bar"), "baz");
		ws.put(Arrays.asList("foo", "bar"), "baz");
		ws.put(Arrays.asList("foo", "bar"), "baz");
		ws.put(Arrays.asList("foo", "bar"), "baz");
		
		ws.put(Arrays.asList("foo", "bar"), "blah");
		ws.put(Arrays.asList("foo", "bar"), "blah");
		ws.put(Arrays.asList("foo", "bar"), "blah");
		
		ws.put(Arrays.asList("foo", "bar"), "boo");
		ws.put(Arrays.asList("foo", "bar"), "boo");
		
		ws.put(Arrays.asList("foo", "bar"), "bamba");
		
		List<WordRank> res = ws.get(Arrays.asList("foo", "bar"), "", 3);
		assertEquals(3, res.size());
		assertEquals("baz", res.get(0).getWord());
		assertEquals(4, res.get(0).getCounter());
		
		assertEquals("blah", res.get(1).getWord());
		assertEquals(3, res.get(1).getCounter());
		
		assertEquals("boo", res.get(2).getWord());
		assertEquals(2, res.get(2).getCounter());
	}

	@Test
	public void testRetrunBestResultsWithPrefix() {
		WordsSuggestion ws = new WordsSuggestion();
		
		ws.put(Arrays.asList("foo", "bar"), "baz");
		ws.put(Arrays.asList("foo", "bar"), "baz");
		ws.put(Arrays.asList("foo", "bar"), "baz");
		ws.put(Arrays.asList("foo", "bar"), "baz");
		
		ws.put(Arrays.asList("foo", "bar"), "blah");
		ws.put(Arrays.asList("foo", "bar"), "blah");
		ws.put(Arrays.asList("foo", "bar"), "blah");
		
		ws.put(Arrays.asList("foo", "bar"), "boo");
		ws.put(Arrays.asList("foo", "bar"), "boo");
		
		ws.put(Arrays.asList("foo", "bar"), "bamba");
		
		List<WordRank> res = ws.get(Arrays.asList("foo", "bar"), "ba", 3);
		assertEquals(2, res.size());
		assertEquals("baz", res.get(0).getWord());
		assertEquals(4, res.get(0).getCounter());
		
		assertEquals("bamba", res.get(1).getWord());
		assertEquals(1, res.get(1).getCounter());
	}

}
