package utils;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import utill.LenguistikTokenizer;

public class LenguistikTokenizerTest {
	
	public void verifySentenceTokenize(String text, List<String> excpectedSentences) {
		assertEquals(excpectedSentences, LenguistikTokenizer.sentenceTokrnizer(text));
	}
	
	public void verifyWordTokenize(String sentence, List<String> excpectedWords) {
		assertEquals(excpectedWords, LenguistikTokenizer.wordTokrnizer(sentence));
	}
	
	@Test
	public void testSentenceTok1() {
		String text = 
				"Hello World.\n" +
				"Hello World.\n";
		List<String> excpectedSentences = 
				Arrays.asList("Hello World", "Hello World");
		
		verifySentenceTokenize(text, 
				excpectedSentences);
	}
	
	@Test
	public void testSentenceTok2() {
		String text = 
				"Hello World..\n" +
						"Hello World\n\n";
		List<String> excpectedSentences = 
				Arrays.asList("Hello World", "Hello World");
		
		verifySentenceTokenize(text, 
				excpectedSentences);
	}
	
	@Test
	public void testSentenceTok3() {
		String text = 
				"Hello World..\n" +
						"Hello World";
		List<String> excpectedSentences = 
				Arrays.asList("Hello World", "Hello World");
		
		verifySentenceTokenize(text, 
				excpectedSentences);
	}
	
	@Test
	public void testSentenceTok4() {
		String text = 
				"Hello World\n\n" +
						"Hello World";
		List<String> excpectedSentences = 
				Arrays.asList("Hello World", "Hello World");
		
		verifySentenceTokenize(text, 
				excpectedSentences);
	}
	
	
	@Test
	public void testWordTok1() {
		String sentence = "Hello World";
		List<String> excpectedWords = Arrays.asList("Hello", "World");
		
		verifyWordTokenize(sentence, excpectedWords);
	}
	
	@Test
	public void testWordTok2() {
		String sentence = "I can't go, to t#e \"be@tch\"";
		List<String> excpectedWords = Arrays.asList("I", "can't", "go", "to", "t#e", "be@tch");
		
		verifyWordTokenize(sentence, excpectedWords);
	}

	@Test
	public void testWordTok3() {
		String sentence = "\"";
		List<String> excpectedWords = new ArrayList<>();
		
		verifyWordTokenize(sentence, excpectedWords);
	}

}
