package cqrs.domain.query;

import java.util.Objects;

import cqrs.domain.BookId;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
public @Data final class BookState {
	private final BookId id;
	private final String title;
	// C'est le seul attribut mutable
	private boolean lent;
}
