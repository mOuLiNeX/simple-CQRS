package cqrs.api.event.storage;

import java.util.Collection;

import cqrs.api.event.Event;


public interface IAggregateRootStorage<ID> {
	void append(ID id, Iterable<Event> events);

	Collection<Event> get(ID id);
}
