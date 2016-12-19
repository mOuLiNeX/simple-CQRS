package cqrs.domain.command.handler;

import cqrs.api.event.repository.IRepository;
import cqrs.api.repository.ISession;
import cqrs.api.repository.ISessionFactory;
import cqrs.domain.Book;
import cqrs.domain.BookId;
import cqrs.domain.command.CreateBook;
import cqrs.domain.command.LendBook;
import cqrs.domain.command.TakeBookBack;
import cqrs.domain.repository.BookRepository;

public class BookCommandHandler {
	private ISessionFactory factory;
	private IRepository<BookId, Book> repository;

	public BookCommandHandler(ISessionFactory factory) {
		this.factory = factory;
	}

	public IRepository<BookId, Book> getBooks() {
		return new BookRepository();
	}

	public void handle(CreateBook command) throws Exception {
		ISession session = factory.openSession();
		try {
			getBooks().add(new Book(command.id, command.title, command.isbn));
			session.submitChanges();
		} finally {
			session.close();
		}
	}

	public void handle(LendBook command) throws Exception {
		ISession session = factory.openSession();
		try {
			Book book = getBooks().findById(command.id);
			book.lend(command.name, command.date, command.duration);
			session.submitChanges();
		} finally {
			session.close();
		}
	}

	public void handle(TakeBookBack command) throws Exception {
		ISession session = factory.openSession();
		try {
			getBooks().findById(command.id).giveBack(command.returnDate);
			session.submitChanges();
		} finally {
			session.close();
		}
	}

}
