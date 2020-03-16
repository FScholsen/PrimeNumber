
public final class ProducerSieve extends WorkerSieve implements Runnable {

	public ProducerSieve(QueueSieve _queue) {
		super(_queue);
	}

	private void produce() throws QueueFullException {
		
		try {
			/* add a new prime number candidate to the queue */
			// TODO replace write by flag
			this.queue().write();

			//System.out.println("[" + this + "] produces");
		} catch (QueueFullException e) {
			/*System.err.println(e.getMessage());
			
			Thread.yield();
			try {
				Thread.sleep(QueueSieve.WAIT_TIME);
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}*/
			throw e;
		}
	}
	
	@Override
	public void run() {
		boolean canWork = true;
		while(canWork) {
			
			try {
				this.produce();
			} catch (QueueFullException e) {
				canWork = false;
			}
			
			try {
				Thread.sleep(QueueSieve.WAIT_TIME);
			} catch (InterruptedException e) {
				canWork = false;
			}
		}
	}
	
}
