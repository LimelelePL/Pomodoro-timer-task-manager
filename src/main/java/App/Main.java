package App;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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

        Text timerText = new Text("TIMER");
        timerText.setFill(Color.DARKGRAY);
        timerText.setFont(Font.font("Arial", 50));
        timerText.setX(650);
        timerText.setY(100);

        Text listText = new Text("To do list");
        listText.setFill(Color.DARKGRAY);
        listText.setFont(Font.font("Arial", 50));
        listText.setX(50);
        listText.setY(400);


        Image sceneImage = new Image("pomidor_scena_bezTla.png");
        ImageView imageView = new ImageView();
        imageView.setImage(sceneImage);
        imageView.setFitWidth(300);
        imageView.setFitHeight(300);
        imageView.setX(30);
        imageView.setY(30);

        root.getChildren().add(timerText);
        root.getChildren().add(imageView);
        root.getChildren().add(listText);

        stage.setScene(scene);
        stage.show();
    }
}