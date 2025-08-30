package App;

import Timer.Timer;
import javafx.event.ActionEvent;

public class Controller {
    Timer timer;

    public Controller() throws InterruptedException {
        timer = new Timer();
    }

    public void startPomodoro(ActionEvent e) throws InterruptedException {
        timer.countPomodoroBySeconds();
    }
    public void stopPomodoro(ActionEvent e) throws InterruptedException {
        timer.countBreakTimeBySeconds();
    }
}
