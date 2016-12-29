package cqrs;

import java.time.LocalDate;
import java.time.Period;

import javax.inject.Singleton;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import cqrs.api.event.storage.IEventStorage;
import cqrs.api.repository.ISessionFactory;
import cqrs.command.CreateBook;
import cqrs.command.LendBook;
import cqrs.command.TakeBookBack;
import cqrs.command.handler.BookCommandHandler;
import cqrs.domain.BookId;
import cqrs.impl.event.storage.EventStorage;
import cqrs.impl.repository.SessionFactory;
import cqrs.query.BookState;
import cqrs.query.BookStateQuery;
import cqrs.query.IBookStateQuery;
import cqrs.query.handler.BookStateHandler;
import cqrs.query.handler.LateReturnNotifier;

public class Program {

	private static Injector injector;

	public static void main(String[] args) throws Exception {
		injector = Guice.createInjector(new AbstractModule() {
			@Override
			protected void configure() {
				bind(IEventStorage.class).to(EventStorage.class).in(Singleton.class);
				bind(ISessionFactory.class).to(SessionFactory.class).in(Singleton.class);
				bind(IBookStateQuery.class).to(BookStateQuery.class).in(Singleton.class);
				// Enregistrement des event handlers
				bind(BookStateHandler.class).asEagerSingleton();
				bind(LateReturnNotifier.class).asEagerSingleton();
			}
		});

		ISessionFactory factory = injector.getInstance(ISessionFactory.class);
		BookCommandHandler bookCommandHandler = injector.getInstance(BookCommandHandler.class);

		// Traitements (procedural)
		BookId bookId = BookId.newBookId();

		try {
			bookCommandHandler.handle(new CreateBook(bookId, "The Lord of the Rings", "0-618-15396-9"));
			showBooks();

			bookCommandHandler.handle(new LendBook(bookId, "Alice", LocalDate.of(2009, 11, 2), Period.ofDays(14)));
			showBooks();

			bookCommandHandler.handle(new TakeBookBack(bookId, LocalDate.of(2009, 11, 8)));
			showBooks();

			bookCommandHandler.handle(new LendBook(bookId, "Bob", LocalDate.of(2009, 11, 9), Period.ofDays(14)));
			showBooks();

			bookCommandHandler.handle(new TakeBookBack(bookId, LocalDate.of(2010, 03, 1)));
			showBooks();
		} finally {
			factory.close();
		}

	}

	private static void showBooks() {
		IBookStateQuery query = injector.getInstance(IBookStateQuery.class);
		for (BookState state : query.getBookStates()) {
			System.out.format("%s is %s.\n", state.getTitle(), state.isLent() ? "lent" : "home");
		}
	}
}
