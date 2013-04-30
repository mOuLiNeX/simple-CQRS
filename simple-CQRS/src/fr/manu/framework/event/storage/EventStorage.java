package fr.manu.framework.event.storage;

import java.util.Map;

import com.google.common.collect.Maps;

// There’s a specific storage for each Aggregate Root type, especially depending on identifier type, for type safety.
public class EventStorage implements IEventStorage {
	private final Map<Class, IAggregateRootStorage> stores = Maps.newHashMap();

	public IAggregateRootStorage getAggregateRootStore() {

		// Object store;
		//
		// if (!stores.contains(typeof(TAggregateRoot), out store))
		//
		// {
		//
		// store = new AggregateRootStorage();
		//
		// stores.put(typeof (TAggregateRoot), store);
		//
		// }
		//
		// return store;
		return new AggregateRootStorage();

	}

	public void dispose() {
		stores.clear();
	}
}
