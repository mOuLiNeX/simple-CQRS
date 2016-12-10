package cqrs.api.domain;

import cqrs.api.event.IUncommittedEvents;

public interface IAggregateRoot<ID> {
	ID getId();

	IUncommittedEvents getUncommittedEvents();
}
