
public class Consumer implements Runnable {

	private Queue queue;

	public Consumer(Queue _queue) {
		this.queue = _queue;
	}
	
	public void consume() {
		
		try {
			this.queue.get();
		} catch (QueueEmptyException e) {
			e.getMessage();
		}
		
	}
	
	@Override
	public void run() {
		

	}

}
