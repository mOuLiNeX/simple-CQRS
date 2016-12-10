package cqrs.domain.event;

import java.time.Period;

import cqrs.api.event.Event;
import cqrs.domain.Book;
import cqrs.domain.BookId;


public class BookReturned implements Event<Book> {

	public final BookId id;

	public final String by;

	public final Period after;

	public final Boolean late;

	public BookReturned(BookId id, String by, Period after, Boolean late) {
		super();
		this.id = id;
		this.by = by;
		this.after = after;
		this.late = late;
	}

	@Override
	public void visit(Book book) {
		book.borrower = null;
	}
}
