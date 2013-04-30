package fr.manu.domain.repository;

import java.util.Collection;

import fr.manu.domain.Book;
import fr.manu.domain.BookId;
import fr.manu.framework.event.Event;
import fr.manu.framework.repository.Repository;

public class BookRepository extends Repository<BookId, Book> implements
		IBookRepository {

	@Override
	protected Book createInstance(BookId id, Collection<Event> events) {
		return new Book(id, events);
	}

}
