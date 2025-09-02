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

    public void removeTask() {
        showTasks();
        System.out.println("Enter task number to remove: ");
        int index = scanner.nextInt();
        scanner.nextLine(); // czyścimy enter
        if (index > 0 && index <= tasks.size()) {
            Task task = tasks.remove(index - 1);
            task.setDone(true);
            System.out.println("Task completed: " + task.getTaskName());
        } else {
            System.out.println("Invalid index!");
        }
    }

    public void showTasks(){
        int i=0;
        for (Task t : tasks) {
            i++;
            System.out.println(i+" "+t.getTaskName().toUpperCase() + ": " + t.getTaskDescription());
        }
    }
}