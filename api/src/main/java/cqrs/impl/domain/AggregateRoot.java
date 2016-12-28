package cqrs.impl.domain;

import java.util.Collection;

import cqrs.api.domain.IAggregateRoot;
import cqrs.api.event.Event;
import cqrs.api.event.IUncommittedEvents;
import cqrs.impl.event.UncommittedEvents;


public class AggregateRoot<ID> implements IAggregateRoot<ID> {
	protected final ID id;

	private final IUncommittedEvents uncommittedEvents = new UncommittedEvents();

	protected AggregateRoot(ID id) {
		this.id = id;
	}

	@Override
	public ID getId() {
		return id;
	}

	public void replay(Collection<Event> events) {
		events.forEach(this::accept);
	}

	private void append(Event event) {
		uncommittedEvents.append(event);
	}

	@Override
	public IUncommittedEvents getUncommittedEvents() {
		return uncommittedEvents;
	}

	// Pattern visitor
	private void accept(Event visitor) {
		visitor.visit(this);
	}

	protected void addEvent(Event event) {
		accept(event);
		append(event);
	}

}
