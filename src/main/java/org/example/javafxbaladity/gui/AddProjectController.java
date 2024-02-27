package org.example.javafxbaladity.gui;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import org.example.javafxbaladity.models.Project;
import org.example.javafxbaladity.services.ProjectService;
import org.example.javafxbaladity.utils.Modals;

import java.sql.Date;

public class AddProjectController {
    ProjectService projectService = new ProjectService();
    @FXML
    public MFXButton add_project_btn;

    @FXML
    private MFXTextField budget_projet;

    @FXML
    private MFXDatePicker date_debut_projet;

    @FXML
    private MFXDatePicker date_fin_projet;

    @FXML
    private MFXTextField description_projet;

    @FXML
    private MFXTextField nom_projet;

    public void onClose(MouseEvent mouseEvent) {
        add_project_btn.getScene().getWindow().hide();
    }


    public void onMinimize(MouseEvent mouseEvent) {
    }

    public void addProject(ActionEvent actionEvent) {
        String title = nom_projet.getText();
        String description = description_projet.getText();
        float budget = Float.parseFloat(budget_projet.getText());
        Date date_debut = Date.valueOf(date_debut_projet.getValue());
        Date date_fin = Date.valueOf(date_fin_projet.getValue());

        try{
            projectService.create(new Project(title, description,"en cours", budget , date_debut, date_fin ));
        }catch (Exception e){
            System.out.println(e.getMessage());
            Modals.displayError(e.getMessage());
        }

        add_project_btn.getScene().getWindow().hide();
    }
}
