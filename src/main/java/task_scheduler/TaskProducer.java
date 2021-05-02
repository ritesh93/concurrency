package task_scheduler;

import java.util.Queue;

public class TaskProducer {
    private int MAX_SIZE = 10;
    private Queue<Task> queue;

    TaskProducer(Queue<Task> queue){
        this.queue = queue;
    }

    public void produceTask(Task task){
        synchronized (queue){
            while(queue.size() == MAX_SIZE){
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("submit task into queue");
            queue.add(task);
            queue.notifyAll();
        }
    }
}
