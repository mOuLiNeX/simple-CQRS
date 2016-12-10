package cqrs.domain.event.handler;

import com.google.common.eventbus.Subscribe;

import cqrs.api.event.EventHandler;
import cqrs.domain.event.BookLent;
import cqrs.domain.event.BookRegistered;
import cqrs.domain.event.BookReturned;
import cqrs.domain.query.IBookStateQuery;


public class BookStateHandler extends EventHandler {

	private final IBookStateQuery stateQuery;

	public BookStateHandler(IBookStateQuery stateQuery) {
		super();
		this.stateQuery = stateQuery;
	}

	@Subscribe
	public void handle(BookRegistered event) {
		stateQuery.addBookState(event.id, event.title, false);
	}

	@Subscribe
	public void handle(BookLent event) {
		System.out.format("Book lent to %s.\n", event.borrower);
		stateQuery.setLent(event.id, true);

	}

	@Subscribe
	public void handle(BookReturned event) {
		System.out.format("Book returned by %s.\n", event.by);
		stateQuery.setLent(event.id, false);
	}

}
