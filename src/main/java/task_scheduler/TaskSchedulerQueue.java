package task_scheduler;

import java.util.PriorityQueue;
import java.util.Queue;

public class TaskSchedulerQueue {

    Queue<Task> taskSchedulerQ = null;
    private static final int MAX_CAPACITY = 10;

    TaskSchedulerQueue() {
        taskSchedulerQ = new PriorityQueue<>((a,b) -> Long.compare(a.getStartTime(), b.getStartTime()));
    }

    public void enQueue(Task item) throws InterruptedException {
        synchronized (taskSchedulerQ) {
            while (taskSchedulerQ.size() == MAX_CAPACITY){
                System.out.println("queue is full");
                taskSchedulerQ.wait();
            }
            System.out.println("adding task into the queue::"+ item.toString());
            taskSchedulerQ.offer(item);
            taskSchedulerQ.notify();
        }
    }

    public void deQueue() throws InterruptedException {
        Task task = null;
        synchronized (taskSchedulerQ) {
            while (isEmpty() || !anyTaskAvailable(System.currentTimeMillis())) {
                System.out.println("queue doesnt have any eligible task consumer going to sleep state");
                Thread.sleep(1000);
            }
            task = taskSchedulerQ.poll();
            System.out.println("pulled task out of queue::" + task.toString());
            task.action();
            if(task.getCounter() > 1){
                task.setCounter(task.getCounter() - 1);
                task.setStartTime(System.currentTimeMillis());
                System.out.println("executed task::" + task);
                taskSchedulerQ.offer(task);
            }
        }
    }

    public boolean isEmpty() {
        return taskSchedulerQ.isEmpty();
    }


    private boolean anyTaskAvailable(Long time) {
        if (taskSchedulerQ.peek().getStartTime() <= time) {
            return true;
        }
        return false;
    }
}
