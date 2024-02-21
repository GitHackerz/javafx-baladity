package org.example.javafxbaladity.gui;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

public class AddProjectController {
    public MFXButton add_project_btn;

    public void onClose(MouseEvent mouseEvent) {
        add_project_btn.getScene().getWindow().hide();
    }


    public void onMinimize(MouseEvent mouseEvent) {
    }

    public void addProject(ActionEvent actionEvent) {
    }
}
