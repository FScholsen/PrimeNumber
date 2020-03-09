import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PrimeNumber {

	public static final int PRIME_NUMBER_LIMIT = 1000;

	public static final int NUM_PRODUCERS = 100;

	public static final int NUM_CONSUMERS = 1;

	public static void main(String[] args) {

		long start = System.currentTimeMillis();

		/* constants for Producer & Consumer Threads */

		final int NUM_CONSUMERS = 1;

		/* List of consumers and producers (global variables) */
		List<Producer> producers = new ArrayList<Producer>(NUM_PRODUCERS);
		List<Consumer> consumers = new ArrayList<Consumer>(NUM_CONSUMERS);

		List<Thread> producerThreads = new ArrayList<Thread>(NUM_PRODUCERS);
		List<Thread> consumerThreads = new ArrayList<Thread>(NUM_CONSUMERS);

		/*
		 * The data structure that will hold the numbers flagged as primes by the
		 * Producer Threads
		 */
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
		int segmentSize = (PRIME_NUMBER_LIMIT / NUM_PRODUCERS), segmentCounter = 0;

		while (i < NUM_PRODUCERS) {
			int segmentMinLimit = segmentCounter + 1;
			int segmentMaxLimit = segmentCounter + segmentSize;

			producer = new Producer(queue, segmentMinLimit, segmentMaxLimit);

			producers.add(producer);

			producerThread = new Thread(producer);

			System.out.println("[Producer #" + i + "] : " + segmentMinLimit + " - " + segmentMaxLimit + " in "
					+ producerThread + " : " + producerThread.getState());

			producerThreads.add(producerThread);

			segmentCounter += segmentSize;
			i++;
		}

		/* Consumer Threads initialization */
		i = 0;
		while (i < NUM_CONSUMERS) {
			consumer = new Consumer(queue);

			consumers.add(consumer);

			consumerThread = new Thread(consumer);

			consumerThreads.add(consumerThread);

			i++;
		}

		/* Start all Producer Threads */
		i = 0;
		while (i < NUM_PRODUCERS) {
			producerThreads.get(i).start();
			i++;
		}

		/* Stop all Producer Threads */
		i = 0;
		while (i < NUM_PRODUCERS) {
			Thread pt = producerThreads.get(i);

			try {
				pt.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(pt + " : " + pt.getState());
			i++;
		}

		/* Start all Consumer Threads */
		/*
		 * i = 0; while (i < NUM_CONSUMERS) { consumerThreads.get(i).start(); i++; }
		 */

		/* Wait */
		/*
		 * try { TimeUnit.MILLISECONDS.sleep(2000); } catch (InterruptedException e) {
		 * e.printStackTrace(); }
		 */
		printQueue(queue);
		System.out.println(queue.getQueue().size());
		printElapsedTime(start);
	}

	public static void printQueue(Queue queue) {
		/* Display queue content for debug purposes */
		try {
			System.out.println(queue.getQueue());
		} catch (Exception e) {
			System.out.println("File vide");
		}
	}

	public static void printElapsedTime(long start) {
		long end = System.currentTimeMillis();
		float timeElapsed = (end - start) / 1000f;
		System.out.println("Execution time : " + timeElapsed + " seconds.");
	}

}
