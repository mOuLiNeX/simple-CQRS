package cqrs.impl.repository;

import java.lang.reflect.ParameterizedType;
import java.util.HashSet;
import java.util.Set;

import cqrs.api.domain.IAggregateRoot;
import cqrs.api.event.storage.IAggregateRootStorage;
import cqrs.api.event.storage.IEventStorage;
import cqrs.api.exception.InvalidOperationException;
import cqrs.api.repository.ISession;
import cqrs.api.repository.ISessionItem;

public class Session implements ISession {
    private final IEventStorage eventStorage;

    private final Set<ISessionItem> enlistedItems = new HashSet<>();

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
