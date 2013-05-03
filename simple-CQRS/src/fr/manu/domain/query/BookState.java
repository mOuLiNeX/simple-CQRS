package fr.manu.domain.query;

import fr.manu.domain.BookId;

public final class BookState {

	public final BookId id;

	public final String title;

	public Boolean lent;

	public BookState(BookId id, String title, Boolean lent) {
		this.id = id;
		this.title = title;
		this.lent = lent;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lent == null) ? 0 : lent.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookState other = (BookState) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lent == null) {
			if (other.lent != null)
				return false;
		} else if (!lent.equals(other.lent))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

}
