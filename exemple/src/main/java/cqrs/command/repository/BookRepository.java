package cqrs.command.repository;

import static java.util.stream.Collectors.toSet;

import java.util.Collection;
import java.util.stream.Stream;

import cqrs.api.event.Event;
import cqrs.domain.Book;
import cqrs.domain.BookId;
import cqrs.impl.repository.Repository;

public class BookRepository extends Repository<BookId, Book> {

	@Override
	protected Book createInstance(BookId id, Collection<Event> events) {
		Book newBook = new Book(id);

		newBook.replay(events);

		return newBook;
	}

	Book createInstance(BookId id, Event... events) {
		return createInstance(id, Stream.of(events).collect(toSet()));
	}

}
