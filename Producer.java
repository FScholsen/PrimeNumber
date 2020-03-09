
public class Producer extends Worker implements Runnable {

	private boolean finishedWork = false;
	private boolean finishedSegment = false;

	// private Integer minRange;
	private Integer maxRange;

	private Integer currentIndex;

	public Producer(Queue _queue, Integer _minRange, Integer _maxRange) {
		super(_queue);
		// this.minRange = _minRange;
		this.maxRange = _maxRange;
		this.currentIndex = _minRange;
	}

	public void produce() {
		if (!this.hasFinishedSegment()) {
			if (isPrime(currentIndex)) {
				try {
					this.getQueue().add(currentIndex);
				} catch (QueueFullException e) {
					System.out.println("Queue is full");

					Thread.yield();
					try {
						Thread.sleep(Queue.WAIT_TIME);
					} catch (InterruptedException ie) {
						ie.printStackTrace();
					}
				}
			}
			this.currentIndex++;
		} else {
			this.finishedWork = true;
			// System.out.println(this + " stopped at " + this.currentIndex);
		}
	}

	@Override
	public void run() {
		boolean canProduce = true;
		while (canProduce && !this.finishedWork && this.canProduce()) {
			this.produce();
			try {
				Thread.sleep(Queue.WAIT_TIME);
			} catch (InterruptedException e) {
				canProduce = false;
			}
		}
		//System.out.println(this + " stopped at " + this.currentIndex + ". can produce : " + this.canProduce());
	}

	public boolean canProduce() {
		return this.queue.isWritable();
	}
	
	public boolean hasFinishedSegment() {
		return this.currentIndex >= this.maxRange;
	}
	
	public boolean hasFinishedWork() {
		return this.hasFinishedWork() && this.queue.isWritable(); 
	}

}
