package App;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

// toDo: dodać dynamiczne rozciaganie okna
// toDo: dodać odpowiednie komunikaty typu ze nie mozna dodac wiecej zadań

public class Main extends Application {
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/sample.fxml")));
            Scene scene = new Scene(root);

            String css = Objects.requireNonNull(this.getClass().getResource("/style.css")).toExternalForm();
            scene.getStylesheets().add(css);

            stage.setTitle("Pomodoro Timer");
            Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pomidor_scena_bezTla.png")));
            stage.getIcons().add(icon);
            stage.setScene(scene);
            stage.show();
            stage.setResizable(false);

         } catch (IOException e) {
            e.printStackTrace();
        }

    }
}