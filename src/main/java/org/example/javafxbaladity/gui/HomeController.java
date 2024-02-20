package org.example.javafxbaladity.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.example.javafxbaladity.Main;

public class HomeController {

    @FXML
    Button Users_Btn, Home_Btn, Documents_Btn;

    public void onHomeButtonClick() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/home.fxml"));
        Parent root = fxmlLoader.load();
        Stage window = (Stage) Home_Btn.getScene().getWindow();
        window.setScene(new Scene(root, 1098, 667));
    }

    public void onUserButtonClick() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/users.fxml"));
        Parent root = fxmlLoader.load();
        Stage window = (Stage) Users_Btn.getScene().getWindow();
        window.setScene(new Scene(root, 1098, 667));
    }

    public void onDocumentsButtonClick() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/documents.fxml"));
        Parent root = fxmlLoader.load();
        Stage window = (Stage) Documents_Btn.getScene().getWindow();
        window.setScene(new Scene(root, 1098, 667));
    }

    public void initialize() {
           System.out.println("Home Controller Initialized");
    }


}