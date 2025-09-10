package App;

import TaskManager.TaskManager;
import javafx.animation.PauseTransition;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import timer.AppTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;
import java.util.Objects;

public class Controller {
    @FXML public Button RozpocznijPomodoro;
    @FXML public Button taskName;
    @FXML public Button properties;

    @FXML private Label timeView;
    @FXML private Label currentState;
    @FXML private Label task1;
    @FXML private Label task2;
    @FXML private Label task3;
    @FXML private Label task4;
    @FXML private Label task5;
    @FXML private Label task6;
    @FXML private Label noMoreTasks;

    @FXML private TextField zadanie;

    @FXML private CheckBox c1;
    @FXML private CheckBox c2;
    @FXML private CheckBox c3;
    @FXML private CheckBox c4;
    @FXML private CheckBox c5;
    @FXML private CheckBox c6;

    private final AppTimer appTimer;
    private final TaskManager taskManager;

    private int pomodoroTime = 25;
    private int breakTime = 5;
    private int longBreakTime = 15;

    public Controller() {
        appTimer = new AppTimer();
        taskManager = new TaskManager();
    }

    @FXML
    public void initialize() {
        timeView.setText("00:00");
        timeView.setStyle("-fx-font-size: 110px; -fx-text-fill: #333;");
        timeView.setAlignment(javafx.geometry.Pos.CENTER);
        currentState.setText(" start the counter and enjoy! ");
        currentState.setStyle("-fx-font-size: 20px; -fx-text-fill: #333;");
        currentState.setAlignment(javafx.geometry.Pos.CENTER);

        appTimer.setOnTick(seconds ->
                Platform.runLater(() -> {
                    timeView.setText(formatTime(Math.max(0, seconds)));
                    currentState.setText(formatState(appTimer.getCurrentState()) +
                            " #" +  appTimer.getCurrentInterval());
                })
        );

        taskManager.loadTasksFromFile("src/main/java/TaskManager/tasks.txt");
        updateTaskView();

    }

    @FXML
    public void skipState(ActionEvent e){
        appTimer.skip();
    }

    @FXML
    public void chooseProperties(ActionEvent e) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pomodoroTime.fxml"));
            Parent root = loader.load();

            pomodoroPick pomodoroPickController = loader.getController();

            Stage stage = new Stage();
            stage.setTitle("Ustawienia Pomodoro");
            Scene scene = new Scene(root);

            String css = Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm();
            scene.getStylesheets().add(css);

            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL); //blokada glownego okna
            stage.setResizable(false);
            stage.showAndWait();

            pomodoroTime = pomodoroPickController.getPomodoroTime();
            breakTime = pomodoroPickController.getBreakTime();
            longBreakTime = pomodoroPickController.getLongBreakTime();

            appTimer.refreshAfterChangeProperties(pomodoroTime, breakTime, longBreakTime);


        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void startPomodoro(ActionEvent e)  {

       //appTimer.countPomodoro(pomodoroTime/7 , breakTime/3, 5);
       appTimer.countPomodoro(pomodoroTime *60, breakTime*60, longBreakTime*60);
    }
    @FXML
    public void StopPomodoro(ActionEvent e)  {
        appTimer.PausePomodoroTimer();
    }
    @FXML
    public void ResumePomodoro(ActionEvent e) throws InterruptedException {
        appTimer.resumePomodoroTimer();
    }

    public void addTask(ActionEvent e) {
        String taskName = zadanie.getText();

        if (taskName.trim().isEmpty() || taskName.length() > 90 ) {
            noMoreTasks.setText("Task is empty or too long!");
            noMoreTasks.setAlignment(javafx.geometry.Pos.CENTER);
            noMoreTasks.setVisible(true);

            PauseTransition pause = new PauseTransition(Duration.seconds(3));
            pause.setOnFinished(event -> noMoreTasks.setVisible(false) );
            pause.play();
            return;
        }

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
                taskManager.addTask(taskName);
            }

            tooMuchTasks(taskManager, noMoreTasks);

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


    private void removeTask(int index) {
        Label[] taskLabels = {task1, task2, task3, task4, task5, task6};
        CheckBox[] checkBoxes = {c1, c2, c3, c4, c5, c6};
    //moving tasks up
        for (int i = index; i < taskLabels.length - 1; i++) {
            taskLabels[i].setText(taskLabels[i + 1].getText());
            checkBoxes[i].setVisible(checkBoxes[i + 1].isVisible());
            checkBoxes[i].setSelected(checkBoxes[i + 1].isSelected());
        }

        taskLabels[taskLabels.length - 1].setText("");
        checkBoxes[checkBoxes.length - 1].setVisible(false);
        checkBoxes[checkBoxes.length - 1].setSelected(false);

        taskManager.removeTask(index);
        tooMuchTasks(taskManager, noMoreTasks);

    }

    private void updateTaskView(){
        Label[] taskLabels = {task1, task2, task3, task4, task5, task6};
        CheckBox[] checkBoxes = {c1, c2, c3, c4, c5, c6};

        for (int i=0; i<taskLabels.length; i++){
            if (i<taskManager.getTasks().size()){
                taskLabels[i].setText(taskManager.getTasks().get(i).getTaskName());
                checkBoxes[i].setVisible(true);
                checkBoxes[i].setSelected(taskManager.getTasks().get(i).isDone());
            } else {
                taskLabels[i].setText("");
                checkBoxes[i].setVisible(false);
                checkBoxes[i].setSelected(false);
            }
        }
        tooMuchTasks(taskManager, noMoreTasks);

    }

    private static void tooMuchTasks(TaskManager taskManager, Label noMoreTasks) {
        if(taskManager.getTasks().size() == 6) {
            noMoreTasks.setText("can't add more tasks!");
            noMoreTasks.setAlignment(javafx.geometry.Pos.CENTER);
            noMoreTasks.setVisible(true);

            PauseTransition pause = new PauseTransition(Duration.seconds(3));
            pause.setOnFinished(event -> noMoreTasks.setVisible(false) );
            pause.play();
        }
    }

    private String formatState(AppTimer.TimerState state) {
        if (state == AppTimer.TimerState.PAUSED) {
            return "Paused (" + formatState(appTimer.getPreviousState()) + ")";
        }
        return switch (state) {
            case POMODORO -> "Work";
            case BREAK -> "Break";
            case LONG_BREAK -> "Long Break";
            default -> "enjoy";
        };
    }

    private String formatTime(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

}


