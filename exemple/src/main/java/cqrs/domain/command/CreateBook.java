package cqrs.domain.command;

import cqrs.api.command.Command;
import cqrs.domain.BookId;

public class CreateBook implements Command {

    public final String isbn;
    public final BookId id;
    public final String title;

    public CreateBook(BookId id, String title, String isbn) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
    }
}
