package cqrs.domain.event;

import java.time.LocalDate;
import java.time.Period;

import cqrs.api.event.Event;
import cqrs.domain.Book;
import cqrs.domain.BookId;
import lombok.Data;

public @Data class BookLent implements Event<Book> {
	private final BookId id;
	private final String borrower;
	private final LocalDate date;
	private final Period expectedDuration;

	@Override
	public void visit(Book book) {
		book.borrower = this.borrower;
		book.date = this.date;
		book.expectedDuration = this.expectedDuration;
	}

}
