package fr.manu.domain.repository;

import java.util.Collection;

import fr.manu.domain.Book;
import fr.manu.domain.BookId;
import fr.manu.framework.event.Event;
import fr.manu.framework.repository.Repository;

public class BookRepository extends Repository<BookId, Book> {

	@Override
	protected Book createInstance(BookId id, Collection<Event> events) {
		Book newBook = new Book(id);

		newBook.replay(events);

		return newBook;
	}

}
