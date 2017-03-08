package cqrs.domain.event;

import java.time.LocalDate;
import java.time.Period;

import cqrs.api.event.Event;
import cqrs.domain.BookId;
import lombok.Data;

public @Data class BookLent implements Event<BookId> {
	private final BookId id;
	private final String borrower;
	private final LocalDate date;
	private final Period expectedDuration;
}
