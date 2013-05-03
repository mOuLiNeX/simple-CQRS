package fr.manu.framework.event.repository;

import fr.manu.framework.domain.IAggregateRoot;

import fr.manu.framework.domain.IAggregateRoot;

public interface IRepository<ID, TAggregateRoot extends IAggregateRoot<ID>> {
	public TAggregateRoot findById(ID id);

	void add(TAggregateRoot user);
}
