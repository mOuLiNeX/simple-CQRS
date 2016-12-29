package cqrs.command.handler;

import javax.inject.Inject;

import cqrs.api.event.repository.IRepository;
import cqrs.api.repository.ISession;
import cqrs.api.repository.ISessionFactory;
import cqrs.command.CreateBook;
import cqrs.command.LendBook;
import cqrs.command.TakeBookBack;
import cqrs.command.repository.BookRepository;
import cqrs.domain.Book;
import cqrs.domain.BookId;

public class BookCommandHandler {
	private final ISessionFactory factory;

	@Inject
	public BookCommandHandler(ISessionFactory factory) {
		this.factory = factory;
	}

	private IRepository<BookId, Book> getBooks() {
		// TODO Trouver mieux pour mutualiser l'instance par unit or work
		return new BookRepository();
	}

	public void handle(CreateBook command) {
		ISession session = factory.openSession();
		try {
			getBooks().add(new Book(command.id, command.title, command.isbn));
			session.submitChanges();
		} finally {
			session.close();
		}
	}

	public void handle(LendBook command) {
		ISession session = factory.openSession();
		try {
			Book book = getBooks().findById(command.getId());
			book.lend(command.getName(), command.getDate(), command.getDuration());
			session.submitChanges();
		} finally {
			session.close();
		}
	}

	public void handle(TakeBookBack command) throws Exception {
		ISession session = factory.openSession();
		try {
			getBooks().findById(command.getId()).giveBack(command.getReturnDate());
			session.submitChanges();
		} finally {
			session.close();
		}
	}

}
