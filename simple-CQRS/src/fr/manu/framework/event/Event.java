package fr.manu.framework.event;

import fr.manu.framework.domain.AggregateRoot;

public interface Event<T extends AggregateRoot> {
	void visit(T domainRoot);
}
