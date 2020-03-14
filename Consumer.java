
public final class Consumer extends Worker {

	public Consumer(Queue _queue) {
		super(_queue);
	}

	public void work() {
		// System.out.println("Consumer working");
		this.consume();
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
}
