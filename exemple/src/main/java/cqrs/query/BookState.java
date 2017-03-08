package cqrs.query;

import cqrs.api.event.AEventProjector;
import cqrs.domain.BookId;
import cqrs.domain.event.BookLent;
import cqrs.domain.event.BookRegistered;
import cqrs.domain.event.BookReturned;
import lombok.Data;

public @Data final class BookState extends AEventProjector {
	private BookId id;
	private String title;
	private boolean lent;

	public BookState() {
		super.register(BookLent.class, this::apply);
		super.register(BookRegistered.class, this::apply);
		super.register(BookReturned.class, this::apply);
	}

	public void apply(BookRegistered event) {
		this.id = event.getId();
		this.title = event.getTitle();
		this.lent = false;
	}

	public void apply(BookLent event) {
		this.lent = true;

	}

	public void apply(BookReturned event) {
		this.lent = false;
	}
}
