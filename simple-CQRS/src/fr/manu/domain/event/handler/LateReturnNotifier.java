package fr.manu.domain.event.handler;

import com.google.common.eventbus.Subscribe;

import fr.manu.domain.event.BookReturned;
import fr.manu.framework.event.Bus;
import fr.manu.framework.event.EventHandler;

public class LateReturnNotifier implements EventHandler {

	public LateReturnNotifier() {
		Bus.register(this);
	}

	@Subscribe
	public void handle(BookReturned event) {
		if (event.late) {
			System.out.format("%s was late.\n", event.by);
		}
	}

}
