package base.exception;

import org.springframework.stereotype.Component;

@Component
public class ServiceException extends Exception {

	private static final long serialVersionUID = 7300475600007121950L;

	public ServiceException() {
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Throwable cause) {
		super(cause);

	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);

	}
}
