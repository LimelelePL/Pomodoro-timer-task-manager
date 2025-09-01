package Timer;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

public class AppTimer {
    private int currentTimeTillEnd;
    private int intervals;
    private int breakTime;
    private int pomodoroTime;
    private int currentInterval=0;
    private int longBreakTime;

    private Timer timer;
    private TimerTask timerTask;

    public enum TimerState{POMODORO, BREAK, LONG_BREAK, PAUSED}
    private TimerState currentState=TimerState.POMODORO;
    private TimerState previousState=TimerState.POMODORO;

    private IntConsumer onTick;

    public void setOnTick(IntConsumer onTick) {
        this.onTick = onTick;
    }

    public TimerState getCurrentState() {
        return currentState;
    }

    public int getCurrentInterval(){
        return currentInterval;
    }

    public void countPomodoro(int pomodoroTime, int breakTime, int intervals){
        this.intervals=intervals;
        this.breakTime=breakTime; //*60
        this.pomodoroTime=pomodoroTime;
        this.longBreakTime=breakTime*3; //*60

        countPomodoroBySeconds(this.pomodoroTime);
    }

    public void resumePomodoroTimer() throws InterruptedException {
        if (timer==null) {
            System.out.println("Timer is not running");
            return;
        }

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
        System.out.println("\nResuming timer...");
    }

    public void PausePomodoroTimer()  {

        if (timer==null) {
            System.out.println("Timer is not running");
            return;
        }
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
        System.out.println("\nTimer paused");
    }

    public void countPomodoroBySeconds(int time)  {
        currentInterval++;
        System.out.println("aktualny interval: "+currentInterval);
        currentState=TimerState.POMODORO;
        countBySeconds(time);

    }

    private void countLongBreakTime(int time)  {
        currentState=TimerState.LONG_BREAK;
        countBySeconds(longBreakTime);
        currentInterval=0;
    }

    private void countBreakTimeBySeconds(int time)  {
        currentState=TimerState.BREAK;
        countBySeconds(time);
    }

    private void countBySeconds(int intervalTime)  {
        if(timer!=null) {
            timer.cancel();
        }

        timer = new Timer();
        timerTask = new TimerTask() {
            int i=intervalTime;
            @Override
            public void run() {
                if(onTick!=null) {
                    onTick.accept(i);
                }
                i--;
                currentTimeTillEnd=i;
                if (i < 0) {
                    timer.cancel();
                    System.out.println("\nCzas minął!");

                    if(currentInterval==intervals) {
                        countLongBreakTime(15);
                        System.out.println("long break");
                        return;
                    }
                    if (currentState == TimerState.POMODORO ) {
                        System.out.println("Koniec pomodoro. Czas na przerwę!");
                        countBreakTimeBySeconds(breakTime);

                    } else if (currentState == TimerState.BREAK) {
                        System.out.println("Koniec przerwy. Kolejne pomodoro!");
                        countPomodoroBySeconds(pomodoroTime);
                    }

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