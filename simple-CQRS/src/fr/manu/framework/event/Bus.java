package fr.manu.framework.event;

import com.google.common.eventbus.EventBus;

public final class Bus {

	private static final EventBus eventBus = new EventBus();

	private Bus() {
	}

	public static void register(EventHandler handler) {
		eventBus.register(handler);
	}

	public static void post(Event event) {
		eventBus.post(event);
	}

}
