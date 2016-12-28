package cqrs.query.handler;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.Period;

import javax.inject.Singleton;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import cqrs.domain.BookId;
import cqrs.domain.event.BookLent;
import cqrs.domain.event.BookRegistered;
import cqrs.domain.event.BookReturned;
import cqrs.query.BookStateQuery;
import cqrs.query.IBookStateQuery;

public class BookStateHandlerTest {
	private IBookStateQuery query;

	private BookStateHandler bookStateHandler;

	@Before
	public void setUp() {
		Injector injector = Guice.createInjector(new AbstractModule() {
			@Override
			protected void configure() {
				bind(IBookStateQuery.class).to(BookStateQuery.class).in(Singleton.class);
			}
		});

		query = injector.getInstance(IBookStateQuery.class);

		bookStateHandler = injector.getInstance(BookStateHandler.class);
	}

	@Test
	public void emptyState() throws Exception {
		// GIVEN

		// WHEN

		// THEN
		assertThat(query.getBookStates()).isEmpty();
	}

	@Test
	public void shouldSeeACreatedBook() throws Exception {
		// GIVEN
		BookId bookId = BookId.newBookId();

		// WHEN
		BookRegistered event = new BookRegistered(bookId, "The Lord of the Rings", "0-618-15396-9");
		bookStateHandler.handle(event);

		// THEN
		assertThat(query.containsBookState(bookId)).isTrue();
		assertThat(query.getBookState(bookId)).isEqualToComparingOnlyGivenFields(event, "id", "title");
		assertThat(query.getBookState(bookId).isLent()).isFalse();
	}

	@Test
	public void shouldSeeALentBook() throws Exception {
		// GIVEN
		BookId bookId = BookId.newBookId();
		bookStateHandler.handle(new BookRegistered(bookId, "The Lord of the Rings", "0-618-15396-9"));

		// WHEN
		BookLent event = new BookLent(bookId, "Alice", LocalDate.now(), Period.ofDays(14));
		bookStateHandler.handle(event);

		// THEN
		assertThat(query.containsBookState(bookId)).isTrue();
		assertThat(query.getBookState(bookId).isLent()).isTrue();
	}

	@Test
	public void shouldSeeAReturnedBook() throws Exception {
		// GIVEN
		BookId bookId = BookId.newBookId();
		bookStateHandler.handle(new BookRegistered(bookId, "The Lord of the Rings", "0-618-15396-9"));
		bookStateHandler.handle(new BookLent(bookId, "Alice", LocalDate.now(), Period.ofDays(14)));

		// WHEN
		BookReturned event = new BookReturned(bookId, "Alice", Period.ofDays(10), false);
		bookStateHandler.handle(event);

		// THEN
		assertThat(query.containsBookState(bookId)).isTrue();
		assertThat(query.getBookState(bookId).isLent()).isFalse();
	}

}
