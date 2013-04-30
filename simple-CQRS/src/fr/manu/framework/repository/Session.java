package fr.manu.framework.repository;

import java.util.Set;

import com.google.common.collect.Sets;

import fr.manu.framework.event.storage.IAggregateRootStorage;
import fr.manu.framework.event.storage.IEventStorage;
import fr.manu.framework.exception.InvalidOperationException;

public class Session implements ISession {
	private final IEventStorage eventStorage;

	private final Set<ISessionItem> enlistedItems = Sets.newHashSet();

	private static final ThreadLocal<Session> current = new ThreadLocal<Session>();

	Session(IEventStorage eventStorage) {
		this.eventStorage = eventStorage;
		if (current != null)
			throw new InvalidOperationException("Cannot nest unit of work");
		current.set(this);
	}

	private static Session get() {
		return current.get();
	}

	public void submitChanges() {
		for (ISessionItem enlisted : enlistedItems) {
			enlisted.submitChanges();
		}
		enlistedItems.clear();
	}

	public void dispose() {
		current.remove();
	}

	static IAggregateRootStorage enlist(Repository repository) {

		Session unitOfWork = get();

		unitOfWork.enlistedItems.add(repository);

		return unitOfWork.eventStorage.getAggregateRootStore();

	}
}
