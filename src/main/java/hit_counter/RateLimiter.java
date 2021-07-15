package hit_counter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RateLimiter {
    Map<String, HitCounter> map = new HashMap<>();
    Lock lock = new ReentrantLock();
    public static void main(String[] args) throws InterruptedException {
        RateLimiter rateLimiter = new RateLimiter();
        Thread t1 = new Thread(() -> {
            for(int i=0;i<10;i++){
                System.out.println("c1::"+rateLimiter.applyLimit("c1"));
            }
        });
        Thread t2 = new Thread(() -> {
            for(int i=0;i<10;i++){
                System.out.println("c1::"+rateLimiter.applyLimit("c1"));
            }
        });
        t1.setName("t1");
        t2.setName("t2");
        t1.start();
        t2.start();

//        System.out.println("c1::"+rateLimiter.applyLimit("c1"));
//        System.out.println("c1::"+rateLimiter.applyLimit("c1"));
//        System.out.println("c1::"+rateLimiter.applyLimit("c1"));
//        System.out.println("c1::"+rateLimiter.applyLimit("c1"));
//        System.out.println("c2::"+rateLimiter.applyLimit("c2"));
//        System.out.println("c2::"+rateLimiter.applyLimit("c2"));
//        System.out.println("c2::"+rateLimiter.applyLimit("c2"));
//        Thread.sleep(500L);
//        System.out.println("c2::"+rateLimiter.applyLimit("c2"));
//        Thread.sleep(1000L);
//        System.out.println("c1::"+rateLimiter.applyLimit("c1"));
    }

    public boolean applyLimit(String clientId){
       try{
           lock.lock();
           System.out.println("lock acquired by::"+Thread.currentThread().getName());
           if(!map.containsKey(clientId)){
               map.put(clientId, new HitCounter());
           }
           Long currentTimeStamp = System.currentTimeMillis();
           return map.get(clientId).isAllowed(currentTimeStamp);
       } finally {
           lock.unlock();
           System.out.println("lock released by::"+Thread.currentThread().getName());
       }

    }
}
