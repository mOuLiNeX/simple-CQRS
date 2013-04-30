package fr.manu.framework.event.storage;


public interface IEventStorage<ID> {
	IAggregateRootStorage getAggregateRootStore();

	void dispose();
}
