package fr.manu.framework.repository;

public interface ISession {
	void submitChanges();

	void close();
}
