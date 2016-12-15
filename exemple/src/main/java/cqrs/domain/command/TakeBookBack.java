package cqrs.domain.command;

import java.time.LocalDate;

import cqrs.api.command.Command;
import cqrs.domain.BookId;

public class TakeBookBack implements Command {
    public final BookId id;
    public final LocalDate returnDate;

    public TakeBookBack(BookId id, LocalDate returnDate) {
        this.id = id;
        this.returnDate = returnDate;
    }
}
