
public class Consumer extends Worker implements Runnable {

	public Consumer(Queue _queue) {
		super(_queue);
	}

	public void consume() {
		try {
			this.getQueue().get();
		} catch (QueueEmptyException e) {
			System.out.println("Queue is empty");
		}
	}

	@Override
	public void run() {
		boolean canConsume = true;
		while (canConsume) {
			this.consume();
			try {
				System.out.println(this.idle());
				Thread.sleep(Queue.WAIT_TIME);
			} catch (InterruptedException e) {
				// e.printStackTrace();
				canConsume = false;
			}
		}
	}

}
