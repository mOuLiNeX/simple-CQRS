package cqrs.api.event;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public abstract class AEventProjector {
	private Map<Class, Consumer<? extends Event>> appliers = new HashMap<>();

	protected <T extends Event> void register(Class<T> eventClass, Consumer<T> eventConsumer) {
		appliers.put(eventClass, eventConsumer);
	}

	@SuppressWarnings("unchecked")
	public void apply(Event event) {
		Consumer consumer = appliers.getOrDefault(event.getClass(), input -> { });
		consumer.accept(event);
	}
}
