package cqrs.impl.event;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import cqrs.api.event.Event;
import cqrs.api.event.IUncommittedEvents;

public class UncommittedEvents implements IUncommittedEvents {

    private final List<Event> events = new LinkedList<>();

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
