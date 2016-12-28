package cqrs.query;

import java.util.Collection;

import cqrs.domain.BookId;

public interface IBookStateQuery {
	Collection<BookState> getBookStates();

	BookState getBookState(BookId id);

	boolean containsBookState(BookId id);

	Collection<BookState> getLentBooks();

	void addBookState(BookId id, String title, boolean lent);

	void setLent(BookId id, Boolean lent);
}
