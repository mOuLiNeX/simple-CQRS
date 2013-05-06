package cqrs.framework.event.storage;

import cqrs.framework.domain.IAggregateRoot;

public interface IEventStorage {
	IAggregateRootStorage getAggregateRootStore(
			Class<IAggregateRoot> aggregateType);

	void close();
}
