package fr.manu.domain.repository;

import fr.manu.domain.Book;
import fr.manu.domain.BookId;

public interface IBookRepository {
	void add(Book book);

	Book findById(BookId id);
}
