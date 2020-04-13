package PrimeNumberExceptions;
/*
 * This exception is thrown when the limit number of prime numbers that the queue can calculate is given
 */
public class QueueSizeLimitException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4448495870995540697L;

	public QueueSizeLimitException() {
		// TODO Auto-generated constructor stub
	}

	public QueueSizeLimitException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public QueueSizeLimitException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public QueueSizeLimitException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public QueueSizeLimitException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
