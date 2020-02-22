
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
		
		
		consumer.consume();
		
		int i = 0;
		while(i < 12) {

			producer.produce();
			//System.out.println(Queue.getCurrent());
			//System.out.println(Queue.getLast());
			i++;
		}
		
		
		try {
			System.out.println(queue.getQueue());
		} catch (Exception e) {
			System.out.println("File vide");
		}
		
		
		i = 0;
		while(i < 15) {

			consumer.consume();
			//System.out.println(Queue.getCurrent());
			//System.out.println(Queue.getLast());
			i++;
		}
	
		
		
	}

}
