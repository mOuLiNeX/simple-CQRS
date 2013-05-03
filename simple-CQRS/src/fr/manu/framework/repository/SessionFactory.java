package fr.manu.framework.repository;

import fr.manu.framework.event.storage.IEventStorage;

public class SessionFactory implements ISessionFactory {
	private final IEventStorage eventStorage;

	public SessionFactory(IEventStorage eventStorage) {
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
