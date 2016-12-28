package cqrs.command;

import java.time.LocalDate;
import java.time.Period;

import cqrs.api.command.Command;
import cqrs.domain.BookId;
import lombok.Data;

public @Data class LendBook implements Command {

	private final BookId id;
	private final String name;
	private final LocalDate date;
	private final Period duration;

    public LendBook(BookId bookId, String name, LocalDate date, Period duration) {
        this.id = bookId;
        this.name = name;
        this.date = date;
        this.duration = duration;
    }
}
