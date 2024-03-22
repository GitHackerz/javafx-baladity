package org.example.javafxbaladity.gui;

import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.javafxbaladity.Main;
import org.example.javafxbaladity.Services.CitoyenService;
import org.example.javafxbaladity.Services.UserService;
import org.example.javafxbaladity.models.Citoyen;
import org.example.javafxbaladity.models.User;

import static org.example.javafxbaladity.gui.smsSender.sendSMS;

public class LoginController {
    @FXML
    Button SignIn_Btn;
    @FXML
    private Button ForgetPassBTN;
    int width = 1200;
    int height = 700;

    @FXML
    private TextField tf_CheckEmailUser;

    @FXML
    private TextField tf_CheckPasswordUser;

    CitoyenService c = new CitoyenService();
    UserService u = new UserService();
    int sessionUserId;
    String sessionUsernom;
    String sessionUserPrenom;
    String sessionUserRole;

    public void OnSignupBtn() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/inscription2.fxml"));
        Parent root = fxmlLoader.load();

        // Get the current stage from the sign-up button
        Stage window = (Stage) onsighnUP_BTN.getScene().getWindow();

        // Set the new scene with desired dimensions
        Scene newScene = new Scene(root, 1098, 667);

        // Set the root to transparent before animation to make sure it is not visible
        root.translateXProperty().set(window.getWidth());

        // Apply the new scene to the window
        window.setScene(newScene);


        // Create the transition
        TranslateTransition transition = new TranslateTransition();
        transition.setNode(root);
        transition.setDuration(Duration.seconds(1));
        transition.setFromX(window.getWidth());
        transition.setToX(0);

        // Play the animation
        transition.play();
    }


    public void OnSignInClicked() throws Exception {


        if (u.authenticateUser(tf_CheckEmailUser.getText(), tf_CheckPasswordUser.getText())) {


            User u1 =  u.HetUser(tf_CheckEmailUser.getText());
            Citoyen c1 = u.HetCitoyen(u1.getIdCitoyen());

            UserSession z = UserSession.getInstace(u1.getId(), c1.getNomCitoyen(), c1.getPrenomCitoyen(), u1.getRole());
            sessionUserId = z.getUserId();
            sessionUsernom = z.getUserName();
            sessionUserPrenom = z.getPrenom();
            sessionUserRole = z.getRole();
            ApplicationContext.getInstance().setUserSession(z);
            System.out.println(sessionUserRole);

//            sendSMS("+21692510826", "salut cava" + " " + sessionUsernom + "/ rak conectit");

            if (sessionUserRole.equals("employe") || sessionUserRole.equals("admin")) {
                MFXProgressSpinner progressIndicator = new MFXProgressSpinner();
                progressIndicator.setPrefSize(70, 70);
                progressIndicator.setStyle("-fx-progress-color: #0C162C;");

                // Label loadingLabel = new Label("Loading");
                //loadingLabel.setStyle("-fx-text-fill: #0C162C; -fx-font-family: Inter; -fx-font-weight: bold;");

                // Vbox contains ProgressIndic and Loading text
                VBox container = new VBox(progressIndicator);
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
                        new KeyFrame(Duration.seconds(0.5), event -> {
                            window.setScene(new Scene(root, width, height));
                            fadeIn.play();
                        })
                );

                fadeOut.setOnFinished(event -> timeline.play());
                fadeOut.play();
                window.setScene(new Scene(container, width, height));
            }
        }
        if (sessionUserRole.equals("user")) {

            MFXProgressSpinner progressIndicator = new MFXProgressSpinner();
            progressIndicator.setPrefSize(70, 70);
            progressIndicator.setStyle("-fx-progress-color: #0C162C;");

            // Label loadingLabel = new Label("Loading");
            //loadingLabel.setStyle("-fx-text-fill: #0C162C; -fx-font-family: Inter; -fx-font-weight: bold;");

            // Vbox contains ProgressIndic and Loading text
            VBox container = new VBox(progressIndicator);
            container.setAlignment(Pos.CENTER); // Center align the content

            Stage window = (Stage) SignIn_Btn.getScene().getWindow();
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), window.getScene().getRoot());
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);

            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/useraccount.fxml"));
            Parent root = fxmlLoader.load();
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), root);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);

            // Timeline for the progressIndicator
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(0.5), event -> {
                        window.setScene(new Scene(root, width, height));
                        fadeIn.play();
                    })
            );

            fadeOut.setOnFinished(event -> timeline.play());
            fadeOut.play();
            window.setScene(new Scene(container, width, height));

        }
        if (!u.authenticateUser(tf_CheckEmailUser.getText(), tf_CheckPasswordUser.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Authentication Failed");
            alert.setHeaderText(null);
            alert.setContentText("Invalid email or password. Please try again.");
            alert.showAndWait();
        }
    }

    @FXML
    private Button onsighnUP_BTN;

    public void onSignup() throws Exception {
        MFXProgressSpinner progressIndicator = new MFXProgressSpinner();
        progressIndicator.setPrefSize(70, 70);
        progressIndicator.setStyle("-fx-progress-color: #0C162C;");

        // Label loadingLabel = new Label("Loading");
        //loadingLabel.setStyle("-fx-text-fill: #0C162C; -fx-font-family: Inter; -fx-font-weight: bold;");

        // Vbox contains ProgressIndic and Loading text
        VBox container = new VBox(progressIndicator);
        container.setAlignment(Pos.CENTER); // Center align the content

        Stage window = (Stage) onsighnUP_BTN.getScene().getWindow();
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), window.getScene().getRoot());
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/inscription2.fxml"));
        Parent root = fxmlLoader.load();
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), root);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        // Timeline for the progressIndicator
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.5), event -> {
                    window.setScene(new Scene(root, width, height));
                    fadeIn.play();
                })
        );

        fadeOut.setOnFinished(event -> timeline.play());
        fadeOut.play();
        window.setScene(new Scene(container, width, height));
    }

    public void Forgetpass() throws Exception {
        MFXProgressSpinner progressIndicator = new MFXProgressSpinner();
        progressIndicator.setPrefSize(70, 70);
        progressIndicator.setStyle("-fx-progress-color: #0C162C;");

        // Label loadingLabel = new Label("Loading");
        //loadingLabel.setStyle("-fx-text-fill: #0C162C; -fx-font-family: Inter; -fx-font-weight: bold;");

        // Vbox contains ProgressIndic and Loading text
        VBox container = new VBox(progressIndicator);
        container.setAlignment(Pos.CENTER); // Center align the content

        Stage window = (Stage) ForgetPassBTN.getScene().getWindow();
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), window.getScene().getRoot());
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/forgetpassword.fxml"));
        Parent root = fxmlLoader.load();
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), root);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        // Timeline for the progressIndicator
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.5), event -> {
                    window.setScene(new Scene(root, width, height));
                    fadeIn.play();
                })
        );

        fadeOut.setOnFinished(event -> timeline.play());
        fadeOut.play();
        window.setScene(new Scene(container, width, height));
    }


}
