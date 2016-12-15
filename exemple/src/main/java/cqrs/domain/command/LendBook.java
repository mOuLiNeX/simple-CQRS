package cqrs.domain.command;

import java.time.LocalDate;
import java.time.Period;

import cqrs.api.command.Command;
import cqrs.domain.BookId;

public class LendBook implements Command {

    public final BookId id;
    public final String name;
    public final LocalDate date;
    public final Period duration;

    public LendBook(BookId bookId, String name, LocalDate date, Period duration) {
        this.id = bookId;
        this.name = name;
        this.date = date;
        this.duration = duration;
    }
}
