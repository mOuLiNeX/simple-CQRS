package fr.manu.domain.query;

import java.util.Collection;
import java.util.Map;

import com.google.common.base.Predicate;
import com.google.common.collect.Maps;

import fr.manu.domain.BookId;

// no domain logic occurs
public class BookStateQuery implements IBookStateQuery {

	private final Map<BookId, BookState> states = Maps.newHashMap();

	@Override
	public Collection<BookState> getBookStates() {
		return states.values();
	}

	@Override
	public BookState getBookState(BookId id) {
		return states.get(id);
	}

	@Override
	public Collection<BookState> getLentBooks() {
		return Maps.filterValues(states, new Predicate<BookState>() {
			public boolean apply(BookState input) {
				return input.lent;
			}
		}).values();
	}

	@Override
	public void addBookState(BookId id, String title, Boolean lent) {
		states.put(id, new BookState(id, title, lent));
	}

	@Override
	public void setLent(BookId id, Boolean lent) {
		getBookState(id).lent = lent;
	}

}
