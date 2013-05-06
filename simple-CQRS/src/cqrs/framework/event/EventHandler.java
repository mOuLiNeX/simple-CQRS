package cqrs.framework.event;

public abstract class EventHandler {

	protected EventHandler() {
		Bus.register(this);
	}

}
