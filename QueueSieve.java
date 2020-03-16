import java.util.Arrays;

public class QueueSieve {

	private static final int NEW = -1; // PRIME alias
	private static final int PRIME = 0;
	public static final int NON_PRIME = 1;

	/* Wait time for the Threads to sleep, limits performance */
	public static final int WAIT_TIME = 50;

	/* the max allowed amount of Numbers that can be added to the queue */
	private static final int MAX_QUEUE_SIZE = 100000;

	/* the count of prime numbers to find */
	private static final int PRIME_NUMBERS_TO_FIND = 1000;
	
	/* the index of the next element that the consumer will read */
	private int readCursor = 2;

	/*
	 * the index of the next element that will be written into the queue by the
	 * producer
	 */
	private int writeCursor = 2;

	/* queue lock to manage concurrent actions on queue */
	private Object queueLock = new Object();

	private static QueueSieve queue = null;

	private int[] numbers;

	private int factorLimit;

	private static int numbersToAnalyze;
	
	/* the count of numbers flagged as primes */
	private int primeNumbersFound = 0;

	/**
	 * @param numberOfPrimeNumbersWanted
	 * @throws QueueSizeLimitException
	 */
	private QueueSieve(int _numbersToAnalyze) throws QueueSizeLimitException {
		if (!this.isValidNumbersToAnalyze(_numbersToAnalyze)) {
			throw new QueueSizeLimitException(
					"The count of numbers to analyze is greater than or equals to " + QueueSieve.MAX_QUEUE_SIZE);
		}
		QueueSieve.setNumbersToAnalyze(_numbersToAnalyze);
		this.setFactorLimit((int) Math.sqrt((double) _numbersToAnalyze) + 1);
		this.numbers = new int[_numbersToAnalyze + 1];
		Arrays.fill(numbers, QueueSieve.NEW);
	}

	/**
	 * The static method for retrieving the instance of the Singleton Queue class
	 * 
	 * @param numberOfPrimeNumbersWanted the limit of prime numbers to find
	 * @return The QueueSieve instance
	 * @throws QueueSizeLimitException
	 */
	public static QueueSieve getQueue() throws QueueSizeLimitException {
		if (QueueSieve.queue == null)
			try {
				queue = new QueueSieve(QueueSieve.getNumbersToAnalyze());
			} catch (QueueSizeLimitException e) {
				System.err.println("Error: " + e.getMessage());
				throw e;
			}
		return queue;
	}

	/*** GETTERS AND SETTERS ***/

	private int getReadCursor() {
		return this.readCursor;
	}

	private void incrementReadCursor() {
		this.readCursor++;
	}

	private int getWriteCursor() {
		return this.writeCursor;
	}

	private void incrementWriteCursor() {
		this.writeCursor++;
	}

	public static int getNumbersToAnalyze() {
		return QueueSieve.numbersToAnalyze;
	}

	public static void setNumbersToAnalyze(int numbersToAnalyze) {
		QueueSieve.numbersToAnalyze = numbersToAnalyze;
	}

	public int getFactorLimit() {
		return this.factorLimit;
	}

	public void setFactorLimit(int factorLimit) {
		this.factorLimit = factorLimit;
	}

	public int[] getNumbers() {
		return this.numbers;
	}

	public void setNumbers(int[] numbers) {
		this.numbers = numbers;
	}

	public int getNumber(int index) {
		return this.numbers[index];
	}

	public void setNumber(int index, int value) {
		this.numbers[index] = value;
	}
	
	private int getPrimeNumbersFound() {
		return primeNumbersFound;
	}

	private void incrementPrimeNumbersFound() {
		this.primeNumbersFound++;
	}

	/*** END GETTERS AND SETTERS ***/

	private boolean isValidNumbersToAnalyze(int _numbersToAnalyze) {
		return _numbersToAnalyze < QueueSieve.MAX_QUEUE_SIZE;
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
	 * Checks whether the whole queue has been read
	 * 
	 * @return boolean true if the queue's last item has been read, false otherwise
	 */
	private boolean queueLimitReached() {
		return QueueSieve.getNumbersToAnalyze() >= this.getReadCursor();
	}

	/**
	 * Checks whether a new item can be inserted into the Queue
	 * 
	 * @return boolean true if the Queue is not full, false otherwise
	 */
	public boolean isWritable() {
		return this.getWriteCursor() <= QueueSieve.getNumbersToAnalyze();
	}
	
	/**
	 * Checks whether work is over
	 * 
	 * @return boolean true if all the prime numbers to find have been found, false
	 *         otherwise
	 */
	private boolean primeNumbersFound() {
		return this.getPrimeNumbersFound() >= QueueSieve.PRIME_NUMBERS_TO_FIND;
	}

	/**
	 * Get the number held at the current index in the queue (i.e. the next number
	 * to read)
	 * 
	 * @return Number the next item to read in the queue
	 * @throws QueueEmptyException
	 * @throws QueueReadLimitException
	 * @throws QueueFoundNumbersException 
	 */
	protected int read() throws QueueEmptyException, QueueReadLimitException, QueueFoundNumbersException {

		/* critical section */
		synchronized (this.queueLock) {
			if (!this.queueLimitReached()) {
				throw new QueueReadLimitException("Queue limit reached. Stopping.");
			}
			if (!this.isReadable()) {
				throw new QueueEmptyException("Queue is empty");
			}
			if (this.primeNumbersFound()) {
				throw new QueueFoundNumbersException("QueueFoundNumbersException");
			}
			int n = this.getReadCursor();
			this.incrementReadCursor();
			int number = this.getNumber(n);

			if (QueueSieve.isPrime(number)) {
				System.out.println(n);
				this.incrementPrimeNumbersFound();
			}
			return n;
		}
		/* end critical section */
	}

	/**
	 * Add a new Number into the queue according to queue's writeCursor
	 * 
	 * @throws QueueFullException
	 * @throws QueueFoundNumbersException
	 */
	public void write() throws QueueFullException {
		/* critical section */
		synchronized (this.queueLock) {
			if (!this.isWritable()) {
				throw new QueueFullException("Queue is full");
			}
			int n = this.getWriteCursor();
			int number = this.getNumber(n);

			if (QueueSieve.isUninitialized(number)) {
				this.setNumber(n, QueueSieve.PRIME);
				for (int i = n * n; i <= QueueSieve.getNumbersToAnalyze(); i += n) {
					this.setNumber(i, QueueSieve.NON_PRIME);
				}
			}

			this.incrementWriteCursor();
		}
		/* end critical section */
	}

	private static boolean isPrime(int number) {
		return number < QueueSieve.NON_PRIME;
	}

	private static boolean isUninitialized(int number) {
		return number < QueueSieve.PRIME;
	}

}
