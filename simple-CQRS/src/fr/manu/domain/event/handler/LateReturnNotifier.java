package fr.manu.domain.event.handler;

import com.google.common.eventbus.Subscribe;

import fr.manu.domain.event.BookReturned;
import fr.manu.framework.event.EventHandler;

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
