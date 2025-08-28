package App;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args){
      launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root, Color.ROSYBROWN);
        stage.setTitle("Pomodoro_TaskList");

        Image icon= new Image("pomidor.png");
        stage.getIcons().add(icon);

        stage.setWidth(1000);
        stage.setHeight(700);
        stage.setResizable(false);

        stage.setFullScreen(true);

        stage.setScene(scene);
        stage.show();
    }
}