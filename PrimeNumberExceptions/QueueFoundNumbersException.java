package PrimeNumberExceptions;

/*
 * The number of queue's prime numbers requested has been found
 * In other terms, this exception is thrown when the queue's primeNumbersFound equals or is greater than the queue's numberOfPrimeNumbersWanted
 */
public class QueueFoundNumbersException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7244019817040452859L;

	public QueueFoundNumbersException() {
		// TODO Auto-generated constructor stub
	}

	public QueueFoundNumbersException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public QueueFoundNumbersException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public QueueFoundNumbersException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public QueueFoundNumbersException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
