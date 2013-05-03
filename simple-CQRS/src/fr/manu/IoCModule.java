package fr.manu;

import com.google.inject.AbstractModule;

import fr.manu.domain.query.BookStateQuery;
import fr.manu.domain.query.IBookStateQuery;
import fr.manu.framework.event.storage.IEventStorage;
import fr.manu.framework.impl.event.storage.EventStorage;
import fr.manu.framework.impl.repository.SessionFactory;
import fr.manu.framework.repository.ISessionFactory;

public class IoCModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(IEventStorage.class).to(EventStorage.class);
		bind(ISessionFactory.class).to(SessionFactory.class);
		bind(IBookStateQuery.class).to(BookStateQuery.class);
	}
}