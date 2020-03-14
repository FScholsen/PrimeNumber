import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PrimeNumber {

	/* constant The number of prime numbers to calculate */
	public static final int PRIME_NUMBER_LIMIT = 10;

	/* constants for Producer & Consumer Threads */
	public static final int NUM_PRODUCERS = 100;
	public static final int NUM_CONSUMERS = 10;

	public static final String RESULT_FILE_NAME = "result.txt";

	public static void main(String[] args) {
		/* Start the time counter */
		long start = System.currentTimeMillis();

		/* Clear the result file from any previous result */
		writeToFile("", false);

		/* List of Threads for consumers and producers (global variables) */
		List<Thread> producerThreads = new ArrayList<Thread>(NUM_PRODUCERS);
		List<Thread> consumerThreads = new ArrayList<Thread>(NUM_CONSUMERS);

		/* The data structure that will hold the numbers */
		Queue queue;

		try {
			queue = new Queue(PRIME_NUMBER_LIMIT);

			/* Local variables */
			int i; /* loop counter */

			/* Producer and consumer local variables declaration */
			Producer producer;
			Consumer consumer;

			Thread producerThread;
			Thread consumerThread;

			/* START THREADS */
			/* Producer Threads initialization */
			i = 0;
			while (i < NUM_PRODUCERS) {
				producer = new Producer(queue);
				producerThread = new Thread(producer);
				producerThreads.add(producerThread);
				i++;
			}

			/* Consumer Threads initialization */
			i = 0;
			while (i < NUM_CONSUMERS) {
				consumer = new Consumer(queue);
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

			/* Start all Consumer Threads */
			i = 0;
			while (i < NUM_CONSUMERS) {
				consumerThreads.get(i).start();
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
				pt = null;
				// System.out.println(pt + " : " + pt.getState());
				i++;
			}

			/* Stop all Consumer Threads */
			i = 0;
			while (i < NUM_CONSUMERS) {
				Thread ct = consumerThreads.get(i);

				try {
					ct.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				ct = null;
				// System.out.println(ct + " : " + ct.getState());
				i++;
			}

			/* END THREADS */

			// Print queue to file
			int printed = 0;
			for (i = 0; i < queue.size(); i++) {
				Number number = queue.get(i);
				
				if (number.isPrime()) {
					boolean result = PrimeNumber.writeToFile(number.getValue().toString(), true);
					if (!result) {
						System.out.println("Failed printing to result file");
					} else {
						printed++;
					}
					
				}
				
			}

			System.out.println("Printed " + printed + " numbers.");
			// Print execution time

		} catch (QueueSizeLimitException e) {
			System.err.println("Error: " + e.getMessage());
		}
		queue = null;
		System.out.println(elapsedTime(start));
		writeToFile(elapsedTime(start), true);
	}

	public static String elapsedTime(long start) {
		long end = System.currentTimeMillis();
		float timeElapsed = (end - start) / 1000f;
		return "Execution time : " + timeElapsed + " seconds.";
	}

	public static boolean writeToFile(String _text, boolean _append) {
		BufferedWriter out = null;
		boolean fileAppended = true;
		try {
			FileWriter fstream = new FileWriter(RESULT_FILE_NAME, _append);
			out = new BufferedWriter(fstream);
			out.write(_text);
		} catch (IOException e) {
			fileAppended = false;
			System.err.println("Error: " + e.getMessage());
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					fileAppended = false;
					e.printStackTrace();
				}
			}
		}
		return fileAppended;
	}

}
