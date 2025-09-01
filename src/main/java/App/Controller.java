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
        appTimer.countPomodoro(10, 5, 2);
    }

    public void StopPomodoro(ActionEvent e) throws InterruptedException {
        appTimer.PausePomodoroTimer();
    }

    public void ResumePomodoro(ActionEvent e) throws InterruptedException {
        appTimer.resumePomodoroTimer();
    }

    public void AddTask(ActionEvent e) {
    }
}

