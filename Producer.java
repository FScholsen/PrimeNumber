
public class Producer implements Runnable {
	
	private Queue queue;

	public Producer(Queue _queue) {
		this.queue = _queue;
	}
	
	public void produce() {
		
		try {
			this.queue.add(1);
		} catch (QueueFullException e) {
			e.getMessage();
		}
	}
	
	@Override
	public void run() {
		

	}

}
