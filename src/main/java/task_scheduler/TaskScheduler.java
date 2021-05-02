package task_scheduler;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class TaskScheduler {
    private static final int MAX_SIZE = 10;
    TaskProducer producer;
    Queue<Task> queue;
    TaskProcessor processor;
    int numberOfConsumers;
    Thread[] consumers = null;

    TaskScheduler(int n){
        numberOfConsumers = n;
        Comparator<Task> compareBasedOnStratTime = new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                return Long.compare(o1.getStartTime(), o2.getStartTime());
            }
        };
        queue = new PriorityQueue<Task>(MAX_SIZE, compareBasedOnStratTime);
        producer = new TaskProducer(queue);
        processor = new TaskProcessor(queue);
        consumers = new Thread[n];
        schedule();
    }
    public void schedule(){
        for(int i=0;i<numberOfConsumers;i++){
            consumers[i]=new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(10000);
                        processor.processTask();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            consumers[i].start();
        }
    }

    public void submitTask(final Task task){
        Thread t = new Thread(new Runnable() {
            public void run() {
                producer.produceTask(task);
            }
        });
        t.start();
//        producer.produceTask(task);
    }

}
