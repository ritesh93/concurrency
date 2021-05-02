package task_scheduler;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Queue;

public class TaskProcessor {
    private final Queue<Task> queue;

    TaskProcessor(Queue<Task> queue){
        this.queue = queue;
    }

    public void processTask() throws InterruptedException {
        synchronized (queue){
            while(queue.isEmpty()){
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("processing task");
            long currentTime = Instant.now().toEpochMilli();
            Task task = queue.poll();
            System.out.println("start time"+task.getStartTime());
            while(currentTime < task.getStartTime()){
                System.out.println("current time less than start time waiting");
                Thread.sleep(1000);
            }
            task.execute();
        }
    }
}
