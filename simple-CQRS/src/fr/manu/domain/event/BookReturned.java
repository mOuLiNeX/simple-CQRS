package fr.manu.domain.event;

import org.joda.time.Period;

import fr.manu.domain.Book;
import fr.manu.domain.BookId;
import fr.manu.framework.event.Event;

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
