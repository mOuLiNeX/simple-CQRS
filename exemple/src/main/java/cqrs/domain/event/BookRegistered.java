package cqrs.domain.event;

import cqrs.api.event.Event;
import cqrs.domain.Book;
import cqrs.domain.BookId;
import lombok.Data;

public @Data class BookRegistered implements Event<Book> {
	private final BookId id;
	private final String title;
	private final String isbn;

	@Override
	public void visit(Book book) {
		book.title = this.title;
		book.isbn = this.isbn;
	}

}
