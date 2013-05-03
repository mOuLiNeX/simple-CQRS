package fr.manu.framework.impl.domain;

import java.util.Collection;

import fr.manu.framework.domain.IAggregateRoot;
import fr.manu.framework.event.Event;
import fr.manu.framework.event.IUncommittedEvents;
import fr.manu.framework.impl.event.UncommittedEvents;

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
