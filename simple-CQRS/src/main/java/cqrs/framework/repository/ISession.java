package cqrs.framework.repository;

public interface ISession {
	void submitChanges();

	void close();
}
