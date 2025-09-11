package timer;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class AppTimer implements Subject {
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

    private List<TimerObserver> observers=new ArrayList<>();

    public void countPomodoro(int pomodoroTime, int breakTime, int longBreakTime){
        this.longBreakTime = longBreakTime;
        this.intervals=4;
        this.breakTime=breakTime; //*60
        this.pomodoroTime=pomodoroTime;

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

    public void skip() {
        if (timer == null) {
            return;
        }
        timer.cancel();

        if (currentState==TimerState.PAUSED) {
            if (previousState==TimerState.POMODORO) {
                countPomodoroBySeconds(currentTimeTillEnd);
            } else if (previousState==TimerState.BREAK) {
                countBreakTimeBySeconds(currentTimeTillEnd);
            } else if (previousState==TimerState.LONG_BREAK) {
                countLongBreakTime(currentTimeTillEnd);
            }
        }

        if (currentState == TimerState.LONG_BREAK) {
            previousState = TimerState.LONG_BREAK;
            currentState = TimerState.POMODORO;
            currentInterval = 1;
            currentTimeTillEnd = pomodoroTime;
            countPomodoroBySeconds(this.pomodoroTime);
            try {
                SoundNotification.playSound("/sound.mp3");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if (currentState == TimerState.POMODORO) {
            if (currentInterval == intervals) {
                currentState = TimerState.LONG_BREAK;
                previousState = TimerState.POMODORO;
                currentTimeTillEnd = longBreakTime;
                countLongBreakTime(this.longBreakTime);
                PausePomodoroTimer();
                return;
            } else {
                previousState = TimerState.POMODORO;
                currentState = TimerState.BREAK;
                currentTimeTillEnd = breakTime;
                countBreakTimeBySeconds(this.breakTime);
            }
        } else if (currentState == TimerState.BREAK) {
            previousState = TimerState.BREAK;
            currentState = TimerState.POMODORO;
            currentInterval++;
            currentTimeTillEnd = pomodoroTime;
            countPomodoroBySeconds(this.pomodoroTime);
        }

        PausePomodoroTimer();

        try {
            SoundNotification.playSound("/sound.mp3");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void resumePomodoroTimer()  {
        if (timer==null) {
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
        }
    }

    public void PausePomodoroTimer()  {
        if (timer==null) {
            return;
        }
        timer.cancel();

        if (currentState==TimerState.POMODORO) {
            previousState=currentState;
            currentState = TimerState.PAUSED;
           notifyObservers();
        } else if (currentState==TimerState.BREAK) {
            previousState=currentState;
            currentState = TimerState.PAUSED;
          notifyObservers();
        } else if (currentState==TimerState.LONG_BREAK) {
            previousState=currentState;
            currentState = TimerState.PAUSED;
            notifyObservers();
        }
    }

    private void countPomodoroBySeconds(int time)  {
        currentState=TimerState.POMODORO;
        countBySeconds(time);
    }

    private void countLongBreakTime(int time)  {
        currentState=TimerState.LONG_BREAK;
        countBySeconds(longBreakTime);

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
              notifyObservers();
                i--;
                currentTimeTillEnd = i;
                if (i < 0) {
                    timer.cancel();

                    if(currentState == TimerState.LONG_BREAK) {
                        try {
                            SoundNotification.playSound("/sound.mp3");
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        currentInterval = 1;
                        currentTimeTillEnd = pomodoroTime;
                        countPomodoroBySeconds(pomodoroTime);
                        PausePomodoroTimer();
                        return;
                    }
                    if (currentInterval == intervals ) {
                        try {
                            SoundNotification.playSound("/sound.mp3");
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        currentTimeTillEnd = longBreakTime;
                        countLongBreakTime(longBreakTime);
                        PausePomodoroTimer();
                        return;
                    }
                    if (currentState == TimerState.POMODORO) {
                        try {
                            SoundNotification.playSound("/sound.mp3");
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        currentTimeTillEnd = breakTime;
                        countBreakTimeBySeconds(breakTime);
                        PausePomodoroTimer();
                    } else if (currentState == TimerState.BREAK) {
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

    @Override
    public void addObserver(TimerObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(TimerObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (TimerObserver observer : observers) {
            observer.update(currentTimeTillEnd);
        }
    }

    public TimerState getCurrentState() {
        return currentState;
    }

    public int getCurrentInterval(){
        return currentInterval;
    }

    public TimerState getPreviousState() {
        return previousState;
    }

}
