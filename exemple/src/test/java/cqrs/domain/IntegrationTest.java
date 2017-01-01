package cqrs.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.Period;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import cqrs.api.event.storage.IAggregateRootStorage;
import cqrs.api.event.storage.IEventStorage;
import cqrs.api.exception.ArgumentException;
import cqrs.api.exception.InvalidOperationException;
import cqrs.api.repository.ISessionFactory;
import cqrs.command.CreateBook;
import cqrs.command.LendBook;
import cqrs.command.TakeBookBack;
import cqrs.command.handler.BookCommandHandler;
import cqrs.domain.event.BookLent;
import cqrs.domain.event.BookRegistered;
import cqrs.domain.event.BookReturned;
import cqrs.query.BookState;
import cqrs.query.IBookStateQuery;
import cqrs.runner.MyGuiceRunner;

@RunWith(MyGuiceRunner.class)
public class IntegrationTest {
	@Inject
	private ISessionFactory factory;
	@Inject
	private IBookStateQuery query;
	@Inject
	private IEventStorage eventStore;
	@Inject
	private BookCommandHandler bookCommandHandler;

	private IAggregateRootStorage<BookId> bookEvtStore;

	@Before
	public void setUp() {
		bookEvtStore = eventStore.getAggregateRootStore(Book.class);
		// On s'assure qu'on démarre dans un environnement vierge
		assertThat(bookEvtStore.isEmpty()).isTrue();
		assertThat(query.getBookStates()).isEmpty();
	}

	@After
	public void tearDown() {
		factory.close();
		query.reset();
		bookEvtStore.reset();
	}

	@Test
	public void emptyQueryState() throws Exception {
		// GIVEN
		// No op

		// WHEN
		// No op

		// THEN
		assertThat(query.getBookStates()).isEmpty();
	}

	@Test
	public void aCreatedBookIsNotLent() throws Exception {
		// GIVEN
		BookId bookId = BookId.newBookId();

		// WHEN
		bookCommandHandler.handle(new CreateBook(bookId, "The Lord of the Rings", "0-618-15396-9"));

		// THEN
		BookState projection = query.getBookState(bookId);
		assertThat(projection.isLent()).isFalse();
		assertThat(query.getLentBooks()).doesNotContain(projection);
	}

	@Test(expected = InvalidOperationException.class)
	public void shouldNotLendUnknownBook() throws Exception {
		// GIVEN
		BookId nonCreatedBookId = BookId.newBookId();

		// WHEN
		bookCommandHandler.handle(new LendBook(nonCreatedBookId, "Alice", LocalDate.now(), Period.ofDays(14)));

		// THEN
		// cf expect
	}

	@Test
	public void aLentBookIsWellLent() throws Exception {
		// GIVEN
		BookId bookId = BookId.newBookId();
		bookCommandHandler.handle(new CreateBook(bookId, "The Lord of the Rings", "0-618-15396-9"));

		// WHEN
		bookCommandHandler.handle(new LendBook(bookId, "Alice", LocalDate.now(), Period.ofDays(14)));

		// THEN
		BookState projection = query.getBookState(bookId);
		assertThat(projection.isLent()).isTrue();
		assertThat(query.getLentBooks()).containsExactly(projection);
	}

	@Test
	public void shouldReturnALentBookInTime() throws Exception {
		// GIVEN
		BookId bookId = BookId.newBookId();
		bookCommandHandler.handle(new CreateBook(bookId, "The Lord of the Rings", "0-618-15396-9"));
		bookCommandHandler.handle(new LendBook(bookId, "Alice", LocalDate.now(), Period.ofDays(14)));

		// WHEN
		bookCommandHandler.handle(new TakeBookBack(bookId, LocalDate.now().plusDays(10)));

		// THEN
		assertThat(bookEvtStore.get(bookId)).isNotEmpty().hasSize(3).contains(
				new BookRegistered(bookId, "The Lord of the Rings", "0-618-15396-9"),
				new BookLent(bookId, "Alice", LocalDate.now(), Period.ofDays(14)),
				new BookReturned(bookId, "Alice", Period.ofDays(10), false));
	}

	@Test
	public void shouldReturnALentBookTooLate() throws Exception {
		// GIVEN
		BookId bookId = BookId.newBookId();
		bookCommandHandler.handle(new CreateBook(bookId, "The Lord of the Rings", "0-618-15396-9"));
		bookCommandHandler.handle(new LendBook(bookId, "Alice", LocalDate.now(), Period.ofDays(14)));

		// WHEN
		bookCommandHandler.handle(new TakeBookBack(bookId, LocalDate.now().plusDays(20)));

		// THEN
		assertThat(bookEvtStore.get(bookId)).isNotEmpty().hasSize(3).contains(
				new BookRegistered(bookId, "The Lord of the Rings", "0-618-15396-9"),
				new BookLent(bookId, "Alice", LocalDate.now(), Period.ofDays(14)),
				new BookReturned(bookId, "Alice", Period.ofDays(20), true));
	}

	@Test(expected = InvalidOperationException.class)
	public void couldNotReturnNonLentBook() throws Exception {
		// GIVEN
		BookId bookId = BookId.newBookId();
		bookCommandHandler.handle(new CreateBook(bookId, "The Lord of the Rings", "0-618-15396-9"));

		// WHEN
		bookCommandHandler.handle(new TakeBookBack(bookId, LocalDate.now().plusDays(20)));

		// THEN
		// cf expect
	}

	@Test(expected = ArgumentException.class)
	public void couldNotReturnBeforeLendBook() throws Exception {
		// GIVEN
		BookId bookId = BookId.newBookId();
		bookCommandHandler.handle(new CreateBook(bookId, "The Lord of the Rings", "0-618-15396-9"));
		bookCommandHandler.handle(new LendBook(bookId, "Alice", LocalDate.now(), Period.ofDays(14)));

		// WHEN
		bookCommandHandler.handle(new TakeBookBack(bookId, LocalDate.now().minusDays(2)));

		// THEN
		// cf expect
	}

	@Test
	public void aReturnedBookIsNotLentAnymore() throws Exception {
		// GIVEN
		BookId bookId = BookId.newBookId();
		bookCommandHandler.handle(new CreateBook(bookId, "The Lord of the Rings", "0-618-15396-9"));
		bookCommandHandler.handle(new LendBook(bookId, "Alice", LocalDate.now(), Period.ofDays(14)));

		// WHEN
		bookCommandHandler.handle(new TakeBookBack(bookId, LocalDate.now().plusDays(10)));

		// THEN
		assertThat(query.getBookState(bookId).isLent()).isFalse();
	}
}
