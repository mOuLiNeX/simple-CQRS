package cqrs.api.repository;

import cqrs.api.domain.IAggregateRoot;

public interface ISessionItem<ID, TAggregateRoot extends IAggregateRoot<ID>> {
	void submitChanges();
}
