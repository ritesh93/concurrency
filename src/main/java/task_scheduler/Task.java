package task_scheduler;

public class Task{
    private Long startTime;
    private int intervals;
    private int count;

    Task(Long startTime, int intervals, int count){
        this.startTime = startTime;
        this.intervals = intervals;
        this.count = count;
    }
    public  void execute(){
        System.out.println("no implementation");
    }

//    public TaskConfig getConfig(){
//        return config;
//    }

//    public void setConfig(TaskConfig config){
//        this.config = config;
//    }

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
