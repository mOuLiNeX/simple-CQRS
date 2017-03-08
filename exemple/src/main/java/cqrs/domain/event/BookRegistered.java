package cqrs.domain.event;

import cqrs.api.event.Event;
import cqrs.domain.BookId;
import lombok.Data;

public @Data class BookRegistered implements Event<BookId> {
	private final BookId id;
	private final String title;
	private final String isbn;

}
