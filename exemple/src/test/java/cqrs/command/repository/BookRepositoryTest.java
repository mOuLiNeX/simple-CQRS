package cqrs.command.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.Period;

import javax.inject.Singleton;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import cqrs.api.event.storage.IEventStorage;
import cqrs.api.repository.ISession;
import cqrs.api.repository.ISessionFactory;
import cqrs.domain.Book;
import cqrs.domain.BookId;
import cqrs.domain.event.BookLent;
import cqrs.domain.event.BookRegistered;
import cqrs.impl.event.storage.EventStorage;
import cqrs.impl.repository.SessionFactory;

public class BookRepositoryTest {

	private BookRepository bookRepository;
	private ISession session;
	private ISessionFactory factory;

	@Before
	public void setUp() {
		Injector injector = Guice.createInjector(new AbstractModule() {
			@Override
			protected void configure() {
				bind(IEventStorage.class).to(EventStorage.class).in(Singleton.class);
				bind(ISessionFactory.class).to(SessionFactory.class).in(Singleton.class);
			}
		});

		// TODO Nécessaire pour instancier bookRepository mais c'est pas top
		factory = injector.getInstance(ISessionFactory.class);
		session = factory.openSession();

		bookRepository = new BookRepository();
	}

	@After
	public void tearDown() {
		session.close();
		factory.close();
	}

	@Test
	public void createBookFromSingleEvent() {
		// GIVEN
		BookId bookId = BookId.newBookId();

		// WHEN
		BookRegistered event = new BookRegistered(bookId, "The Lord of the Rings", "0-618-15396-9");
		Book book = bookRepository.createInstance(bookId, event);

		// THEN
		assertThat(book).isEqualToComparingOnlyGivenFields(event, "id", "title", "isbn");
		assertThat(book.borrower).isNull();
	}

	@Test
	public void createBookFromEvents() {
		// GIVEN
		BookId bookId = BookId.newBookId();

		// WHEN
		BookRegistered event1 = new BookRegistered(bookId, "The Lord of the Rings", "0-618-15396-9");
		BookLent event2 = new BookLent(bookId, "Alice", LocalDate.now(), Period.ofDays(14));
		Book book = bookRepository.createInstance(bookId, event1, event2);

		// THEN
		assertThat(book).isEqualToComparingOnlyGivenFields(event1, "id", "title", "isbn");
		assertThat(book).isEqualToComparingOnlyGivenFields(event2, "id", "borrower", "date", "expectedDuration");
	}

}
