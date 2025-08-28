package Timer;

import java.util.Scanner;

/*
Timer zawartosc:
-choose Time()- metoda w ktorej pobieramy czas danego pomodoru i przerwy.
Czas ten mieści sie w zakresie od 0 do 180min
-countBySeconds- odliczanie oraz wyświetlanie odliczania
 */
public class Timer {
    private int pomodoroTime;
    private int breakTime;

    public Timer() throws InterruptedException {
        chooseTime();
        int decision=1;
        Scanner scanner = new Scanner(System.in);
        while (decision==1) {
            for (int i = 0; i < 4; i++) {
                countPomodoroBySeconds();
                countBreakTimeBySeconds();
            }

            System.out.println("long break: ");
            countBySeconds(30);
            System.out.println("would u like to continue? 0 no, 1 yes");
            decision = scanner.nextInt();
        }
    }

    public void chooseTime(){
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Choose pomodoro time in minutes: ");
            this.pomodoroTime = scanner.nextInt()*60;

            System.out.println("Choose break time in minutes: ");
            this.breakTime = scanner.nextInt()*60;

            if(pomodoroTime<0 || breakTime<0)  System.out.println("Invalid time");
            if (pomodoroTime>180 || breakTime>180) System.out.println("Invalid time");

        }
        while(pomodoroTime < 0 || breakTime < 0 || pomodoroTime>180 || breakTime>180);
    }

    public void countPomodoroBySeconds() throws InterruptedException {
        System.out.println("POMODORO ");
        countBySeconds(pomodoroTime);
    }

    public void countBreakTimeBySeconds() throws InterruptedException {
        System.out.println("BREAK ");
        countBySeconds(breakTime);
    }

    public void countBySeconds(int intervalTime) throws InterruptedException {
        for (int i = intervalTime; i > 0; i--) {
            int minutes = i / 60;
            int seconds = i % 60;
            System.out.printf("\rPozostało: %02d:%02d", minutes, seconds);
            Thread.sleep(1000);
        }
        System.out.println("\nKoniec!");
    }


    public static void main (String[] args) throws InterruptedException {
        Timer timer=new Timer();

    }
}