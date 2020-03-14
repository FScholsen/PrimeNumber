
public final class Producer extends Worker implements Runnable {

	public Producer(Queue _queue) {
		super(_queue);
	}

	private void produce() throws QueueFoundNumbersException, QueueFullException {
		
		try {
			this.queue().write(new Number(this.queue().getWriteCursor()));
			System.out.println("[" + this + "] produces");
		} catch (QueueFullException e) {
			System.err.println(e.getMessage());
			
			Thread.yield();
			try {
				Thread.sleep(Queue.WAIT_TIME);
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
			throw e;
		} catch (QueueFoundNumbersException qfne) {
			throw qfne;
		}
	}
	
	@Override
	public void run() {
		boolean canWork = true;
		while(canWork) {
			
			try {
				this.produce();
			} catch (QueueFoundNumbersException | QueueFullException e) {
				canWork = false;
			}
			
			try {
				Thread.sleep(Queue.WAIT_TIME);
			} catch (InterruptedException e) {
				canWork = false;
			}
		}
	}
	
}
