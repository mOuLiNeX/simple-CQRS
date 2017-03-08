package cqrs.api.event;

@FunctionalInterface
public interface Event<ID> {
	ID getId();
}
