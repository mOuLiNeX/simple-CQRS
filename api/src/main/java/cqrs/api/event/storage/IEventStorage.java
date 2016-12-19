package cqrs.api.event.storage;

import cqrs.api.domain.IAggregateRoot;

public interface IEventStorage {
	IAggregateRootStorage getAggregateRootStore(
			Class<? extends IAggregateRoot> aggregateType);

	void close();
}
