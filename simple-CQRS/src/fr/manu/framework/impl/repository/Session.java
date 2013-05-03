package fr.manu.framework.impl.repository;

import java.lang.reflect.ParameterizedType;
import java.util.Set;

import com.google.common.collect.Sets;

import fr.manu.framework.domain.IAggregateRoot;
import fr.manu.framework.event.storage.IAggregateRootStorage;
import fr.manu.framework.event.storage.IEventStorage;
import fr.manu.framework.exception.InvalidOperationException;
import fr.manu.framework.repository.ISession;
import fr.manu.framework.repository.ISessionItem;

public class Session implements ISession {
	private final IEventStorage eventStorage;

	private final Set<ISessionItem> enlistedItems = Sets.newHashSet();

	private static final ThreadLocal<Session> current = new ThreadLocal<Session>();

	Session(IEventStorage eventStorage) {
		this.eventStorage = eventStorage;
		if (exists())
			throw new InvalidOperationException("Cannot nest unit of work");
		current.set(this);
	}

	static Session get() {
		return current.get();
	}

	public static boolean exists() {
		return current.get() != null;
	}

	public void submitChanges() {
		for (ISessionItem enlisted : enlistedItems) {
			enlisted.submitChanges();
		}
		enlistedItems.clear();
	}

	public void close() {
		current.remove();
	}

	static IAggregateRootStorage enlist(Repository repository) {

		Session unitOfWork = get();

		unitOfWork.enlistedItems.add(repository);

		// Récup type de l'AggregateRoot via generics sur Repository
		Class<IAggregateRoot> aggregateRootType = (Class<IAggregateRoot>) ((ParameterizedType) repository
				.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
		return unitOfWork.eventStorage.getAggregateRootStore(aggregateRootType);

	}
}
