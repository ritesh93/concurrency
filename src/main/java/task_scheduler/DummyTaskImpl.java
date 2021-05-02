package task_scheduler;

public class DummyTaskImpl extends Task{
//    private TaskConfig config;

    DummyTaskImpl(Long startTime, int interval, int count){
        super(startTime, interval, count);
    }
    public void execute() {
        System.out.println("dummy task");
    }
}
