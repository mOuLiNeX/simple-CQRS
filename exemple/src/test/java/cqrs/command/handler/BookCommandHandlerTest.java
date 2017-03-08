package cqrs.command.handler;

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
import cqrs.api.exception.InvalidOperationException;
import cqrs.command.CreateBook;
import cqrs.command.LendBook;
import cqrs.command.TakeBookBack;
import cqrs.domain.Book;
import cqrs.domain.BookId;
import cqrs.domain.event.BookLent;
import cqrs.domain.event.BookRegistered;
import cqrs.domain.event.BookReturned;
import cqrs.runner.MyGuiceRunner;

@RunWith(MyGuiceRunner.class)
public class BookCommandHandlerTest {
	@Inject
	private BookCommandHandler bookCommandHandler;
	@Inject
	private IEventStorage eventStore;

	private IAggregateRootStorage<BookId> bookEvtStore;

	@Before
	public void setUp() {
		bookEvtStore = eventStore.getAggregateRootStore(Book.class);
		// On s'assure qu'on démarre dans un environnement vierge
		assertThat(bookEvtStore.isEmpty()).isTrue();
	}

	@After
	public void tearDown() {
		bookEvtStore.reset();
	}

	@Test
	public void emptyEventStore() throws Exception {
		// GIVEN
		// No op

		// WHEN
		// No op

		// THEN
		assertThat(bookEvtStore.isEmpty()).isTrue();
	}

	@Test
	public void shouldCreateABook() throws Exception {
		// GIVEN
		BookId bookId = BookId.newBookId();

		// WHEN
		bookCommandHandler.handle(new CreateBook(bookId, "The Lord of the Rings", "0-618-15396-9"));

		// THEN
		assertThat(bookEvtStore.get(bookId)).isNotEmpty().hasSize(1)
				.contains(new BookRegistered(bookId, "The Lord of the Rings", "0-618-15396-9"));
	}

	@Test
	public void shouldLendABook() throws Exception {
		// GIVEN
		BookId bookId = BookId.newBookId();
		bookCommandHandler.handle(new CreateBook(bookId, "The Lord of the Rings", "0-618-15396-9"));

		// WHEN
		bookCommandHandler.handle(new LendBook(bookId, "Alice", LocalDate.now(), Period.ofDays(14)));

		// THEN
		assertThat(bookEvtStore.get(bookId)).isNotEmpty().hasSize(2).contains(
				new BookRegistered(bookId, "The Lord of the Rings", "0-618-15396-9"),
				new BookLent(bookId, "Alice", LocalDate.now(), Period.ofDays(14)));
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
}
