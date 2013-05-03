package fr.manu.framework.impl.repository;

import com.google.inject.Inject;

import fr.manu.framework.event.storage.IEventStorage;
import fr.manu.framework.repository.ISession;
import fr.manu.framework.repository.ISessionFactory;

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
