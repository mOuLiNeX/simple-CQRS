package cqrs.impl.event.storage;

import java.util.HashMap;
import java.util.Map;

import cqrs.api.event.storage.IAggregateRootStorage;
import cqrs.api.event.storage.IEventStorage;


// There is a specific storage for each Aggregate Root type, especially depending on identifier type, for type safety.
public class EventStorage implements IEventStorage {
    private final Map<Class, IAggregateRootStorage> stores = new HashMap<>();

    public void close() {
        stores.clear();
    }

    @Override
    public IAggregateRootStorage getAggregateRootStore(Class aggregateType) {
        IAggregateRootStorage store;

        if (!stores.containsKey(aggregateType)) {
            store = new AggregateRootStorage();
            stores.put(aggregateType, store);
        } else {
            store = stores.get(aggregateType);
        }

        return store;
    }
}
