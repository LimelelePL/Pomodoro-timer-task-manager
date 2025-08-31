package App;

import Timer.AppTimer;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class Controller {
    public Button RozpocznijPomodoro;
    public Button DodajZadanie;
    AppTimer appTimer;

    public Controller() throws InterruptedException {
        appTimer = new AppTimer();
    }

    public void startPomodoro(ActionEvent e) throws InterruptedException {
        appTimer.countPomodoroBySeconds(10);
    }

    public void StopPomodoro(ActionEvent e) throws InterruptedException {
        appTimer.PausePomodoroTimer();
    }

    public void AddTask(ActionEvent e) {
    }
}
