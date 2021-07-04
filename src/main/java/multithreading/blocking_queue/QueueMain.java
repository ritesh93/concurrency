package multithreading.blocking_queue;

import java.sql.SQLOutput;

public class QueueMain {
    public static void main(String[] args) throws InterruptedException {
        Integer[] arr = new Integer[5];
        BlockingQueue<Integer> queue = new BlockingQueue<Integer>(arr);
        Thread consumer = new Thread(() -> {
                try {
                    while(!queue.isEmpty()){
                        System.out.println(queue.deQueue());
                    }
                    System.out.println("queue is empty");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        });

        Thread producer = new Thread(() -> {
            for(int i=0;i<10;i++){
                try {
                    queue.enqueue(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        producer.start();
        Thread.sleep(1000);
        consumer.start();
        consumer.join();
        producer.join();
    }
}
