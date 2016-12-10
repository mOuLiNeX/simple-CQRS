package cqrs.domain.event.handler;

import com.google.common.eventbus.Subscribe;

import cqrs.domain.event.BookReturned;
import cqrs.framework.event.EventHandler;


public class LateReturnNotifier extends EventHandler {

	public LateReturnNotifier() {
		super();
	}

	@Subscribe
	public void handle(BookReturned event) {
		if (event.late) {
			System.out.format("%s was late.\n", event.by);
		}
	}

}
