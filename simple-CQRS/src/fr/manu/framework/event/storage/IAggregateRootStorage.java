package fr.manu.framework.event.storage;

import java.util.Collection;

import fr.manu.framework.event.Event;

public interface IAggregateRootStorage<ID> {
	void append(ID id, Iterable<Event> events);

	Collection<Event> get(ID id);
}
