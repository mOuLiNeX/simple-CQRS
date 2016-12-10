package cqrs.domain.repository;

import java.util.Collection;

import cqrs.domain.Book;
import cqrs.domain.BookId;
import cqrs.framework.event.Event;
import cqrs.framework.impl.repository.Repository;


public class BookRepository extends Repository<BookId, Book> {

	@Override
	protected Book createInstance(BookId id, Collection<Event> events) {
		Book newBook = new Book(id);

		newBook.replay(events);

		return newBook;
	}

}
