package cqrs.impl.event.storage;

import java.util.Collection;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import cqrs.api.event.Event;
import cqrs.api.event.storage.IAggregateRootStorage;


//  Stores list of events associated with aggregate root identifier.
public class AggregateRootStorage<ID> implements IAggregateRootStorage<ID> {
	private final Multimap<ID, Event> store = ArrayListMultimap.create();

	@Override
	public void append(ID id, Iterable<Event> events) {
		store.putAll(id, events);
	}

	@Override
	public Collection<Event> get(ID id) {
		return store.get(id);
	}

}
