
public final class Consumer extends Worker implements Runnable {

	public Consumer(Queue _queue) {
		super(_queue);
	}
	

	private void consume() {
		Number pnc = null;
		try {
			/* retrieve a prime number candidate */
			/* TODO Maybe try to insert the isPrime logic in the read() method of Queue, inside of the synchronized lock */
			pnc = this.queue().read();
			/* try to flag the number as prime */
			System.out.println(pnc.getValue() + " => " + pnc.isPrime());
			if (pnc.isPrime()) {
				pnc.setPrime(true);
				this.queue().incrementPrimeNumbersFound();
			}
			
			// writeToFile
		} catch (QueueEmptyException e) {
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
			this.consume();
			
			try {
				Thread.sleep(Queue.WAIT_TIME);
			} catch (InterruptedException e) {
				canWork = false;
			}
		}
	}
}
