package multithreading.blocking_queue;

public class BlockingQueue <T> {
    T[] queue = null;
    int size = 0;
    int front = 0;
    int rear = 0;

    BlockingQueue(T[] arr) {
        queue = arr;
    }

    public  void enqueue(T item) throws InterruptedException {
        synchronized (queue){
            while(isFull()){
                queue.wait();
            }
            System.out.println("Thread::"+Thread.currentThread().getName()+" inserted item at index::"+rear);
            rear = ((rear+1)%queue.length == 0 ? 0 : rear+1);
            queue[rear] = item;
            size++;
            queue.notify();
        }
    }

    public  T deQueue() throws InterruptedException {
        T item = null;
        synchronized (queue){
            while(isEmpty()){
              queue.wait();
            }
            System.out.println("Thread::"+Thread.currentThread().getName()+" removed item at index::"+front);
            front = ((front+1) % queue.length) == 0 ? 0:front+1;
            item = queue[front];
            size--;
            queue.notify();
        }
        return item;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private boolean isFull(){
        return size == queue.length-1;
    }
}
