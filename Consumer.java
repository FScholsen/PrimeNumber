
public final class Consumer extends Worker implements Runnable {

	public Consumer(Queue _queue) {
		super(_queue);
	}
	

	private void consume() throws QueueFoundNumbersException {
		Number pnc = null;
		try {
			/* retrieve a prime number candidate */
			pnc = this.queue().read();
			System.out.println("[" + this + "]" + "\t#[" + pnc + " - " + pnc.getValue() + "] => prime : " + pnc.getPrime());
			// writeToFile
			/*if (pnc.getPrime()) {
				PrimeNumber.writeToFile(pnc.getValue().toString(), true);
			}*/
		} catch (QueueEmptyException e) {
			System.err.println(e.getMessage());

			Thread.yield();
			try {
				Thread.sleep(Queue.WAIT_TIME);
			} catch (InterruptedException ie) {
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
