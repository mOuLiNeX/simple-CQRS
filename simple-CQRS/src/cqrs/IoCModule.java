package cqrs;

import com.google.inject.AbstractModule;

import cqrs.domain.query.BookStateQuery;
import cqrs.domain.query.IBookStateQuery;
import cqrs.framework.event.storage.IEventStorage;
import cqrs.framework.impl.event.storage.EventStorage;
import cqrs.framework.impl.repository.SessionFactory;
import cqrs.framework.repository.ISessionFactory;


public class IoCModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(IEventStorage.class).to(EventStorage.class);
		bind(ISessionFactory.class).to(SessionFactory.class);
		bind(IBookStateQuery.class).to(BookStateQuery.class);
	}
}