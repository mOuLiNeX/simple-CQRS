package cqrs.api.repository;

import cqrs.api.domain.IAggregateRoot;

@FunctionalInterface
public interface ISessionItem<ID, TAggregateRoot extends IAggregateRoot<ID>> {
	void submitChanges();
}
