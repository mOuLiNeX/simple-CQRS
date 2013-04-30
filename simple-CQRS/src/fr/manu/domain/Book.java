package fr.manu.domain;

import java.util.Collection;

import org.joda.time.LocalDate;
import org.joda.time.Period;

import fr.manu.domain.event.BookLent;
import fr.manu.domain.event.BookRegistered;
import fr.manu.domain.event.BookReturned;
import fr.manu.exception.ArgumentException;
import fr.manu.framework.domain.AggregateRoot;
import fr.manu.framework.event.Event;
import fr.manu.framework.exception.InvalidOperationException;

public class Book extends AggregateRoot<BookId> {
	private final BookId id;

	public String title;

	public String isbn;

	public String borrower;

	public LocalDate date;

	public Period expectedDuration;

	public Book(BookId id, Collection<Event> events) {
		this.id = id;

		for (Event<Book> event : events) {
			acceptEvent(event);
		}
	}

	@Override
	public BookId getId() {
		// TODO Auto-generated method stub
		return id;
	}

	public Book(BookId id, String title, String isbn) {
		this.id = id;

		Event<Book> event = new BookRegistered(id, title, isbn);
		acceptEvent(event);
		append(event);
	}

	public void lend(String borrower, LocalDate date, Period expectedDuration)
			throws InvalidOperationException {
		if (this.borrower != null)
			throw new InvalidOperationException("The book is already lent.");

		Event<Book> event = new BookLent(id, borrower, date, expectedDuration);
		acceptEvent(event);
		append(event);
	}

	public void giveBack(LocalDate returnDate)
			throws InvalidOperationException, ArgumentException {
		if (borrower == null)
			throw new InvalidOperationException("The book has not been lent.");
		if (returnDate.isBefore(date))
			throw new ArgumentException(
					"The book cannot be returned before being lent.");

		Period actualDuration = Period.fieldDifference(date, returnDate);
		Event<Book> event = new BookReturned(id, borrower, actualDuration,
				(actualDuration.getDays() > expectedDuration.getDays()));

		acceptEvent(event);
		append(event);
	}
}
