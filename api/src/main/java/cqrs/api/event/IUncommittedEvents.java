package cqrs.api.event;

public interface IUncommittedEvents<ID> extends Iterable<Event<ID>> {
	Boolean hasEvents();

	void commit();

	void append(Event<ID> event);
}
