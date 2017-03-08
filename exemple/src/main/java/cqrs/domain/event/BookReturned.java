package cqrs.domain.event;

import java.time.Period;

import cqrs.api.event.Event;
import cqrs.domain.BookId;
import lombok.Data;

public @Data class BookReturned implements Event<BookId> {
	private final BookId id;
	private final String by;
	private final Period after;
	private final boolean late;
}
