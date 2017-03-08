package cqrs.api.event.storage;

import static java.util.stream.Collectors.toSet;

import java.util.Collection;
import java.util.stream.Stream;

import cqrs.api.event.Event;

public interface IAggregateRootStorage<ID> {
	void append(ID id, Iterable<Event<ID>> events);

	default void append(ID id, Event<ID>... events) {
		append(id, Stream.of(events).collect(toSet()));
	}

	Collection<Event<ID>> get(ID id);

	boolean contains(ID id);

	boolean isEmpty();

	void reset();
}
