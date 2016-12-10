package cqrs.api.exception;

@SuppressWarnings("serial")
public class InvalidOperationException extends RuntimeException {

	public InvalidOperationException(String message) {
		super(message);
	}

}
