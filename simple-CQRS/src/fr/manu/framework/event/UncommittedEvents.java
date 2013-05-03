package fr.manu.framework.event;

import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;

public class UncommittedEvents implements IUncommittedEvents {

	private final List<Event> events = Lists.newLinkedList();

	public void append(Event event) {
		events.add(event);
	}

	@Override
	public Boolean hasEvents() {
		return !events.isEmpty();
	}

	@Override
	public void commit() {
		events.clear();
	}

	@Override
	public Iterator<Event> iterator() {
		return events.iterator();
	}
}
