package cqrs.framework.event;

import cqrs.framework.domain.IAggregateRoot;

public interface Event<TAggregateRoot extends IAggregateRoot<?>> {
	void visit(TAggregateRoot domainRoot);
}
