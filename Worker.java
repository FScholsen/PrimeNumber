
public class Worker {

	protected Queue queue;

	public Worker(Queue _queue) {
		this.setQueue(_queue);
	}

	public Queue getQueue() {
		return queue;
	}

	public void setQueue(Queue queue) {
		this.queue = queue;
	}

	public String idle() {
		return this + " is waiting";
	}

	public static boolean isPrime(int _number) {
		int i;
		int number = _number;

		if (number <= 1)
			return false;

		for (i = 2; i * i <= number; i++) {
			if (number % i == 0)
				return false;
		}

		return true;
	}
}
