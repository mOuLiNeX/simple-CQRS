package cqrs;

import org.joda.time.LocalDate;
import org.joda.time.Period;

import com.google.inject.Guice;
import com.google.inject.Injector;

import cqrs.domain.Book;
import cqrs.domain.BookId;
import cqrs.domain.event.handler.BookStateHandler;
import cqrs.domain.event.handler.LateReturnNotifier;
import cqrs.domain.query.BookState;
import cqrs.domain.query.IBookStateQuery;
import cqrs.domain.repository.BookRepository;
import cqrs.exception.ArgumentException;
import cqrs.framework.event.repository.IRepository;
import cqrs.framework.repository.ISession;
import cqrs.framework.repository.ISessionFactory;


public class Program {

	public static void main(String[] args) throws ArgumentException {
		Injector injector = Guice.createInjector(new IoCModule());

		ISessionFactory factory = injector.getInstance(ISessionFactory.class);
		IBookStateQuery query = injector.getInstance(IBookStateQuery.class);

		// Enregistrement des event handlers
		new BookStateHandler(query);
		new LateReturnNotifier();

		// Traitements (procédural)
		BookId bookId = BookId.newBookId();

		try {
			create(factory, bookId);
			showBooks(query);

			lend(factory, bookId, "Alice", new LocalDate(2009, 11, 2),
					Period.days(14));
			showBooks(query);

			giveBack(factory, bookId, new LocalDate(2009, 11, 8));
			showBooks(query);

			lend(factory, bookId, "Bob", new LocalDate(2009, 11, 9),
					Period.days(14));
			showBooks(query);

			giveBack(factory, bookId, new LocalDate(2010, 03, 1));
			showBooks(query);
		} finally {
			factory.close();
		}

	}

	private static void giveBack(ISessionFactory factory, BookId bookId,
			LocalDate returnDate) throws ArgumentException {
		ISession session = factory.openSession();

		IRepository<BookId, Book> books = new BookRepository();
		Book book = books.findById(bookId);

		book.giveBack(returnDate);

		session.submitChanges();
	}

	private static void lend(ISessionFactory factory, BookId bookId,
			String name, LocalDate date, Period duration) {
		ISession session = factory.openSession();

		try {
			IRepository<BookId, Book> books = new BookRepository();
			Book book = books.findById(bookId);

			book.lend(name, date, duration);

			session.submitChanges();
		} finally {
			session.close();
		}
	}

	private static void create(ISessionFactory factory, BookId bookId) {
		ISession session = factory.openSession();
		try {
			IRepository<BookId, Book> books = new BookRepository();

			books.add(new Book(bookId, "The Lord of the Rings", "0-618-15396-9"));
			session.submitChanges();
		} finally {
			session.close();
		}
	}

	private static void showBooks(IBookStateQuery query) {
		for (BookState state : query.getBookStates()) {
			System.out.format("%s is %s.\n", state.title, state.lent ? "lent"
					: "home");
		}
	}
}
