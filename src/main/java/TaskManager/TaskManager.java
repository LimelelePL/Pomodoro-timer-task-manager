package TaskManager;

import java.util.ArrayList;
import java.util.Scanner;


public class TaskManager  {
    private ArrayList<Task> tasks;
    Scanner scanner = new Scanner(System.in);

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