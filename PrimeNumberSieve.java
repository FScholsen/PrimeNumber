import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PrimeNumberSieve {

	/* The number of prime numbers to calculate */
	public static final int PRIME_NUMBER_LIMIT = 1000;

	/* The number of Producer and Consumer Threads to create */
	public static final int NUM_PRODUCERS = 150;
	public static final int NUM_CONSUMERS = 100;

	/* The name of the result file that will hold the prime numbers and the execution time */
	public static final String RESULT_FILE_NAME = "result_final_sieve.txt";
	
	public static final int NUMBER_LIMIT = 7920;
	
	public static void main(String[] args) {
		/* Start the time counter */
		long start = System.currentTimeMillis();
		
		/* Clear the result file from any previous result */
		PrimeNumber.writeToFile("", false, PrimeNumber.RESULT_FILE_NAME);

		/* List of Threads for consumers and producers (global variables) */
		List<Thread> pts = new ArrayList<Thread>();
		List<Thread> cts = new ArrayList<Thread>();
		
		/* The data structure that will hold the numbers */
		QueueSieve queue;

		QueueSieve.setNumbersToAnalyze(NUMBER_LIMIT);
		

		try {
			queue = QueueSieve.getQueue();
			
			if(queue != null) {
				/* Local variables */
				int i; /* loop counter */

				/* Producer and consumer local variables declaration */
				ProducerSieve p;
				ConsumerSieve c;

				Thread pt;
				Thread ct;

				for(i = 0; i < queue.getFactorLimit(); i++) {
					p = new ProducerSieve(queue);
					pt = new Thread(p);
					pts.add(pt);
					pt.start();
				}
				
				/* Wait for all Producer Threads to stop */
				for(i = 0; i < queue.getFactorLimit(); i++) {
					pt = pts.get(i);
					try {
						pt.join();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					pt = null;
				}
				
				for(i = 2; i < QueueSieve.getNumbersToAnalyze(); i++) {
					//queue.read();
					int n = queue.getNumber(i);
					if(n < QueueSieve.NON_PRIME) System.out.println(i);
					//System.out.println(i + " => " + (n < QueueSieve.NON_PRIME ? "\tprime": ""));
				}
				
				//System.out.println(QueueSieve.getQueue());
				
				p = null;
				c = null;

				ct = null;
				
			}
			
		
		} catch (QueueSizeLimitException e) {
			System.err.println("Error: " + e.getMessage());
		}
		
		/* Empty global variables */
		queue = null;
		pts = null;
		cts = null;
		
		// Print execution time to file
		System.out.println(PrimeNumber.elapsedTime(start));
	}
	
	public static boolean writeToFile(QueueSieve _queue, boolean _append, String filename) {
		BufferedWriter out = null;
		boolean fileAppended = true;
		String lf = null;
		try {
			FileWriter fstream = new FileWriter(filename, _append);
			out = new BufferedWriter(fstream);
			if (_append)
				lf = "\n";
			for (int i = 0; i < _queue.getNumbers().length; i++) {
				/*if (_queue.getNumber(i).getPrime()) {
					System.out.println(_queue.get(i).getValue().toString());
					out.write(_queue.get(i).getValue().toString() + lf);
				}*/

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
