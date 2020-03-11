import java.util.ArrayList;

public class Queue extends ArrayList<Number> {

	private static final long serialVersionUID = 1L;

	/* Not useful (performance killer) */
	public static final int WAIT_TIME = 1000;

	/* the max allowed size of prime numbers to find */
	private static final int MAX_SIZE = 10000;

	/* the index of the next element that the consumer will get */
	private int readCursor = 0;

	/* the index of the next element that will be added to the queue by the producer */
	private int writeCursor = 0;

	/* the number of prime numbers to find */
	private int numberOfPrimeNumbersWanted;

	/* the count of numbers flagged as primes */
	private int primeNumbersFound = 0;

	/* queue lock to manage concurrent actions on queue */
	private Object queueLock = new Object();

	public Queue(int numberOfPrimeNumbersWanted) throws QueueSizeLimitException {
		if (!this.isValidNumberOfPrimeNumbers(numberOfPrimeNumbersWanted)) {
			throw new QueueSizeLimitException(
					"The number of prime numbers to find is greater than or equals to " + Queue.MAX_SIZE);
		}
		this.numberOfPrimeNumbersWanted = numberOfPrimeNumbersWanted;
	}

	/*** GETTERS AND SETTERS ***/

	synchronized private int getReadCursor() {
		synchronized (this.queueLock) {
			return readCursor;
		}
	}

	private void setReadCursor(int readCursor) {
		this.readCursor = readCursor;
	}

	private void incrementReadCursor() {
		this.readCursor++;
	}

	synchronized protected int getWriteCursor() {
		synchronized (this.queueLock) {
			return writeCursor;
		}
	}

	private void setWriteCursor(int writeCursor) {
		this.writeCursor = writeCursor;
	}

	private void incrementWriteCursor() {
		this.writeCursor++;
	}

	private int getNumberOfPrimeNumbersWanted() {
		return numberOfPrimeNumbersWanted;
	}

	private void setNumberOfPrimeNumbersWanted(int numberOfPrimeNumbersWanted) {
		this.numberOfPrimeNumbersWanted = numberOfPrimeNumbersWanted;
	}

	private int getPrimeNumbersFound() {
		return primeNumbersFound;
	}

	private void setPrimeNumbersFound(int primeNumbersFound) {
		this.primeNumbersFound = primeNumbersFound;
	}

	synchronized protected void incrementPrimeNumbersFound() {
		synchronized (this.queueLock) {
			this.primeNumbersFound++;
		}
	}

	/*** END GETTERS AND SETTERS ***/

	/*
	 * Checks whether the number of prime numbers to find is not bigger than the
	 * MAX_SIZE constant
	 */
	public boolean isValidNumberOfPrimeNumbers(int primeNumbersWanted) {
		return primeNumbersWanted < MAX_SIZE;
	}

	/**
	 * Checks whether the last item inserted has been read
	 * 
	 * @return boolean true if a new item has been inserted and has not been already
	 *         read, false otherwise
	 */
	synchronized protected boolean isReadable() {
		return this.getWriteCursor() > this.getReadCursor();
	}

	/**
	 * Checks whether a new item can be inserted into the Queue
	 * 
	 * @return boolean true if the Queue is not full, false otherwise
	 */
	synchronized protected boolean isWritable() {
		return this.size() < MAX_SIZE;
	}

	/**
	 * @return boolean true if all the prime numbers to find have been found, false
	 *         otherwise
	 */
	synchronized protected boolean primeNumbersComputeComplete() {
		synchronized (this.queueLock) {
			return this.getPrimeNumbersFound() >= this.getNumberOfPrimeNumbersWanted();
		}
	}

	/**
	 * Gives the number held at the _index in the queue
	 * 
	 * @param int _index
	 * @return Number
	 */
	private Number read(int _index) throws QueueEmptyException {
		if (!this.isReadable()) {
			throw new QueueEmptyException("Queue is empty");
		}
		return super.get(_index);
	}

	/**
	 * Returns the number held at the current index in the queue
	 * 
	 * @return Number the next item to read in the queue
	 * @throws QueueEmptyException
	 * @hint synchronization is the key
	 */
	synchronized protected Number read() throws QueueEmptyException {
		Number number = null;
		try {
			/* critical section */
			synchronized (this.queueLock) {
				number = this.read(this.getReadCursor());
				this.incrementReadCursor();
			}
			/* end critical section */
		} catch (QueueEmptyException e) {
			throw e;
		}
		return number;
	}

	/**
	 * Add a number into the queue
	 * 
	 * @param _number Number to add to the queue
	 * @throws QueueFullException
	 */
	synchronized protected void write(Number _number) throws QueueFullException {
		if (!this.isWritable()) {
			throw new QueueFullException("Queue is full");
		}
		/* critical section */
		synchronized (this.queueLock) {
			this.add(_number);
			this.incrementWriteCursor();
		}
		/* end critical section */
	}

}
