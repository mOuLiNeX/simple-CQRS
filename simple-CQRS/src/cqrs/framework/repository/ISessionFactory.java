package cqrs.framework.repository;

public interface ISessionFactory {
	ISession openSession();

	void close();
}
