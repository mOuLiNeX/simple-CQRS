package fr.manu.framework.event.storage;

import fr.manu.framework.domain.IAggregateRoot;

public interface IEventStorage {
	IAggregateRootStorage getAggregateRootStore(
			Class<IAggregateRoot> aggregateType);

	void close();
}
