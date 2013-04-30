package fr.manu.framework.event.storage;

import java.util.Collection;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import fr.manu.framework.event.Event;

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
