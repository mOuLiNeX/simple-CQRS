package cqrs.query;

import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import cqrs.api.event.Event;
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
	public Iterable<BookState> getLentBooks() {
		return states.values().stream().filter(BookState::isLent).collect(toList());
	}

	@Override
	public void reset() {
		states.clear();
	}

	@Override
	public void updateState(Event<BookId> event) {
		BookState state = states.getOrDefault(event.getId(), new BookState());
		state.apply(event);
		states.put(state.getId(), state);
	}

}
