package cqrs.domain;

import java.util.UUID;

import lombok.Data;

public @Data final class BookId {
	private final UUID id;

	private BookId(UUID id) {
		this.id = id;
	}

	public static final BookId newBookId() {
		return new BookId(UUID.randomUUID());
	}

}
