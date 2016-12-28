package cqrs;

import java.time.LocalDate;
import java.time.Period;

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

    public static void main(String[] args) throws Exception {
        Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(IEventStorage.class).to(EventStorage.class);
                bind(ISessionFactory.class).to(SessionFactory.class);
                bind(IBookStateQuery.class).to(BookStateQuery.class);
            }
        });

        ISessionFactory factory = injector.getInstance(ISessionFactory.class);
        IBookStateQuery query = injector.getInstance(IBookStateQuery.class);

        // Enregistrement des event handlers
        new BookStateHandler(query);
        new LateReturnNotifier();

        // Traitements (procedural)
        BookId bookId = BookId.newBookId();

        try {
            BookCommandHandler bookCommandHandler = new BookCommandHandler(factory);
            bookCommandHandler.handle(new CreateBook(bookId, "The Lord of the Rings", "0-618-15396-9"));
            showBooks(query);

            bookCommandHandler.handle(new LendBook(bookId, "Alice", LocalDate.of(2009, 11, 2), Period.ofDays(14)));
            showBooks(query);

            bookCommandHandler.handle(new TakeBookBack(bookId, LocalDate.of(2009, 11, 8)));
            showBooks(query);

            bookCommandHandler.handle(new LendBook(bookId, "Bob", LocalDate.of(2009, 11, 9), Period.ofDays(14)));
            showBooks(query);

            bookCommandHandler.handle(new TakeBookBack(bookId, LocalDate.of(2010, 03, 1)));
            showBooks(query);
        } finally {
            factory.close();
        }

    }

    private static void showBooks(IBookStateQuery query) {
        for (BookState state : query.getBookStates()) {
			System.out.format("%s is %s.\n", state.getTitle(), state.isLent() ? "lent"
                : "home");
        }
    }
}
