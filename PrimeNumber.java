import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PrimeNumber {

	public static void main(String[] args) {
		
		long start = System.currentTimeMillis();
		
		/* constants for Producer & Consumer Threads */
		final int NUM_PRODUCERS = 4;
		final int NUM_CONSUMERS = 1;
		
		/* List of consumers and producers (global variables) */
		List<Producer> producers = new ArrayList<Producer>(NUM_PRODUCERS);
		List<Consumer> consumers = new ArrayList<Consumer>(NUM_CONSUMERS);
		
		List<Thread> producerThreads = new ArrayList<Thread>(NUM_PRODUCERS);
		List<Thread> consumerThreads = new ArrayList<Thread>(NUM_CONSUMERS);
		
		/* The data structure that will hold the numbers flagged as primes by the Producer Threads */
		Queue queue = new Queue();
		
		/* Local variables */
		int i; /* loop counter */
		
		/* Producer and consumer local variables declaration */
		Producer producer;
		Consumer consumer;
		
		Thread producerThread;
		Thread consumerThread;
		
		
		/* Producer Threads initialization */
		i = 0;
		while(i < NUM_PRODUCERS) {
			producer = new Producer(queue);
			
			producers.add(producer);
			
			producerThread = new Thread(producer);
			
			producerThreads.add(producerThread);

			i++;
		}
		
		/* Consumer Threads initialization */
		i = 0;
		while(i < NUM_CONSUMERS) {
			consumer = new Consumer(queue);
			
			consumers.add(consumer);
			
			consumerThread = new Thread(consumer);
			
			consumerThreads.add(consumerThread);
			
			i++;
		}
		
		printQueue(queue);
		
		
		/* Start all Producer Threads */
		/*i = 0;
		while(i < NUM_PRODUCERS) {
			producerThreads.get(i).start();
			i++;
		}
		*/
		
		/* Start all Consumer Threads */
		/*i = 0;
		while(i < NUM_CONSUMERS) {
			consumerThreads.get(i).start();
			i++;
		}*/
	
		try {
			TimeUnit.MILLISECONDS.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		printElapsedTime(start);
	}
	

	static void printQueue(Queue queue) {
		/* Display queue content for debug purposes */
		try {
			System.out.println(queue.getQueue());
		} catch (Exception e) {
			System.out.println("File vide");
		}
	}
	
	static void printElapsedTime(long start) {
		long end = System.currentTimeMillis(); 
		float timeElapsed = (end - start) / 1000f;
		System.out.println("Execution time : " + timeElapsed + " seconds.");
	}
}
