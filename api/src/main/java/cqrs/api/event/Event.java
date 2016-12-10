package cqrs.api.event;

import cqrs.api.domain.IAggregateRoot;

public interface Event<TAggregateRoot extends IAggregateRoot<?>> {
	void visit(TAggregateRoot domainRoot);
}
