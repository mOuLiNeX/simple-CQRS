package cqrs.api.event.repository;

import cqrs.api.domain.IAggregateRoot;


public interface IRepository<ID, TAggregateRoot extends IAggregateRoot<ID>> {
	TAggregateRoot findById(ID id);

	void add(TAggregateRoot user);
}
