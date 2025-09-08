package TaskManager;


public class Task{
    private String taskName;
    private String taskDescription;
    private boolean isDone;

    public Task(String taskName, boolean isDone) {
        this.taskName = taskName;
        this.isDone=isDone;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

}
