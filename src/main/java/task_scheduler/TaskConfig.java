package task_scheduler;

public class TaskConfig {
    private Long startTime;
    private int intervals;
    private int count;

    public TaskConfig(Long startTime, int intervals, int count){
        this.startTime = startTime;
        this.intervals = intervals;
        this.count = count;
    }

    public Long getStartTime(){
        return startTime;
    }

    public int getIntervals(){
        return intervals;
    }

    public int getCount(){
        return count;
    }
}
