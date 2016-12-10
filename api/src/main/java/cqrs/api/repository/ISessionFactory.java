package cqrs.api.repository;

public interface ISessionFactory {
	ISession openSession();

	void close();
}
