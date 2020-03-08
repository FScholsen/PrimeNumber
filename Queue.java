import java.util.ArrayList;
import java.util.List;

public class Queue {
	
	/* Not useful (performance killer) */
	public static final int WAIT_TIME = 1000;
	
	private static final int SIZE = 10;
	
	private List<Integer> queue;
	
	private Object lock;
	
	/* TODO : there is no need to store those current and last as static members */
	/* the index of the next element that the consumer will get */
	private static Integer current = 0;
	
	/* the index of the next element that will be added to the queue by the producer */
	private static Integer last = 0;
	
	public Queue() {
		if(this.queue == null) {
			this.queue = new ArrayList<Integer>(SIZE);
		}
	}
	
	public void add(Integer _number) throws QueueFullException {
		//System.out.println(this);
		if(this.queue.size() >= SIZE) {
			throw new QueueFullException();
		}
		/* section critique */
		this.queue.add(_number + Queue.getLast());
		
		Queue.last++;
		/* section critique */
		
	}
	
	public Integer get() throws QueueEmptyException {
		//System.out.println(this);
		
		Integer current = null;
		
		try {
			/* section critique */
			current = this.get(Queue.getCurrent());
			
			
			Queue.current++;
			/* section critique */
			
		} catch (QueueEmptyException e) {
			throw e;
		}
		
		return current;
	}
	
	private Integer get(Integer _number) throws QueueEmptyException {
		if(Queue.getLast() <= Queue.getCurrent()) {
			throw new QueueEmptyException();
		}
		return this.queue.get(_number);
	}
	
	
	/* GETTERS */
	
	/* For debugging purposes */
	public List<Integer> getQueue() {
		return this.queue;
	}
	
	public static Integer getCurrent() {
		return Queue.current;
	}
	
	public static Integer getLast() {
		return Queue.last;
	}
	
}
