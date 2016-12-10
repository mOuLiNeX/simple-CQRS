package cqrs.framework.impl.repository;

import com.google.inject.Inject;

import cqrs.framework.event.storage.IEventStorage;
import cqrs.framework.repository.ISession;
import cqrs.framework.repository.ISessionFactory;


public class SessionFactory implements ISessionFactory {
	private final IEventStorage eventStorage;

	@Inject
	SessionFactory(IEventStorage eventStorage) {
		this.eventStorage = eventStorage;
	}

	@Override
	public ISession openSession() {
		if (Session.exists()) {
			return Session.get();
		} else {
			return new Session(eventStorage);
		}
	}

	public void close() {
		eventStorage.close();
	}

}
