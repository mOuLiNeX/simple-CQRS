package cqrs.runner;

import javax.inject.Singleton;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import cqrs.api.event.storage.IEventStorage;
import cqrs.api.repository.ISessionFactory;
import cqrs.impl.event.storage.EventStorage;
import cqrs.impl.repository.SessionFactory;
import cqrs.query.BookStateQuery;
import cqrs.query.IBookStateQuery;
import cqrs.query.handler.BookStateHandler;

public class MyGuiceRunner extends BlockJUnit4ClassRunner {

	private Injector injector;

	@Override
	protected Object createTest() throws Exception {
		return injector.getInstance(getTestClass().getJavaClass());
	}

	@Override
	public void run(RunNotifier notifier) {
		injector = createInjector();
		super.run(notifier);
	}

	private Injector createInjector() {
		return Guice.createInjector(new AbstractModule() {
			@Override
			protected void configure() {
				bind(IEventStorage.class).to(EventStorage.class).in(Singleton.class);
				bind(ISessionFactory.class).to(SessionFactory.class).in(Singleton.class);
				bind(IBookStateQuery.class).to(BookStateQuery.class).in(Singleton.class);
				bind(BookStateHandler.class).asEagerSingleton();
			}
		});
	}

	public MyGuiceRunner(Class<?> klass) throws InitializationError {
		super(klass);
	}

}
