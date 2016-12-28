package cqrs.query.handler;

import javax.inject.Inject;

import com.google.common.eventbus.Subscribe;

import cqrs.api.event.EventHandler;
import cqrs.domain.event.BookLent;
import cqrs.domain.event.BookRegistered;
import cqrs.domain.event.BookReturned;
import cqrs.query.IBookStateQuery;


public class BookStateHandler extends EventHandler {

	private final IBookStateQuery stateQuery;

	@Inject
	public BookStateHandler(IBookStateQuery stateQuery) {
		super();
		this.stateQuery = stateQuery;
	}

	@Subscribe
	public void handle(BookRegistered event) {
		stateQuery.addBookState(event.getId(), event.getTitle(), false);
	}

	@Subscribe
	public void handle(BookLent event) {
		System.out.format("Book lent to %s.\n", event.getBorrower());
		stateQuery.setLent(event.getId(), true);

	}

	@Subscribe
	public void handle(BookReturned event) {
		System.out.format("Book returned by %s.\n", event.getBy());
		stateQuery.setLent(event.getId(), false);
	}

}
