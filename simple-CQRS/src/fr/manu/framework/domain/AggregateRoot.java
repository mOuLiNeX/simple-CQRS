package fr.manu.framework.domain;

import java.util.Collection;

import fr.manu.framework.event.Event;
import fr.manu.framework.event.IUncommittedEvents;
import fr.manu.framework.event.UncommittedEvents;

public abstract class AggregateRoot<ID> implements IAggregateRoot<ID> {

	private final UncommittedEvents uncommittedEvents = new UncommittedEvents();

	protected void replay(Collection<Event> events) {
		for (Event event : events) {
			this.acceptEvent(event);
		}
	}

	protected void append(Event event) {
		uncommittedEvents.Append(event);
	}

	@Override
	public IUncommittedEvents getUncommittedEvents() {
		return uncommittedEvents;
	}

	public void acceptEvent(Event visitor) {
		visitor.visit(this);
	}

}
