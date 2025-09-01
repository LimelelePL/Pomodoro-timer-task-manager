package App;

import Timer.AppTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Controller {
    @FXML public Button RozpocznijPomodoro;
    @FXML public Button DodajZadanie;
    @FXML private Label timeView;
    @FXML private Label currentState;
    private final AppTimer appTimer;

    public Controller() throws InterruptedException {
        appTimer = new AppTimer();
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
        appTimer.countPomodoro(10, 5, 2);
    }
    @FXML
    public void StopPomodoro(ActionEvent e) throws InterruptedException {
        appTimer.PausePomodoroTimer();
    }
    @FXML
    public void ResumePomodoro(ActionEvent e) throws InterruptedException {
        appTimer.resumePomodoroTimer();
    }

    public void AddTask(ActionEvent e) {
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
