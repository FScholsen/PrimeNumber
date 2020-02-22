import java.util.ArrayList;
import java.util.List;

public class Queue {
	
	private static final int SIZE = 10;
	
	private List<Integer> queue ;
	
	private Object lock;
	
	/* TODO : there is no need to store those current and last as static members */
	private static Integer current = 0;
	
	private static Integer last = 0;
	
	public Queue() {
		if(this.queue == null) {
			this.queue = new ArrayList<Integer>(SIZE);
		}
	}
	
	public void add(Integer _number) throws QueueFullException {
		if(this.queue.size() >= SIZE) {
			throw new QueueFullException();
		}
		/* section critique */
		this.queue.add(_number + Queue.getLast());
		
		Queue.last++;
		/* section critique */
		
	}
	
	public Integer get() throws QueueEmptyException {
		Integer current = null;
		
		try {
			current = this.get(Queue.getCurrent());
			
			/* section critique */
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
