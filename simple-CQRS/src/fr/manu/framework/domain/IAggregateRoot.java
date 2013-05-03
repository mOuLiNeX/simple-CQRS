package fr.manu.framework.domain;

import fr.manu.framework.event.IUncommittedEvents;

public interface IAggregateRoot<ID> {
	ID getId();

	IUncommittedEvents getUncommittedEvents();
}
