package org.example.javafxbaladity.gui;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.example.javafxbaladity.Main;
import org.example.javafxbaladity.Services.UserService;
import org.example.javafxbaladity.models.Document;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class forgetpassword implements Initializable {
    UserService u = new UserService() ;

    @FXML
    private MFXGenericDialog Dialog_Password;

    @FXML
    private MFXButton SendCodeBtn;

    @FXML
    private MFXButton ValidateCodeBtn;

    @FXML
    private MFXGenericDialog Verif_Dialog;

    @FXML
    private MFXButton savePassword;

    @FXML
    private TextField tf_Email;

    @FXML
    private MFXTextField tf_codee;

    @FXML
    private Pane general_pane;
    int SCREEN_WIDTH = 1200;
    int SCREEN_HEIGHT = 700;

    @FXML
    private MFXTextField tf_passwordUpdate;
      int x  ;
      int userID ;

    public void sendcode(ActionEvent event) {
        if (event.getSource() == SendCodeBtn) {
            Random random = new Random();
            int randomNumber = random.nextInt(999999);
            x=randomNumber ;
            System.out.println(x);
            String message = "Your verification code is: " + randomNumber;
            String recipientEmail = tf_Email.getText();

            boolean emailSent = SendEmail.send(recipientEmail, "Verification Code", message);
            if (emailSent) {
                //Verif_Dialog.setVisible(true);

                Verif_Dialog.setOpacity(0);
                Verif_Dialog.setVisible(true);

                // Créer une transition de fondu pour simuler l'effet de coup d'éponge
                FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), Verif_Dialog);
                fadeIn.setToValue(1.0);
                fadeIn.play();

                BoxBlur boxBlur = new BoxBlur();
                boxBlur.setWidth(5);
                boxBlur.setHeight(5);
                boxBlur.setIterations(3);
                general_pane.setEffect(boxBlur);


            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Email Sending Failed");
                alert.setHeaderText(null);
                alert.setContentText("Failed to send verification code. Please check your internet connection or email settings and try again.");

                alert.showAndWait();
            }
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
           Dialog_Password.setVisible(false);
           Verif_Dialog.setVisible(false);
    }


    public void sendcode(javafx.event.ActionEvent event) {



        userID = u.getUserIdByEmail(tf_Email.getText()) ;
        System.out.println(userID);
        if( userID!=-1 )
        {


        Random random = new Random();
        int randomNumber = random.nextInt(999999);
        x=randomNumber ;
        System.out.println(x);
        String message = "Your verification code is: " + randomNumber;
        String recipientEmail = tf_Email.getText();

        boolean emailSent = SendEmail.send(recipientEmail, "Verification Code", message);
        if (emailSent) {
            Verif_Dialog.setVisible(true);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Email Sending Failed");
            alert.setHeaderText(null);
            alert.setContentText("Failed to send verification code. Please check your internet connection or email settings and try again.");
            alert.showAndWait();
        }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("email mch mawjoud database");
            alert.showAndWait();

        }
    }

    public void validateCode()
    {
        if(Integer.parseInt(tf_codee.getText())==x)
        {
//            Dialog_Password.setVisible(true);
            Verif_Dialog.setVisible(false);

            Dialog_Password.setOpacity(0);
            Dialog_Password.setVisible(true);

            // Créer une transition de fondu pour simuler l'effet de coup d'éponge
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), Dialog_Password);
            fadeIn.setToValue(1.0);
            fadeIn.play();

            BoxBlur boxBlur = new BoxBlur();
            boxBlur.setWidth(5);
            boxBlur.setHeight(5);
            boxBlur.setIterations(3);
            general_pane.setEffect(boxBlur);


        }
    }

    public void modifierpass() throws IOException {
        u.modifyPassword(userID,tf_passwordUpdate.getText());
        /*Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Stage window = (Stage) savePassword.getScene().getWindow();
        window.setScene(new Scene(root,1098,667));*/
        Setup_Page("views/login.fxml",savePassword);

        general_pane.setEffect(null);



    }


    public void Setup_Page(String path,Button Btn) throws IOException {
        // Load the loading screen
        FXMLLoader loadingFXMLLoader = new FXMLLoader(Main.class.getResource("views/loading.fxml"));
        Parent loadingRoot = loadingFXMLLoader.load();
        Stage loadingStage = new Stage();
        loadingStage.initModality(Modality.APPLICATION_MODAL);
        loadingStage.initStyle(StageStyle.UNDECORATED);
        loadingStage.setScene(new Scene(loadingRoot));
        loadingStage.setX(1100);
        loadingStage.setY(445);
        loadingStage.show();

        // Load the actual content in the background
        Task<Void> loadContentTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(path));
                Parent root = fxmlLoader.load();
                Platform.runLater(() -> {
                    // Switch to the loaded content
                    Stage window = (Stage) Btn.getScene().getWindow();
                    window.setScene(new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT));
                    // Close the loading screen
                    loadingStage.close();
                });
                return null;
            }
        };
        Thread thread = new Thread(loadContentTask);
        thread.setDaemon(true);
        thread.start();
    }

}
