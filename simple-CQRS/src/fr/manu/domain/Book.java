package fr.manu.domain;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.Period;

import com.google.common.base.Objects;

import fr.manu.domain.event.BookLent;
import fr.manu.domain.event.BookRegistered;
import fr.manu.domain.event.BookReturned;
import fr.manu.exception.ArgumentException;
import fr.manu.framework.exception.InvalidOperationException;
import fr.manu.framework.impl.domain.AggregateRoot;

public class Book extends AggregateRoot<BookId> {

	public String title;

	public String isbn;

	public String borrower;

	public LocalDate date;

	public Period expectedDuration;

	public Book(BookId id) {
		super(id);
	}

	public Book(BookId id, String title, String isbn) {
		this(id);

		addEvent(new BookRegistered(id, title, isbn));
	}

	public void lend(String borrower, LocalDate date, Period expectedDuration)
			throws InvalidOperationException {
		if (this.borrower != null)
			throw new InvalidOperationException("The book is already lent.");

		addEvent(new BookLent(id, borrower, date, expectedDuration));
	}

	public void giveBack(LocalDate returnDate)
			throws InvalidOperationException, ArgumentException {
		if (borrower == null)
			throw new InvalidOperationException("The book has not been lent.");
		if (returnDate.isBefore(date))
			throw new ArgumentException(
					"The book cannot be returned before being lent.");

		Days actualDuration = Days.daysBetween(date, returnDate);
		addEvent(new BookReturned(id, borrower, actualDuration,
				(actualDuration.getDays() > expectedDuration.getDays())));
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Book other = (Book) obj;
		return Objects.equal(this.id, other.id)
				&& Objects.equal(this.isbn, other.isbn)
				&& Objects.equal(this.title, other.title);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.id, this.isbn, this.title);
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).addValue(this.id)
				.addValue(this.isbn).addValue(this.title).toString();
	}
}
