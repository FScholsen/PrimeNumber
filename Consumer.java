
public final class Consumer extends Worker implements Runnable {

	public Consumer(Queue _queue) {
		super(_queue);
	}
	

	private void consume() throws QueueFoundNumbersException {
		try {
			/* flag a prime number candidate as prime, or not */
			Number pnc = this.queue().read();
			//System.out.println(this + "\tconsumes" + "\t" + pnc.getValue() + " - " + (pnc.getPrime()? "Prime" : "") );
		} catch (QueueEmptyException e) {
			System.err.println(e.getMessage());

			Thread.yield();
			try {
				Thread.sleep(Queue.WAIT_TIME);
			} catch (InterruptedException ie) {
				// TODO maybe try to thorw this exception again to stop the current thread
				ie.printStackTrace();
			}
		}catch (QueueFoundNumbersException qfne) {
			throw qfne;
		}
	}
	
	@Override
	public void run() {
		boolean canWork = true;
		while(canWork) {
			try {
				this.consume();
			} catch (QueueFoundNumbersException e) {
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
