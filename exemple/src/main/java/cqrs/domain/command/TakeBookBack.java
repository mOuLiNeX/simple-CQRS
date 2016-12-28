package cqrs.domain.command;

import java.time.LocalDate;

import cqrs.api.command.Command;
import cqrs.domain.BookId;
import lombok.Data;

public @Data class TakeBookBack implements Command {
	private final BookId id;
	private final LocalDate returnDate;

	public TakeBookBack(BookId id, LocalDate returnDate) {
		this.id = id;
		this.returnDate = returnDate;
	}
}
