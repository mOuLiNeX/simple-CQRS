package cqrs.framework.impl.domain;

import java.util.Collection;

import cqrs.framework.domain.IAggregateRoot;
import cqrs.framework.event.Event;
import cqrs.framework.event.IUncommittedEvents;
import cqrs.framework.impl.event.UncommittedEvents;


public class AggregateRoot<ID> implements IAggregateRoot<ID> {
	protected ID id;

	private final IUncommittedEvents uncommittedEvents = new UncommittedEvents();

	public AggregateRoot(ID id) {
		this.id = id;
	}

	@Override
	public ID getId() {
		return id;
	}

	public void replay(Collection<Event> events) {
		for (Event event : events) {
			this.accept(event);
		}
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

	public void addEvent(Event event) {
		accept(event);
		append(event);
	}

}
