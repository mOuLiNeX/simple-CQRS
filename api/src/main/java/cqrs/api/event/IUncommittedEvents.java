package cqrs.api.event;

public interface IUncommittedEvents extends Iterable<Event> {
	Boolean hasEvents();

	void commit();

	void append(Event event);
}
