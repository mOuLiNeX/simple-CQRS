package fr.manu.domain.query;

import java.util.Collection;

import fr.manu.domain.BookId;

public interface IBookStateQuery {
	Collection<BookState> getBookStates();

	BookState getBookState(BookId id);

	Collection<BookState> getLentBooks();

	void addBookState(BookId id, String title, Boolean lent);

	void setLent(BookId id, Boolean lent);
}
