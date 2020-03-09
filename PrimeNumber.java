import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PrimeNumber {

	/* constant The number of prime numbers to calculate */
	public static final int PRIME_NUMBER_LIMIT = 1000;

	/* constants for Producer & Consumer Threads */
	public static final int NUM_PRODUCERS = 250;
	public static final int NUM_CONSUMERS = 100;

	public static final String RESULT_FILE_NAME = "result.txt";

	public static void main(String[] args) {

		long start = System.currentTimeMillis();

		writeToFile("", false);

		/* List of Threads for consumers and producers (global variables) */
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
			producerThread = new Thread(producer);
			producerThreads.add(producerThread);
			segmentCounter += segmentSize;
			i++;
		}

		/* Consumer Threads initialization */
		i = 0;
		while (i < NUM_CONSUMERS) {
			consumer = new Consumer(queue, RESULT_FILE_NAME);
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
			//System.out.println(pt + " : " + pt.getState());
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
			//System.out.println(ct + " : " + ct.getState());
			i++;
		}
		
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
			FileWriter fstream = new FileWriter(RESULT_FILE_NAME, _append); // true tells to append data.
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
