
public final class ConsumerSieve extends WorkerSieve implements Runnable {

	public ConsumerSieve(QueueSieve _queue) {
		super(_queue);
	}
	

	private void consume() throws QueueFoundNumbersException, QueueReadLimitException {
		try {
			/* flag a prime number candidate as prime, or not */
			int pnc = this.queue().read();
			//System.out.println(this + "\tconsumes" + "\t" + pnc.getValue() + " - " + (pnc.getPrime()? "Prime" : "") );
		} catch (QueueEmptyException e) {
			System.err.println(e.getMessage());

			Thread.yield();
			try {
				Thread.sleep(QueueSieve.WAIT_TIME);
			} catch (InterruptedException ie) {
				// TODO maybe try to throw this exception again to stop the current thread execution
				ie.printStackTrace();
			}
		}catch (QueueFoundNumbersException qfne) {
			throw qfne;
		} catch (QueueReadLimitException qrle) {
			throw qrle;
		}
	}
	
	@Override
	public void run() {
		boolean canWork = true;
		while(canWork) {
			try {
				this.consume();
			} catch (QueueFoundNumbersException | QueueReadLimitException e) { 
				// consume while all the prime numbers wanted (1000) haven't been found or the whole queen has been read
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
