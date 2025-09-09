package TaskManager;

import java.io.*;
import java.util.ArrayList;



public class TaskManager  {
    private final ArrayList<Task> tasks;

    public TaskManager() {
        tasks = new ArrayList<Task>();
        loadTasksFromFile("src/main/java/TaskManager/tasks.txt");
    }

    public void addTask(String taskName) {
        Task task = new Task(taskName, false);
        tasks.add(task);
        saveTaskToFile("src/main/java/TaskManager/tasks.txt", task);
    }

    public void removeTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.remove(index);
            loadAllTaskToFile("src/main/java/TaskManager/tasks.txt");
        }
    }

    public void saveTaskToFile(String filename,  Task task) {
        try {
            FileWriter file = new FileWriter(filename, true);
            BufferedWriter writer = new BufferedWriter(file);
            writer.write(task.getTaskName() + ";");
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadTasksFromFile(String filename) {
        tasks.clear();
        try {
            FileReader file = new FileReader(filename);
            BufferedReader reader = new BufferedReader(file);
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                for (String part : parts) {
                    if (!part.trim().isEmpty()) {
                        tasks.add(new Task(part, false));
                    }
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadAllTaskToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Task task : tasks) {
                writer.write(task.getTaskName() + ";");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }
}