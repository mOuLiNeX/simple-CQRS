package fr.manu.framework.repository;

import fr.manu.framework.domain.IAggregateRoot;

public interface ISessionItem<ID, TAggregateRoot extends IAggregateRoot<ID>> {
	void submitChanges();
}
