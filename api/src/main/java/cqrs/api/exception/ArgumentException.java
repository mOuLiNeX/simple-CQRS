package cqrs.api.exception;

@SuppressWarnings("serial")
public class ArgumentException extends Exception {

    public ArgumentException(String message) {
        super(message);
    }

}
