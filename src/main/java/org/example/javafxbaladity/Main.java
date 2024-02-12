package org.example.javafxbaladity;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.javafxbaladity.utils.Database;

import java.io.IOException;
import java.net.URL;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //Connection To Database
        Database.connectDB();

        String videoFileName = "0206.mp4";
        URL videoUrl = Main.class.getResource("videos/" + videoFileName);

        if (videoUrl == null) {
            throw new RuntimeException("Video file not found: " + videoFileName);
        }
        Media media = new Media(videoUrl.toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);
        mediaPlayer.setAutoPlay(true);

        StackPane root = new StackPane();
        root.getChildren().add(mediaView);

        mediaPlayer.setOnEndOfMedia(() -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/login.fxml"));
                Scene nextScene = new Scene(fxmlLoader.load(), 1098, 667);


                // Fade Effect
                FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.1), root);
                fadeTransition.setFromValue(1.0);
                fadeTransition.setToValue(0.0);
                fadeTransition.setOnFinished(event -> stage.setScene(nextScene));
                fadeTransition.play();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        stage.sizeToScene();
        Scene videoScene = new Scene(root, 1098, 667);
        stage.setScene(videoScene);
        stage.show();
        stage.sizeToScene();
    }

    public static void main(String[] args) {
        launch();
    }
}