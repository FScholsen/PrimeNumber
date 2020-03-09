import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Consumer extends Worker implements Runnable {

	private String resultFileName;
	
	private Object fileLock = new Object();

	public Consumer(Queue _queue, String _fileName) {
		super(_queue);
		this.resultFileName = _fileName;
	}

	public void consume() {
		Integer pn = null;
		boolean writeComplete = false;
		try {
			pn = this.getQueue().get();
			this.writeToFile(pn);
			/*
			while (writeComplete) {
				synchronized (this.fileLock) {
					writeComplete = this.writeToFile(pn);
				}
			}*/
		} catch (QueueEmptyException e) {
			System.out.println("Queue is empty");

			Thread.yield();
			try {
				Thread.sleep(Queue.WAIT_TIME);
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
		}
	}

	@Override
	public void run() {
		boolean canConsume = true;
		while (canConsume && this.canConsume() && !this.finished()) {
		//while (canConsume) {
			this.consume();
			try {
				Thread.sleep(Queue.WAIT_TIME);
			} catch (InterruptedException e) {
				// e.printStackTrace();
				canConsume = false;
			}
		}
		//System.out.println(this + " can consume : " + this.canConsume());
	}

	public boolean canConsume() {
		return this.queue.isReadable();
	}

	public boolean finished() {
		return !this.canConsume() && !this.queue.isWritable();
	}

	public boolean writeToFile(Integer _primeNumber) {
		boolean queueAppended = true;

		BufferedWriter out = null;

		try {
			FileWriter fstream = new FileWriter(this.resultFileName, true);
			out = new BufferedWriter(fstream);
			out.write(_primeNumber + "\n");
		} catch (IOException e) {
			queueAppended = false;
			System.err.println("Error: " + e.getMessage());
		}

		finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					queueAppended = false;
					e.printStackTrace();
				}
			}
		}

		return queueAppended;
	}

}
