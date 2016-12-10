package cqrs.api.repository;

public interface ISession {
	void submitChanges();

	void close();
}
