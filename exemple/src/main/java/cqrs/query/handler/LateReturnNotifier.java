package cqrs.query.handler;

import com.google.common.eventbus.Subscribe;

import cqrs.api.event.EventHandler;
import cqrs.domain.event.BookReturned;


public class LateReturnNotifier extends EventHandler {

	public LateReturnNotifier() {
		super();
	}

	@Subscribe
	public void handle(BookReturned event) {
		if (event.isLate()) {
			System.out.format("%s was late.%n", event.getBy());
		}
	}

}
