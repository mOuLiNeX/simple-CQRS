package cqrs.impl.repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import cqrs.api.domain.IAggregateRoot;
import cqrs.api.event.Bus;
import cqrs.api.event.Event;
import cqrs.api.event.IUncommittedEvents;
import cqrs.api.event.repository.IRepository;
import cqrs.api.event.storage.IAggregateRootStorage;
import cqrs.api.exception.InvalidOperationException;
import cqrs.api.repository.ISessionItem;

public abstract class Repository<ID, TAggregateRoot extends IAggregateRoot<ID>>
		implements ISessionItem, IRepository<ID, TAggregateRoot> {

	private final Map<ID, TAggregateRoot> items = new HashMap<>();

	private final IAggregateRootStorage<ID> aggregateRootStorage;

	protected Repository() {
		aggregateRootStorage = Session.enlist(this);
	}

	@Override
	public void add(TAggregateRoot root) {
		items.put(root.getId(), root);
	}

	@Override
	public TAggregateRoot findById(ID id) {
		if (!items.containsKey(id)) {
			return load(id);
		} else {
			return items.get(id);
		}
	}

	private TAggregateRoot load(ID id) {
		if (!aggregateRootStorage.contains(id)) {
			throw new InvalidOperationException("No element in repository with id " + id);
		}
		TAggregateRoot root = createInstance(id, aggregateRootStorage.get(id));
		items.put(id, root);
		return root;
	}

	protected abstract TAggregateRoot createInstance(ID id, Collection<Event> events);

	@Override
	public void submitChanges() {
		for (IAggregateRoot<ID> user : items.values()) {
			IUncommittedEvents uncomitedEvents = user.getUncommittedEvents();
			if (uncomitedEvents.hasEvents()) {
				aggregateRootStorage.append(user.getId(), uncomitedEvents);
				publishEvents(uncomitedEvents);
				uncomitedEvents.commit();
			}
			items.clear();
		}
	}

	private void publishEvents(IUncommittedEvents uncommittedEvents) {
		for (Event event : uncommittedEvents) {
			Bus.post(event);
		}
	}
}
