package cqrs.query;

import java.util.Collection;

import cqrs.api.event.Event;
import cqrs.domain.BookId;

public interface IBookStateQuery {
	Collection<BookState> getBookStates();

	BookState getBookState(BookId id);

	boolean containsBookState(BookId id);

	Iterable<BookState> getLentBooks();

	// TODO pour test uniquement :-(
	void reset();

	void updateState(Event<BookId> event);
}
