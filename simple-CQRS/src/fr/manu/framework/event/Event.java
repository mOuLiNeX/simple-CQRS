package fr.manu.framework.event;

import fr.manu.framework.domain.IAggregateRoot;

public interface Event<TAggregateRoot extends IAggregateRoot<?>> {
	void visit(TAggregateRoot domainRoot);
}
