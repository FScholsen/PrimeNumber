
public abstract class WorkerSieve {

	private QueueSieve queue;

	/**
	 * Set the queue for either the producer, either the consumer
	 * @param queue 
	 */
	protected WorkerSieve(QueueSieve queue) {
		this.setQueue(queue);
	}

	/**
	 * 
	 * @return the queue instance
	 */
	protected QueueSieve queue() {
		return queue;
	}

	private void setQueue(QueueSieve queue) {
		this.queue = queue;
	}
}
