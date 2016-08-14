package zc.studdy.rpc.rmi.shared;

import org.apache.commons.lang3.exception.ContextedRuntimeException;
import org.apache.commons.lang3.exception.ExceptionContext;


public class ServiceLocatorException extends ContextedRuntimeException {
	private static final long serialVersionUID = 1L;

	public ServiceLocatorException() {
		super();
	}

	public ServiceLocatorException(String message, Throwable cause, ExceptionContext context) {
		super(message, cause, context);
	}

	public ServiceLocatorException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceLocatorException(String message) {
		super(message);
	}

	public ServiceLocatorException(Throwable cause) {
		super(cause);
	}
}
