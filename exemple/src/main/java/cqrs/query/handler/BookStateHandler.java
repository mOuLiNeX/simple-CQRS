package cqrs.query.handler;

import javax.inject.Inject;

import com.google.common.eventbus.Subscribe;

import cqrs.api.event.Event;
import cqrs.api.event.EventHandler;
import cqrs.domain.BookId;
import cqrs.query.IBookStateQuery;

public class BookStateHandler extends EventHandler {

	private final IBookStateQuery stateQuery;

	@Inject
	public BookStateHandler(IBookStateQuery stateQuery) {
		super();
		this.stateQuery = stateQuery;
	}

	@Subscribe
	public void handle(Event<BookId> event) {
		stateQuery.updateState(event);
	}
}
