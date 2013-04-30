package fr.manu.domain.event;

import fr.manu.domain.Book;
import fr.manu.domain.BookId;
import fr.manu.framework.event.Event;

public class BookRegistered implements Event<Book> {
	public final BookId id;

	public final String title;

	public final String isbn;

	public BookRegistered(BookId id, String title, String isbn) {
		super();
		this.id = id;
		this.title = title;
		this.isbn = isbn;
	}

	@Override
	public void visit(Book book) {
		book.title = this.title;
		book.isbn = this.isbn;
	}
}
