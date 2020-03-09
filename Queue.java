import java.util.ArrayList;
import java.util.List;

public class Queue {

	/* Not useful (performance killer) */
	public static final int WAIT_TIME = 15;

	private static final int SIZE = 1000;

	/* ArrayList of Prime Numbers */
	private List<Integer> queue;

	/* the index of the next element that the consumer will get */
	private Integer current = 0;

	/* the index of the next element that will be added to the queue by the producer */
	private Integer last = 0;

	/* queue lock to manage concurrent actions on queue */
	private Object queueLock = new Object();
	
	private Object currentLock = new Object();
	
	private Object lastLock = new Object();
	
	public Queue() {
		if (this.queue == null) {
			this.queue = new ArrayList<Integer>(SIZE);
		}
	}

	/**
	 * Add a number into the queue
	 * @param _number Integer to add to the queue
	 * @throws QueueFullException
	 */
	synchronized public void add(Integer _number) throws QueueFullException {
		// System.out.println(this);
		if (!this.isWritable()) {
			throw new QueueFullException();
		}

		/* critical section */
		synchronized (this.queueLock) {

			this.queue.add(_number);

			this.last++;
		}
		/* end critical section */

	}

	/**
	 * Gives the number held at the current index in the queue
	 * @return Integer the next item to read in the queue
	 * @throws QueueEmptyException
	 */
	synchronized public Integer get() throws QueueEmptyException {
		// System.out.println(this);

		Integer current = null;

		try {

			/* critical section */
			synchronized (this.queueLock) {

				current = this.get(this.getCurrent());

				this.current++;
			}
			/* end critical section */

		} catch (QueueEmptyException e) {
			throw e;
		}

		return current;
	}

	/**
	 * Gives the number held at the _index in the queue
	 * @param Integer index
	 * @return Integer 
	 * @throws QueueEmptyException
	 */
	private Integer get(Integer _index) throws QueueEmptyException {
		if (!this.isReadable()) {
			throw new QueueEmptyException();
		}
		return this.queue.get(_index);
	}

	/**
	 * Checks whether the last item inserted has been read
	 * @return boolean true if a new item has been inserted and has not been already read, false otherwise
	 */
	public boolean isReadable() {
		return this.getLast() > this.getCurrent();
	}
	
	/**
	 * Checks whether a new item can be inserted into the Queue 
	 * @return boolean true if the Queue is not full, false otherwise
	 */
	public boolean isWritable() {
		return this.queue.size() < Queue.SIZE;
	}

	/* GETTERS */

	/* For debugging purposes */
	public List<Integer> getQueue() {
		return this.queue;
	}

	public Integer getCurrent() {
		/* remove the lock here is something strange happens */
		synchronized(this.currentLock) {
			return this.current;
		}
	}

	public Integer getLast() {
		/* remove the lock here is something strange happens */
		synchronized(this.lastLock) {
			return this.last;
		}
	}

}
