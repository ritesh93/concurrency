package hit_counter;

import java.util.LinkedList;
import java.util.Queue;

class HitCounter {
    Queue<Long> queue = null;
    private static final int UPPER_LIMIT = 3;
    int count;
    Long timeUnit = 500L;

    HitCounter(){
        queue = new LinkedList<>();
    }

    boolean isAllowed(Long timeStamp){
        while(!queue.isEmpty() && queue.peek() < timeStamp - timeUnit){
            queue.poll();
        }
        if(queue.size() < UPPER_LIMIT){
            queue.add(timeStamp);
            System.out.println("request allowed::"+queue.size());
            return true;
        }
        System.out.println("request not allowed::"+queue.size());
        return false;
    }
}
