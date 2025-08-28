package TaskManager;

import java.util.ArrayList;
import java.util.Scanner;


public class TaskManager  {
    private ArrayList<Task> tasks;
    private ArrayList<Task> doneTasks;
    Scanner scanner = new Scanner(System.in);

    public TaskManager() {
        tasks = new ArrayList<Task>();
        doneTasks = new ArrayList<Task>();
    }

    public void addTask( ) {
        System.out.println("Enter Task Name: ");
        String taskName= scanner.nextLine();
        System.out.println("Enter Task Description: ");
        String taskDescription= scanner.nextLine();
        tasks.add(new Task(taskName, taskDescription, false));
    }

    public void removeTask() {
        showTasks();
        System.out.println("Enter task number to remove: ");
        int index = scanner.nextInt();
        scanner.nextLine(); // czyścimy enter
        if (index > 0 && index <= tasks.size()) {
            Task task = tasks.remove(index - 1);
            task.setDone(true);
            doneTasks.add(task);
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
    public void showDoneTasks(){
        int i=0;
        for (Task t : doneTasks) {
            i++;
            System.out.println(i+" "+t.getTaskName().toUpperCase() + ": " + t.getTaskDescription());
        }
    }
}