package fr.manu.domain;

import java.util.UUID;

import com.google.common.base.Objects;

public final class BookId {
	private final UUID id;

	private BookId(UUID id) {
		this.id = id;
	}

	public static final BookId newBookId() {
		return new BookId(UUID.randomUUID());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookId other = (BookId) obj;
		return Objects.equal(this.id, other.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.id);
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).addValue(this.id).toString();
	}

}
