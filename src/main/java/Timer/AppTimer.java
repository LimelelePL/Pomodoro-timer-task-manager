package Timer;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class AppTimer {
    private int currentTimeTillEnd;
    private int currentInterval=4;

    private Timer timer;
    private TimerTask timerTask;

    public enum TimerState{POMODORO, BREAK, LONG_BREAK, PAUSED}
    private TimerState currentState=TimerState.POMODORO;
    private TimerState previousState=TimerState.POMODORO;

    public AppTimer() throws InterruptedException {
       // chooseTime();
    }

    public void countPomodoro(int pomodoroTime, int breakTime, int intervals){
        currentInterval=intervals;
        for (int i = 0; i < intervals; i++) {
            countPomodoroBySeconds(pomodoroTime);
            countBreakTimeBySeconds(breakTime);
            currentInterval--;
        }
        currentInterval=4;
        countLongBreakTime(breakTime);

    }

    public void resumePomodoroTimer() throws InterruptedException {
       if (currentState==TimerState.PAUSED) {
           if (previousState==TimerState.POMODORO) {
               countPomodoroBySeconds(currentTimeTillEnd);
           } else if (previousState==TimerState.BREAK) {
               countBreakTimeBySeconds(currentTimeTillEnd);
           } else if (previousState==TimerState.LONG_BREAK) {
               countLongBreakTime(currentTimeTillEnd);
           }
       } else {
           System.out.println("Timer is not paused");
       }
    }

    public void PausePomodoroTimer()  {
        timer.cancel();
        if (currentState==TimerState.POMODORO) {
            previousState=currentState;
            currentState = TimerState.PAUSED;
        } else if (currentState==TimerState.BREAK) {
            previousState=currentState;
            currentState = TimerState.PAUSED;
        } else if (currentState==TimerState.LONG_BREAK) {
            previousState=currentState;
            currentState = TimerState.PAUSED;
        } else {
            System.out.println("Timer is not running");
        }
    }

    public void countPomodoroBySeconds(int time)  {
        currentState=TimerState.POMODORO;
        countBySeconds(time);
    }

    public void countLongBreakTime(int time)  {
        currentState=TimerState.LONG_BREAK;
        countBySeconds(time*3);
    }

    public void countBreakTimeBySeconds(int time)  {
        currentState=TimerState.BREAK;
        countBySeconds(time);
    }

    public void countBySeconds(int intervalTime)  {
        timer = new Timer();
        timerTask = new TimerTask() {
            int i=intervalTime;
            @Override
            public void run() {
                int minutes = i / 60;
                int seconds = i % 60;
                System.out.printf("\rPozostało: %02d:%02d", minutes, seconds);
                i--;
                currentTimeTillEnd=i;
                if (i < 0) {
                    timer.cancel();
                    System.out.println("\nCzas minął!");
                    return;
                }
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }


    public static void main (String[] args) throws InterruptedException {
        AppTimer appTimer =new AppTimer();
        appTimer.countBySeconds(10);

    }
}