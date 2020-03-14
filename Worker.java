
public abstract class Worker {

	private Queue queue;

	public Worker(Queue _queue) {
		this.setQueue(_queue);
	}

	protected Queue queue() {
		return queue;
	}

	private void setQueue(Queue queue) {
		this.queue = queue;
	}

	public String idle() {
		return this + " is waiting";
	}

}
