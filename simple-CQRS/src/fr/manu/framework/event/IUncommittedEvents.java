package fr.manu.framework.event;

public interface IUncommittedEvents extends Iterable<Event> {
	Boolean hasEvents();

	void commit();

	void append(Event event);
}
