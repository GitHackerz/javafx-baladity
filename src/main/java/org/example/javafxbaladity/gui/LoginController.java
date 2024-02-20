package org.example.javafxbaladity.gui;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.javafxbaladity.Main;

public class LoginController {
    @FXML
    Button SignIn_Btn;
    int width = 1200;
    int height = 700;

    public void OnSignInClicked() throws Exception {
        ProgressIndicator progressIndicator = new ProgressIndicator();
        progressIndicator.setPrefSize(70, 70);
        progressIndicator.setStyle("-fx-progress-color: #0C162C;");

        Label loadingLabel = new Label("Loading");
        loadingLabel.setStyle("-fx-text-fill: #0C162C; -fx-font-family: Inter; -fx-font-weight: bold;");

        // Vbox contains ProgressIndic and Loading text
        VBox container = new VBox(progressIndicator, loadingLabel);
        container.setAlignment(Pos.CENTER); // Center align the content

        Stage window = (Stage) SignIn_Btn.getScene().getWindow();
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), window.getScene().getRoot());
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/home.fxml"));
        Parent root = fxmlLoader.load();
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), root);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        // Timeline for the progressIndicator
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(2), event -> {
                    window.setScene(new Scene(root, width, height));
                    fadeIn.play();
                })
        );

        fadeOut.setOnFinished(event -> timeline.play());
        fadeOut.play();
        window.setScene(new Scene(container, width, height));
    }


}
