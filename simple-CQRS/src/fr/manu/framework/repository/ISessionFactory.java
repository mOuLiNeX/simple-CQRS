package fr.manu.framework.repository;

public interface ISessionFactory {
	ISession openSession();

	void close();
}
