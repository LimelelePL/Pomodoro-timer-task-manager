package App;

import TaskManager.TaskManager;
import javafx.scene.control.TextField;
import timer.AppTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.CheckBox;



public class Controller {
    @FXML public Button RozpocznijPomodoro;
    @FXML public Button taskName;

    @FXML private Label timeView;
    @FXML private Label currentState;
    @FXML private Label task1;
    @FXML private Label task2;
    @FXML private Label task3;
    @FXML private Label task4;
    @FXML private Label task5;
    @FXML private Label task6;

    @FXML private TextField zadanie;


    @FXML private CheckBox c1;
    @FXML private CheckBox c2;
    @FXML private CheckBox c3;
    @FXML private CheckBox c4;
    @FXML private CheckBox c5;
    @FXML private CheckBox c6;


    private final AppTimer appTimer;
    private final TaskManager taskManager;


    public Controller() throws InterruptedException {
        appTimer = new AppTimer();
        taskManager = new TaskManager();
    }


    @FXML
    public void initialize() {
        timeView.setText("00:00");
        timeView.setStyle("-fx-font-size: 90px; -fx-text-fill: #333;");
        currentState.setText("idle");
        currentState.setStyle("-fx-font-size: 30px; -fx-text-fill: #333;");

        appTimer.setOnTick(seconds ->
                Platform.runLater(() -> {
                    timeView.setText(formatTime(Math.max(0, seconds)));
                    currentState.setText(formatState(appTimer.getCurrentState()) +
                            " #" +  appTimer.getCurrentInterval());
                })
        );
    }

    @FXML
    public void startPomodoro(ActionEvent e) throws InterruptedException {
        appTimer.countPomodoro(25*60, 5*60, 4);
    }
    @FXML
    public void StopPomodoro(ActionEvent e) throws InterruptedException {
        appTimer.PausePomodoroTimer();
    }
    @FXML
    public void ResumePomodoro(ActionEvent e) throws InterruptedException {
        appTimer.resumePomodoroTimer();
    }

    public void addTask(ActionEvent e) {
        String taskName = zadanie.getText();
        if (!taskName.isEmpty()) {
            Label[] taskLabels = {task1, task2, task3, task4, task5, task6};
            CheckBox[] checkBoxes = {c1, c2, c3, c4, c5, c6};

            boolean added = false;
            for (int i = 0; i < taskLabels.length; i++) {
                if (taskLabels[i].getText().isEmpty()) {
                    taskLabels[i].setText(taskName);

                    checkBoxes[i].setVisible(true);
                    added = true;
                    break;
                }
            }

            if (added) {
                System.out.println("Added task: " + taskName);
            } else {
                System.out.println("Task list is full. Cannot add more tasks.");
            }


            zadanie.clear();
        }
    }

    public void removeTask1(ActionEvent e) {
        removeTask(0);
    }
    public void removeTask2(ActionEvent e) {
       removeTask(1);
    }
    public void removeTask3(ActionEvent e) {
        removeTask(2);
    }
    public void removeTask4(ActionEvent e) {
       removeTask(3);
    }
    public void removeTask5(ActionEvent e) {
       removeTask(4);
    }
    public void removeTask6(ActionEvent e) {
       removeTask(5);
    }


    public void removeTask(int index) {
        Label[] taskLabels = {task1, task2, task3, task4, task5, task6};
        CheckBox[] checkBoxes = {c1, c2, c3, c4, c5, c6};

        for (int i = index; i < taskLabels.length - 1; i++) {
            taskLabels[i].setText(taskLabels[i + 1].getText());
            checkBoxes[i].setVisible(checkBoxes[i + 1].isVisible());
            checkBoxes[i].setSelected(checkBoxes[i + 1].isSelected());
        }

        taskLabels[taskLabels.length - 1].setText("");
        checkBoxes[checkBoxes.length - 1].setVisible(false);
        checkBoxes[checkBoxes.length - 1].setSelected(false);

        taskManager.removeTask(index + 1);
    }

    private String formatState(AppTimer.TimerState state) {
        return switch (state) {
            case POMODORO -> "Work";
            case BREAK -> "Break";
            case LONG_BREAK -> "Long Break";
            case PAUSED -> "Paused";
            default -> "Idle";
        };
    }

    private String formatTime(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

}


