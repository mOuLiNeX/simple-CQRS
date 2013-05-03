package fr.manu.framework.impl.repository;

import java.util.Collection;
import java.util.Map;

import com.google.common.collect.Maps;

import fr.manu.framework.domain.IAggregateRoot;
import fr.manu.framework.event.Bus;
import fr.manu.framework.event.Event;
import fr.manu.framework.event.IUncommittedEvents;
import fr.manu.framework.event.repository.IRepository;
import fr.manu.framework.event.storage.IAggregateRootStorage;
import fr.manu.framework.repository.ISessionItem;

public abstract class Repository<ID, TAggregateRoot extends IAggregateRoot<ID>>
		implements ISessionItem, IRepository<ID, TAggregateRoot> {

	private final Map<ID, TAggregateRoot> items = Maps.newHashMap();

	private final IAggregateRootStorage<ID> aggregateRootStorage;

	public Repository() {
		aggregateRootStorage = Session.enlist(this);
	}

	@Override
	public void add(TAggregateRoot user) {
		items.put(user.getId(), user);
	}

	@Override
	public TAggregateRoot findById(ID id) {
		if (!items.containsKey(id)) {
			return (TAggregateRoot) load(id);
		} else {
			return (TAggregateRoot) items.get(id);
		}
	}

	private TAggregateRoot load(ID id) {
		TAggregateRoot user = createInstance(id, aggregateRootStorage.get(id));
		items.put(id, user);
		return user;
	}

	protected abstract TAggregateRoot createInstance(ID id,
			Collection<Event> events);

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

	protected void publishEvents(IUncommittedEvents uncommittedEvents) {
		for (Event event : uncommittedEvents) {
			Bus.post(event);
		}
	}
}
