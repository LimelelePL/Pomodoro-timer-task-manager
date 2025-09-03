package App;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class pomodoroPick {
    @FXML
    private ChoiceBox<Integer> pomodoro;
    @FXML
    private ChoiceBox<Integer> chooseBreak;
    @FXML
    private ChoiceBox<Integer> longBreak;

    @FXML
    public void initialize() {
        pomodoro.getItems().addAll(25, 30, 35, 40, 45, 50, 55);
        chooseBreak.getItems().addAll(5, 10, 15, 20);
        longBreak.getItems().addAll(15, 25 ,30, 45);

        pomodoro.setValue(25);
        chooseBreak.setValue(5);
        longBreak.setValue(15);
    }
    public int getPomodoroTime() {
        return pomodoro.getValue();
    }
    public int getBreakTime() {
        return chooseBreak.getValue();
    }
    public int getLongBreakTime() {
        return longBreak.getValue();
    }
}
