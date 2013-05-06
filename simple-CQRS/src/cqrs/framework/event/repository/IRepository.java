package cqrs.framework.event.repository;

import cqrs.framework.domain.IAggregateRoot;


public interface IRepository<ID, TAggregateRoot extends IAggregateRoot<ID>> {
	public TAggregateRoot findById(ID id);

	void add(TAggregateRoot user);
}
