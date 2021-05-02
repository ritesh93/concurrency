package concurrency_practise;

import java.util.Arrays;
import java.util.List;

public class ThreadCreation {

    public static void main(String[] args) throws InterruptedException {
        List<Integer> list = Arrays.asList(1,2,3);
        Integer[] arr = list.stream().toArray(Integer[] :: new);
//        Thread t = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for(int i=0;i<5;i++) {
//                    System.out.println(Thread.currentThread().getId() + "::"+ i);
//                }
//            }
//        });
//        System.out.println("inside main 1");
//        Thread t = new Thread(() -> {
//            for(int i=0;i<5;i++)
//            { System.out.println(Thread.currentThread().getId() + "::"+ i); }
//        });
//
//        t.start();
////        t.setDaemon(true);
////        t.join();
//        System.out.println("inside main last");
    }
}
