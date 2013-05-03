package fr.manu.domain.event;

import org.joda.time.Days;

import fr.manu.domain.Book;
import fr.manu.domain.BookId;
import fr.manu.framework.event.Event;

public class BookReturned implements Event<Book> {

	public final BookId id;

	public final String by;

	public final Days after;

	public final Boolean late;

	public BookReturned(BookId id, String by, Days after, Boolean late) {
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
