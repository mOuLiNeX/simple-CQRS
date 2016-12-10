package cqrs.api.event;

public abstract class EventHandler {

	protected EventHandler() {
		Bus.register(this);
	}

}
