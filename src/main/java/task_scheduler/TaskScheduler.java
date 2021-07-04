package task_scheduler;

import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class TaskScheduler {
    public static void main(String[] args) throws InterruptedException {
        Task task1 = new Task(System.currentTimeMillis() + 10000, 2, "task1", "task1");
        Task task2 = new Task(System.currentTimeMillis() + 20000, 1, "task2", "task2");
        final List<Task> taskList = Arrays.asList(task1, task2);
        TaskSchedulerQueue queue = new TaskSchedulerQueue();
        Thread producer = new Thread(() -> {
            for(Task task : taskList){
                try {
                    System.out.println("adding task");
                    queue.enQueue(task);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread consumer = new Thread(() -> {
           while(!queue.isEmpty()){
               try {
                   System.out.println("pulling task from queue");
                   queue.deQueue();
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
        });

        producer.start();
        Thread.sleep(1000);
        consumer.start();
        producer.join();
        consumer.join();
    }
}
