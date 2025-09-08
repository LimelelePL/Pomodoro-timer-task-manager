package timer;

import java.util.Timer;
import java.util.TimerTask;
import java.util.function.IntConsumer;


public class AppTimer {
    private int currentTimeTillEnd;
    private int intervals;
    private int breakTime;
    private int pomodoroTime;
    private int currentInterval=1;
    private int longBreakTime;


    private Timer timer;


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

    public void refreshAfterChangeProperties(int pomodoroTime, int breakTime, int longBreakTime){
        this.currentInterval=1;
        this.breakTime=breakTime;
        this.pomodoroTime=pomodoroTime;
        this.longBreakTime=longBreakTime;

        if (currentState==TimerState.POMODORO) {
            countPomodoroBySeconds(this.pomodoroTime);
        } else if (currentState==TimerState.BREAK) {
            countBreakTimeBySeconds(this.breakTime);
        } else if (currentState==TimerState.LONG_BREAK) {
            countLongBreakTime(this.longBreakTime);
        }

        PausePomodoroTimer();
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
            if (onTick != null) {
                onTick.accept(currentTimeTillEnd);
            }
        } else if (currentState==TimerState.BREAK) {
            previousState=currentState;
            currentState = TimerState.PAUSED;
            if (onTick != null) {
                onTick.accept(currentTimeTillEnd);
            }
        } else if (currentState==TimerState.LONG_BREAK) {
            previousState=currentState;
            currentState = TimerState.PAUSED;
            if (onTick != null) {
                onTick.accept(currentTimeTillEnd);
            }
        } else {
            System.out.println("Timer is not running");
        }
        System.out.println("\nTimer paused");
    }

    public void countPomodoroBySeconds(int time)  {
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
        TimerTask timerTask = new TimerTask() {
            int i = intervalTime;

            @Override
            public void run() {
                if (onTick != null) {
                    onTick.accept(i);
                }
                i--;
                currentTimeTillEnd = i;
                if (i < 0) {
                    timer.cancel();
                    System.out.println("\nCzas minął!");

                    if (currentInterval == intervals) {

                        countLongBreakTime(longBreakTime);

                        System.out.println("long break");
                        try {
                            SoundNotification.playSound("/sound.mp3");
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        currentTimeTillEnd = longBreakTime;
                        PausePomodoroTimer();
                        return;
                    }
                    if (currentState == TimerState.POMODORO) {

                        System.out.println("Koniec pomodoro. Czas na przerwę!");
                        try {
                            SoundNotification.playSound("/sound.mp3");
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        currentTimeTillEnd = breakTime;
                        countBreakTimeBySeconds(breakTime);
                        PausePomodoroTimer();
                    } else if (currentState == TimerState.BREAK) {
                        System.out.println("Koniec przerwy. Kolejne pomodoro!");
                        currentInterval++;
                        try {
                            SoundNotification.playSound("/sound.mp3");
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        currentTimeTillEnd = pomodoroTime;
                        countPomodoroBySeconds(pomodoroTime);
                        PausePomodoroTimer();
                    }

                }
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }

    public TimerState getPreviousState() {
        return previousState;
    }

    public static void main (String[] args) throws InterruptedException {
        AppTimer appTimer =new AppTimer();
        appTimer.countBySeconds(10);

    }
}
