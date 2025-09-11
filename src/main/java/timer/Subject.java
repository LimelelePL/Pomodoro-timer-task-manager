package timer;

public interface Subject {
        void addObserver(TimerObserver observer);
        void removeObserver(TimerObserver observer);
        void notifyObservers();
}
