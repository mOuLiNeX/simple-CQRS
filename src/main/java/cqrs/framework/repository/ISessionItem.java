package cqrs.framework.repository;

import cqrs.framework.domain.IAggregateRoot;

public interface ISessionItem<ID, TAggregateRoot extends IAggregateRoot<ID>> {
	void submitChanges();
}
