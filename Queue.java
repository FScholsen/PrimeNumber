import java.util.ArrayList;
import java.util.List;

public class Queue {

	/* Not useful (performance killer) */
	public static final int WAIT_TIME = 15;

	private static final int SIZE = 1000;

	private List<Integer> queue;

	private Object lock = new Object();

	/* TODO : there is no need to store those current and last as static members */
	/* the index of the next element that the consumer will get */
	private static Integer current = 0;

	/*
	 * the index of the next element that will be added to the queue by the producer
	 */
	private static Integer last = 0;

	public Queue() {
		if (this.queue == null) {
			this.queue = new ArrayList<Integer>(SIZE);
		}
	}

	synchronized public void add(Integer _number) throws QueueFullException {
		// System.out.println(this);
		if (this.queue.size() >= SIZE) {
			throw new QueueFullException();
		}

		/* critical section */
		synchronized (this.lock) {

			this.queue.add(_number);

			Queue.last++;
		}
		/* end critical section */

	}

	synchronized public Integer get() throws QueueEmptyException {
		// System.out.println(this);

		Integer current = null;

		try {

			/* critical section */
			synchronized (this.lock) {

				current = this.get(Queue.getCurrent());

				Queue.current++;
			}
			/* end critical section */

		} catch (QueueEmptyException e) {
			throw e;
		}

		return current;
	}

	private Integer get(Integer _number) throws QueueEmptyException {
		if (Queue.getLast() <= Queue.getCurrent()) {
			throw new QueueEmptyException();
		}
		return this.queue.get(_number);
	}

	/* GETTERS */

	/* For debugging purposes */
	public List<Integer> getQueue() {
		return this.queue;
	}

	public static Integer getCurrent() {
		return Queue.current;
	}

	public static Integer getLast() {
		return Queue.last;
	}
	
	/*
	 * Checks whether the last item inserted has been read (true) 
	 */
	public boolean completedReadingQueue() {
		return Queue.getLast() == Queue.getCurrent();
	}

}
