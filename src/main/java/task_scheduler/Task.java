package task_scheduler;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Task {
    private Long startTime;
    private int counter;
    private String taskName;
    private String taskDescription;

    public void action(){
        System.out.println("Executing task with taskname "+ taskName+"::"+counter+" times");
    }
}
