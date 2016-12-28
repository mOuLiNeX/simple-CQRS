package cqrs.impl.event.storage;

import static java.util.stream.Collectors.toSet;

import java.util.Collection;
import java.util.stream.Stream;

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
	public void append(ID id, Event... events) {
		store.putAll(id, Stream.of(events).collect(toSet()));
	}

	@Override
	public Collection<Event> get(ID id) {
		return store.get(id);
	}

	@Override
	public boolean contains(ID id) {
		return store.containsKey(id);
	}

	@Override
	public boolean isEmpty() {
		return store.isEmpty();
	}

}
