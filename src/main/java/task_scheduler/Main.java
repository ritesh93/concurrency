package task_scheduler;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class Main {
    public static void main(String[] args){

//        Queue<Task> queue = new PriorityQueue<Task>(3, new Comparator<Task>() {
//            @Override
//            public int compare(Task o1, Task o2) {
//                return Long.compare(o1.getStartTime(), o2.getStartTime());
//            }
//        });
//        queue.add(new Task(100l, 1,1));
//        queue.add(new Task(200l, 1,1));
//        while(!queue.isEmpty()){
//            Task task = queue.poll();
//            System.out.println(task.getStartTime());
//        }
        TaskScheduler scheduler = new TaskScheduler(2);
//        for(int i=0;i<10;i++){
            scheduler.submitTask(new DummyTaskImpl(1619959943000l,2,2));
            scheduler.submitTask(new DummyTaskImpl(1619969943000l,2,1));
//        }
    }
}
