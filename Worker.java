
public abstract class Worker implements Runnable {

	protected Queue queue;

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

	/**
	 * Checks whether work is over
	 * @return boolean true if work is over, false otherwise
	 */
	protected boolean workDone() {
		if (this instanceof Consumer || this instanceof Producer) {
			return this.queue().primeNumbersComputeComplete();
		}
		return false;
	}

	/**
	 * Do some work
	 */
	public abstract void work();

	@Override
	public void run() {
		boolean canWork = true;
		//while(!this.workDone() && canWork) {
			this.work();
			
			try {
				Thread.sleep(Queue.WAIT_TIME);
			} catch (InterruptedException e) {
				canWork = false;
			}
		//}
	}

}
