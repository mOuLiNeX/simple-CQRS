package cqrs.query;

import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import cqrs.domain.BookId;

// no domain logic occurs
public class BookStateQuery implements IBookStateQuery {

	private final Map<BookId, BookState> states = new HashMap<>();

	@Override
	public Collection<BookState> getBookStates() {
		return states.values();
	}

	@Override
	public BookState getBookState(BookId id) {
		return states.get(id);
	}

	@Override
	public boolean containsBookState(BookId id) {
		return states.containsKey(id);
	}

	@Override
	public Collection<BookState> getLentBooks() {
		return states.values().stream().filter(state -> state.isLent()).collect(toList());
	}

	@Override
	public void addBookState(BookId id, String title, boolean lent) {
		states.put(id, new BookState(id, title, lent));
	}

	@Override
	public void setLent(BookId id, Boolean lent) {
		getBookState(id).setLent(lent);
	}

	@Override
	public void reset() {
		states.clear();
	}

}
