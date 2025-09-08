package TaskManager;

import java.util.ArrayList;



public class TaskManager  {
    private final ArrayList<Task> tasks;

    public TaskManager() {
        tasks = new ArrayList<Task>();
    }

    public void addTask(String taskName) {
        tasks.add(new Task(taskName, false));
    }

    public void removeTask(int index) {
        if (index > 0 && index <= tasks.size()) {
            Task task = tasks.remove(index - 1);
            task.setDone(true);
        }
    }
}