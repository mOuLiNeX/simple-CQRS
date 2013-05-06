package cqrs.domain.event;

import org.joda.time.LocalDate;
import org.joda.time.Period;

import cqrs.domain.Book;
import cqrs.domain.BookId;
import cqrs.framework.event.Event;


public class BookLent implements Event<Book> {
	public final BookId id;

	public final String borrower;

	public final LocalDate date;

	public final Period expectedDuration;

	public BookLent(BookId id, String borrower, LocalDate date,
			Period expectedDuration) {
		super();
		this.id = id;
		this.borrower = borrower;
		this.date = date;
		this.expectedDuration = expectedDuration;
	}

	@Override
	public void visit(Book book) {
		book.borrower = this.borrower;
		book.date = this.date;
		book.expectedDuration = this.expectedDuration;
	}

}
