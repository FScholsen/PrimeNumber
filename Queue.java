import java.util.ArrayList;

public class Queue extends ArrayList<Number> {

	/* a serial version unique identifier inherited from ArrayList */
	private static final long serialVersionUID = 1L;

	/* Wait time for the Threads to sleep, limits performance */
	public static final int WAIT_TIME = 50;

	/* the max allowed size of prime numbers to find */
	private static final int MAX_PRIME_NUMBER = 10000;

	/* the max allowed amount of Numbers that can be added to the queue */
	private static final int MAX_QUEUE_SIZE = 100000;

	/* the index of the next element that the consumer will read */
	private int readCursor = 0;

	/*
	 * the index of the next element that will be written into the queue by the
	 * producer
	 */
	private int writeCursor = 0;

	/* the number of prime numbers to find */
	private int numberOfPrimeNumbersWanted;

	/* the count of numbers flagged as primes */
	private int primeNumbersFound = 0;

	/* queue lock to manage concurrent actions on queue */
	private Object queueLock = new Object();

	private static Queue queue = null;

	/**
	 * @param numberOfPrimeNumbersWanted
	 * @throws QueueSizeLimitException
	 */
	private Queue(int numberOfPrimeNumbersWanted) throws QueueSizeLimitException {
		if (!this.isValidNumberOfPrimeNumbers(numberOfPrimeNumbersWanted)) {
			throw new QueueSizeLimitException(
					"The number of prime numbers to find is greater than or equals to " + Queue.MAX_PRIME_NUMBER);
		}
		this.numberOfPrimeNumbersWanted = numberOfPrimeNumbersWanted;
	}

	/**
	 * The static method for retrieving the instance of the Singleton Queue class
	 * 
	 * @param numberOfPrimeNumbersWanted the limit of prime numbers to find
	 * @return The Queue instance
	 * @throws QueueSizeLimitException
	 */
	public static Queue getQueue(int numberOfPrimeNumbersWanted) throws QueueSizeLimitException {
		if (Queue.queue == null)
			queue = new Queue(numberOfPrimeNumbersWanted);
		return queue;
	}

	/*** GETTERS AND SETTERS ***/

	private int getReadCursor() {
		return readCursor;
	}

	private void incrementReadCursor() {
		this.readCursor++;
	}

	private int getWriteCursor() {
		return writeCursor;
	}

	private void incrementWriteCursor() {
		this.writeCursor++;
	}

	private int getNumberOfPrimeNumbersWanted() {
		return numberOfPrimeNumbersWanted;
	}

	private int getPrimeNumbersFound() {
		return primeNumbersFound;
	}

	private void incrementPrimeNumbersFound() {
		this.primeNumbersFound++;
	}

	/*** END GETTERS AND SETTERS ***/

	/**
	 * Checks whether the number of prime numbers to find is not greater than the
	 * MAX_PRIME_NUMBER
	 * 
	 * @param primeNumbersWanted
	 * @return boolean true is the number of prime numbers wanted do not exceed
	 *         MAX_PRIME_NUMBER, false otherwise
	 */
	private boolean isValidNumberOfPrimeNumbers(int primeNumbersWanted) {
		return primeNumbersWanted < Queue.MAX_PRIME_NUMBER;
	}

	/**
	 * Checks whether the last item inserted has been read
	 * 
	 * @return boolean true if a new item has been inserted and has not been already
	 *         read, false otherwise
	 */
	private boolean isReadable() {
		return this.getWriteCursor() > this.getReadCursor();
	}

	/**
	 * Checks whether a new item can be inserted into the Queue
	 * 
	 * @return boolean true if the Queue is not full, false otherwise
	 */
	private boolean isWritable() {
		return this.size() < MAX_QUEUE_SIZE;
	}

	/**
	 * Checks whether work is over
	 * 
	 * @return boolean true if all the prime numbers to find have been found, false
	 *         otherwise
	 */
	private boolean primeNumbersFound() {
		return this.getPrimeNumbersFound() >= this.getNumberOfPrimeNumbersWanted();
	}

	/**
	 * Get the number held at the current index in the queue (i.e. the next number
	 * to read)
	 * 
	 * @return Number the next item to read in the queue
	 * @throws QueueEmptyException
	 * @throws QueueFoundNumbersException
	 */
	protected Number read() throws QueueEmptyException, QueueFoundNumbersException {

		/* critical section */
		synchronized (this.queueLock) {
			if (this.primeNumbersFound()) {
				throw new QueueFoundNumbersException("QueueFoundNumbersException");
			}

			if (!this.isReadable()) {
				throw new QueueEmptyException("Queue is empty");
			}
			Number number = super.get(this.getReadCursor());
			this.incrementReadCursor();

			if (number.isPrime()) {
				number.setPrime(true);
				this.incrementPrimeNumbersFound();
			}
			return number;
		}
		/* end critical section */
	}

	/**
	 * Add a new Number into the queue according to queue's writeCursor
	 * 
	 * @throws QueueFullException
	 * @throws QueueFoundNumbersException
	 */
	protected void write() throws QueueFullException, QueueFoundNumbersException {
		/* critical section */
		synchronized (this.queueLock) {
			if (this.primeNumbersFound()) {
				throw new QueueFoundNumbersException("QueueFoundNumbersException");
			}
			if (!this.isWritable()) {
				throw new QueueFullException("Queue is full");
			}
			Number number = new Number(this.getWriteCursor());
			this.add(number);
			this.incrementWriteCursor();
		}
		/* end critical section */
	}

}
