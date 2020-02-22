
public class PrimeNumber {

	public static void main(String[] args) {
		
		/* constants for Producer & Consumer Threads */
		final int NUM_PRODUCERS = 4;
		final int NUM_CONSUMERS = 1;
		
		/* The data structure that will hold the numbers flagged as primes by the Producer Threads */
		Queue queue = new Queue();
		
		/* Producer Threads initialization */
		Producer producer = new Producer(queue);
		
		/* Consumer Threads initialization */
		Consumer consumer = new Consumer(queue);
		
		
		try {
			System.out.println(queue.get());
		} catch (QueueEmptyException e1) {
			//e1.getMessage();
			System.out.println("File vide");
		}
		
		int i = 0;
		while(i < 10) {

			producer.produce();
			
			try {
				System.out.println(queue.get());
			} catch (QueueEmptyException e) {
				//e.getMessage();
				System.out.println("Case vide");
			}
			
			i++;
		}
		
		
		try {
			System.out.println(queue.getQueue());
		} catch (Exception e) {
			System.out.println("File vide");
		}
		
		
		
	}

}
