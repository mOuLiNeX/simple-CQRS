package cqrs.api.impl.repository;

import com.google.inject.Inject;

import cqrs.api.event.storage.IEventStorage;
import cqrs.api.repository.ISession;
import cqrs.api.repository.ISessionFactory;


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
