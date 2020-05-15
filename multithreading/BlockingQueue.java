package multithreading;

public class BlockingQueue<T> {

	T[] array;
	int size=0;
	int capacity;
	int head = 0;
	int tail =0;
	
	public BlockingQueue(int capacity) {
		this.capacity = capacity;
		array = (T[]) new Object[capacity];
	}
	
	public synchronized void enqueue(T item) throws InterruptedException {
		while(size == capacity) {
			wait();
		}
		if(tail == capacity) {
			tail =0;
		}
		array[tail] = item;
		System.out.println("enqued data "+item);
		tail++;
		size++;
		notify();
	}
	
	public synchronized T dequeue() throws InterruptedException {
		while(size == 0) {
			wait();
		}
		if(head == capacity) {
			head =0;
			tail=0;
		}
		T item = array[head];
		System.out.println("dequed data "+item);
		head++;
		size--;
		notify();
		return item;
	}
}
