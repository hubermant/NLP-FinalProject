package autocomplete.core.completer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import utill.collection.WordRank;
import autocomplete.core.event.Event;
import autocomplete.core.event.NewWordEvent;

public class FilterCompleter implements Completer {
	
	private Completer inner;
	private Set<String> previousSuggestions;
	
	public FilterCompleter(Completer inner) {
		this.inner = inner;
		previousSuggestions = new HashSet<String>();
	}

	@Override
	public void train(List<String> text) {
		inner.train(text);
	}

	@Override
	public List<WordRank> complete(List<String> lastWords, String prefix, int resNum) {
		List<WordRank> res = inner.complete(lastWords, prefix, 10000);
		List<WordRank> newRes = new ArrayList<WordRank>();
		
		
		for (WordRank rank : res) {
			if (!previousSuggestions.contains(rank.getWord())) {
				previousSuggestions.add(rank.getWord());
				newRes.add(rank);
				
				if (newRes.size() == resNum) {
					break;
				}
			}
		}
		
		return newRes;
	}

	@Override
	public void handleEvent(Event e) {
		if (e instanceof NewWordEvent) {
			previousSuggestions.clear();
		}
		
		inner.handleEvent(e);
	}
}
