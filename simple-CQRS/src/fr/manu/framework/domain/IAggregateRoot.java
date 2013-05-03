package fr.manu.framework.domain;

import java.util.Collection;

import fr.manu.framework.event.Event;
import fr.manu.framework.event.IUncommittedEvents;

public interface IAggregateRoot<ID> {
	ID getId();

	IUncommittedEvents getUncommittedEvents();

	void replay(Collection<Event> events);
}
