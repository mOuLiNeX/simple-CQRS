package fr.manu;

import org.joda.time.LocalDate;
import org.joda.time.Period;

import fr.manu.domain.Book;
import fr.manu.domain.BookId;
import fr.manu.domain.event.handler.BookStateHandler;
import fr.manu.domain.event.handler.LateReturnNotifier;
import fr.manu.domain.query.BookState;
import fr.manu.domain.query.BookStateQuery;
import fr.manu.domain.query.IBookStateQuery;
import fr.manu.domain.repository.BookRepository;
import fr.manu.exception.ArgumentException;
import fr.manu.framework.event.storage.EventStorage;
import fr.manu.framework.repository.ISession;
import fr.manu.framework.repository.ISessionFactory;
import fr.manu.framework.repository.Repository;
import fr.manu.framework.repository.SessionFactory;

public class Program {

	public static void main(String[] args) throws ArgumentException {
		ISessionFactory factory = new SessionFactory(new EventStorage());
		IBookStateQuery query = new BookStateQuery();

		// Enregistrement des event handlers
		new BookStateHandler(query);
		new LateReturnNotifier();

		// Traitements (procédural)
		BookId bookId = BookId.newBookId();

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

		factory.close();

	}

	private static void giveBack(ISessionFactory factory, BookId bookId,
			LocalDate returnDate) throws ArgumentException {
		ISession session = factory.openSession();

		Repository<BookId, Book> books = new BookRepository();
		Book book = books.findById(bookId);

		book.giveBack(returnDate);

		session.submitChanges();
	}

	private static void lend(ISessionFactory factory, BookId bookId,
			String name, LocalDate date, Period duration) {
		ISession session = factory.openSession();

		try {
			Repository<BookId, Book> books = new BookRepository();
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
			Repository<BookId, Book> books = new BookRepository();

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
