package org.example.javafxbaladity;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.javafxbaladity.utils.Database;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //Connection To Database
        Database.connectDB();

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1098, 667);
        stage.setResizable(false);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}