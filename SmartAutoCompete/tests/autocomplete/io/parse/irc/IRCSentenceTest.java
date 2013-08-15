package autocomplete.io.parse.irc;

import static org.junit.Assert.*;

import org.junit.Test;

public class IRCSentenceTest {
	@Test
	public void testParsingIrelevent() {
		IRCSentence ircSentence = new IRCSentence("[18:43] * Now talking in ##chemistry");
		assertEquals(18,ircSentence.houre);
		assertEquals(43,ircSentence.minutes);
		assertFalse(ircSentence.isRelevent);
		assertEquals("Now talking in ##chemistry",ircSentence.message);
		System.out.println(ircSentence);
	}
	
	@Test
	public void testParsingIreleventPing() {
		IRCSentence ircSentence = new IRCSentence("[18:43] [Pinchie:#wikipedia PING]");
		assertEquals(18,ircSentence.houre);
		assertEquals(43,ircSentence.minutes);
		assertFalse(ircSentence.isRelevent);
		System.out.println(ircSentence.message);
		assertEquals("[Pinchie:#wikipedia PING]",ircSentence.message);
		System.out.println(ircSentence);
	}
	
	@Test
	public void testParsingIreleventChanServ() {
		IRCSentence ircSentence = new IRCSentence("[19:57] -ChanServ- [#biology] Welcome to the channel. You are free to ask in the channel or message an OP for a question. Check out biology sites, using google, or http://biology.about.com/");
		assertEquals(19,ircSentence.houre);
		assertEquals(57,ircSentence.minutes);
		assertFalse(ircSentence.isRelevent);
		System.out.println(ircSentence.message);
		assertEquals("-ChanServ- [#biology] Welcome to the channel. You are free to ask in the channel or message an OP for a question. Check out biology sites, using google, or http://biology.about.com/",ircSentence.message);
		System.out.println(ircSentence);
	}
	
	@Test
	public void testParsingRelevent() {
		IRCSentence ircSentence = new IRCSentence("[18:45] <KimiSleep> the same?");
		assertEquals(18,ircSentence.houre);
		assertEquals(45,ircSentence.minutes);
		assertTrue(ircSentence.isRelevent);
		assertEquals("KimiSleep",ircSentence.messageWriter);
		assertEquals("the same?",ircSentence.message);
		System.out.println(ircSentence);
	}

}
