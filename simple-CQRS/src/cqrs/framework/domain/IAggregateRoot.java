package cqrs.framework.domain;

import cqrs.framework.event.IUncommittedEvents;

public interface IAggregateRoot<ID> {
	ID getId();

	IUncommittedEvents getUncommittedEvents();
}
