package org.example.javafxbaladity.gui;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class AlertController {
    @FXML
    private ImageView close_alert_btn;

    @FXML
    private Text AlertText;

    @FXML
    void closeAlert(MouseEvent event) {
        close_alert_btn.getScene().getWindow().hide();
    }
}
