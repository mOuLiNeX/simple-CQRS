package fr.manu.domain.query;

import com.google.common.base.Objects;

import fr.manu.domain.BookId;

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
		return Objects.hashCode(this.id, this.title);
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
		return Objects.equal(this.id, other.id)
				&& Objects.equal(this.title, other.title);
	}

}
