package cqrs.domain;

import java.util.Objects;
import java.util.UUID;

import com.google.common.base.MoreObjects;

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
		return Objects.equals(this.id, other.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id);
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).addValue(this.id).toString();
	}

}
