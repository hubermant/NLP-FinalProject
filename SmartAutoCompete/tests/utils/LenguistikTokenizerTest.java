package utils;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
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
	public void testWordTok1() {
		String sentence = "Hello World";
		List<String> excpectedWords = Arrays.asList("Hello", "World");
		
		verifyWordTokenize(sentence, excpectedWords);
	}

}
