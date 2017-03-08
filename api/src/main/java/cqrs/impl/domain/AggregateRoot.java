package cqrs.impl.domain;

import cqrs.api.domain.IAggregateRoot;
import cqrs.api.event.AEventProjector;
import cqrs.api.event.Event;
import cqrs.api.event.IUncommittedEvents;
import cqrs.impl.event.UncommittedEvents;

public class AggregateRoot<ID> extends AEventProjector implements IAggregateRoot<ID> {
	protected final ID id;

	private final IUncommittedEvents<ID> uncommittedEvents = new UncommittedEvents<>();

	protected AggregateRoot(ID id) {
		this.id = id;
	}

	@Override
	public ID getId() {
		return id;
	}

	public void replay(Iterable<Event<ID>> events) {
		events.forEach(this::apply);
	}

	private void append(Event<ID> event) {
		uncommittedEvents.append(event);
	}

	@Override
	public IUncommittedEvents<ID> getUncommittedEvents() {
		return uncommittedEvents;
	}

	protected void addEvent(Event<ID> event) {
		apply(event);
		append(event);
	}

}
