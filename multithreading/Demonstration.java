package multithreading;

public class Demonstration {
	private static final int CAPACITY = 5;
	public static void main(String [] args) {
		BlockingQueue<Integer> queue = new BlockingQueue<Integer>(CAPACITY);
		Runnable producer = () -> {
			for(int i=1;i<=10;i++) {
				try {
					queue.enqueue(i);
//					System.out.println("enqued value"+ i);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		
		Runnable consumer = ()->{
			for(int i=1;i<=10;i++) {
				try {
//					System.out.println("dequed value"+ queue.dequeue());
					queue.dequeue();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		
		Thread t1 = new Thread(producer);
		Thread t2 = new Thread(consumer);
		t1.start();
		try {
//			t2.sleep(5000);
			t2.start();
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
