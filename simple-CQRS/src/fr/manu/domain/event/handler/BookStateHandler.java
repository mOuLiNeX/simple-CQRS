package fr.manu.domain.event.handler;

import com.google.common.eventbus.Subscribe;

import fr.manu.domain.event.BookLent;
import fr.manu.domain.event.BookRegistered;
import fr.manu.domain.event.BookReturned;
import fr.manu.domain.query.IBookStateQuery;
import fr.manu.framework.event.Bus;
import fr.manu.framework.event.EventHandler;

public class BookStateHandler implements EventHandler {

	private final IBookStateQuery stateQuery;

	public BookStateHandler(IBookStateQuery stateQuery) {
		Bus.register(this);
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
