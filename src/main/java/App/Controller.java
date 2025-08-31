package App;

import Timer.Timer;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class Controller {
    public Button RozpocznijPomodoro;
    public Button DodajZadanie;
    Timer timer;

    public Controller() throws InterruptedException {
        timer = new Timer();
    }

    public void startPomodoro(ActionEvent e) throws InterruptedException {
        timer.fullPomodoroTime();
    }

    public void StopPomodoro(ActionEvent e) throws InterruptedException {
        timer.countBreakTimeBySeconds();
    }

    public void AddTask(ActionEvent e) {
    }
}
