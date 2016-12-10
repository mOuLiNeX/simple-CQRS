package cqrs.api.event.storage;

import cqrs.api.domain.IAggregateRoot;

public interface IEventStorage {
	IAggregateRootStorage getAggregateRootStore(
			Class<IAggregateRoot> aggregateType);

	void close();
}
