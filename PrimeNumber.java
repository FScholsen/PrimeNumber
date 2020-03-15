import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PrimeNumber {

	/* The number of prime numbers to calculate */
	public static final int PRIME_NUMBER_LIMIT = 1000;

	/* The number of Producer and Consumer Threads to create */
	public static final int NUM_PRODUCERS = 150;
	public static final int NUM_CONSUMERS = 100;

	/* The name of the result file that will hold the prime numbers and the execution time */
	public static final String RESULT_FILE_NAME = "result.txt";

	public static void main(String[] args) {
		/* Start the time counter */
		long start = System.currentTimeMillis();

		/* Clear the result file from any previous result */
		PrimeNumber.writeToFile("", false, PrimeNumber.RESULT_FILE_NAME);

		/* List of Threads for consumers and producers (global variables) */
		List<Thread> producerThreads = new ArrayList<Thread>(NUM_PRODUCERS);
		List<Thread> consumerThreads = new ArrayList<Thread>(NUM_CONSUMERS);

		/* The data structure that will hold the numbers */
		Queue queue;

		try {
			queue = Queue.getQueue(PRIME_NUMBER_LIMIT);

			/* Local variables */
			int i; /* loop counter */

			/* Producer and consumer local variables declaration */
			Producer producer;
			Consumer consumer;

			Thread producerThread;
			Thread consumerThread;

			/**** START THREADS ****/
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
			/* Wait for all Producer Threads to stop */
			i = 0;
			while (i < NUM_PRODUCERS) {
				Thread pt = producerThreads.get(i);
				try {
					pt.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				pt = null;
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
				i++;
			}
			/**** END THREADS ****/
			
			// Print queue to file
			PrimeNumber.writeToFile(queue, true, PrimeNumber.RESULT_FILE_NAME);
			
		} catch (QueueSizeLimitException e) {
			System.err.println("Error: " + e.getMessage());
		}
		queue = null;
		
		// Print execution time to file
		System.out.println(PrimeNumber.elapsedTime(start));
		PrimeNumber.writeToFile(elapsedTime(start), true, PrimeNumber.RESULT_FILE_NAME);
	}

	/**
	 * Returns the string containing execution time
	 * @param long start The time the program started
	 * @return String 
	 */
	public static String elapsedTime(long start) {
		long end = System.currentTimeMillis();
		float timeElapsed = (end - start) / 1000f;
		return "Execution time : " + timeElapsed + " seconds.";
	}

	public static boolean writeToFile(String _text, boolean _append, String filename) {
		BufferedWriter out = null;
		boolean fileAppended = true;
		try {
			FileWriter fstream = new FileWriter(filename, _append);
			out = new BufferedWriter(fstream);
			if (_append)
				_text += "\n";
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

	public static boolean writeToFile(Queue _queue, boolean _append, String filename) {
		BufferedWriter out = null;
		boolean fileAppended = true;
		String lf = null;
		try {
			FileWriter fstream = new FileWriter(filename, _append);
			out = new BufferedWriter(fstream);
			if (_append)
				lf = "\n";
			for (int i = 0; i < _queue.size(); i++) {
				if (_queue.get(i).getPrime()) {
					System.out.println(_queue.get(i).getValue().toString());
					out.write(_queue.get(i).getValue().toString() + lf);
				}

			}
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
