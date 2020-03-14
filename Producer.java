
public final class Producer extends Worker {

	public Producer(Queue _queue) {
		super(_queue);
	}

	public void work() {
		//System.out.println("Producer working");
		this.produce();
	}
	
	private void produce() {
		
		Number number = new Number(this.queue().getWriteCursor());
		
		try {
			this.queue().write(number);
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
	
}
