/*
 * Queue is full, nothing can be written in it
 * In other terms, this exception is thrown when the queue's size equals or is greater than the queue's MAX_QUEUE_SIZE
 */
public class QueueFullException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6771435114421146393L;

	public QueueFullException() {
		// TODO Auto-generated constructor stub
	}

	public QueueFullException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public QueueFullException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public QueueFullException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public QueueFullException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
