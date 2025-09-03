package App;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

// toDo: wybieranie czasów w gui
// toDo: powiadamianie o końcu pomodoro i przerwy

public class Main extends Application {
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/sample.fxml")));
            Scene scene = new Scene(root);

            String css = Objects.requireNonNull(this.getClass().getResource("/style.css")).toExternalForm();
            scene.getStylesheets().add(css);

            stage.setTitle("FXML Test");
            stage.setScene(scene);
            stage.show();
            stage.setResizable(false);
         } catch (IOException e) {
            e.printStackTrace();
        }

    }
}