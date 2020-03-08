
public class Worker {
	
	protected Queue queue;

	public Queue getQueue() {
		return queue;
	}

	public void setQueue(Queue queue) {
		this.queue = queue;
	}
	
	public void idle() {
		System.out.println("Waiting ... ");
	}

}
