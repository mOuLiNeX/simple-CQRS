package cqrs.domain.query;

import java.util.Objects;

import cqrs.domain.BookId;

public final class BookState {

	public final BookId id;

	public final String title;

	// C'est le seul attribut mutable
	public Boolean lent;

	public BookState(BookId id, String title, Boolean lent) {
		this.id = id;
		this.title = title;
		this.lent = lent;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.title);
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
		BookState other = (BookState) obj;
		return Objects.equals(this.id, other.id) && Objects.equals(this.title, other.title);
	}

}
