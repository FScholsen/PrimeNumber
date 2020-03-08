
public class Producer extends Worker implements Runnable {
	
	public Integer minRange;
	public Integer maxRange;
	
	public Producer(Queue _queue) {
		this.setQueue(_queue);
	}
	
	public void produce() {
		try {
			this.getQueue().add(0);
		} catch (QueueFullException e) {
			System.out.println("Queue is full");
		}
	}
	
	@Override
	public void run() {
		boolean canProduce = true;
		while(canProduce) {
			this.produce();
			try {
				this.idle();
				Thread.sleep(Queue.WAIT_TIME);
			} catch (InterruptedException e) {
				canProduce = false;
			}
		}
	}

}
