package org.example.javafxbaladity.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.example.javafxbaladity.Main;

public class HomeController {

    @FXML
    Button Users_Btn, Home_Btn, Documents_Btn, Project_Btn;

    int width = 1200;
    int height = 700;

    public void onHomeButtonClick() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/home.fxml"));
        Parent root = fxmlLoader.load();
        Stage window = (Stage) Home_Btn.getScene().getWindow();
        window.setScene(new Scene(root, width, height));
    }

    public void onUserButtonClick() throws Exception {

    }

    public void onDocumentsButtonClick() throws Exception {

    }


    public void onProjectButtonClick(ActionEvent actionEvent) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/project/project_as_admin.fxml"));
        Parent root = fxmlLoader.load();
        Stage window = (Stage) Project_Btn.getScene().getWindow();
        window.setScene(new Scene(root, width, height));
    }
}