package org.example.javafxbaladity.utils;

import javafx.scene.control.Alert;

public class Modals {
    public static void displayError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error while adding project");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
