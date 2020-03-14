
public final class Producer extends Worker implements Runnable {

	public Producer(Queue _queue) {
		super(_queue);
	}

	private void produce() {
		
		try {
			this.queue().write(new Number(this.queue().getWriteCursor()));
		} catch (QueueFullException e) {
			System.err.println(e.getMessage());

			Thread.yield();
			try {
				Thread.sleep(Queue.WAIT_TIME);
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
		}
	}
	
	@Override
	public void run() {
		boolean canWork = true;
		while(!this.queue().primeNumbersFound() && canWork) {
			this.produce();
			
			try {
				Thread.sleep(Queue.WAIT_TIME);
			} catch (InterruptedException e) {
				canWork = false;
			}
		}
	}
	
}
