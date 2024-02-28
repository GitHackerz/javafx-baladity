package org.example.javafxbaladity.gui;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.example.javafxbaladity.models.Project;
import org.example.javafxbaladity.services.ProjectService;
import org.example.javafxbaladity.utils.Modals;

import java.sql.Date;
import java.time.LocalDate;

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

    @FXML
    private Label budget_projet_error;

    @FXML
    private Label date_debut_projet_error;

    @FXML
    private Label date_fin_projet_error;

    @FXML
    private Label description_projet_error;

    @FXML
    private Label nom_projet_error;

    public void onClose() {
        add_project_btn.getScene().getWindow().hide();
    }


    public void onMinimize() {
    }

    public void addProject() {
        boolean isError = false;
        resetInputsErrors();
        if (nom_projet.getText().isEmpty()) {
            nom_projet_error.setText("This field is required");
            isError = true;
        }

        if (description_projet.getText().isEmpty()) {
            description_projet_error.setText("This field is required");
            isError = true;
        }

        if (budget_projet.getText().isEmpty()) {
            budget_projet_error.setText("This field is required");
            isError = true;
        }

        //check if budget is number
        try {
            Float.parseFloat(budget_projet.getText());
        } catch (NumberFormatException e) {
            budget_projet_error.setText("This field should be a number");
            isError = true;
        }

        if (date_debut_projet.getValue() == null) {
            date_debut_projet_error.setText("This field is required");
            isError = true;
        }

        if (date_fin_projet.getValue() == null) {
            date_fin_projet_error.setText("This field is required");
            isError = true;
        }

        if (date_debut_projet.getValue() != null && date_fin_projet.getValue() != null) {
            if (date_fin_projet.getValue().isBefore(date_debut_projet.getValue())) {
                date_fin_projet_error.setText("This field should be after date debut");
                isError = true;
            }
        }

        if (isError)
            return;

        String title = nom_projet.getText();
        String description = description_projet.getText();
        float budget = Float.parseFloat(budget_projet.getText());
        Date date_debut = Date.valueOf(date_debut_projet.getValue());
        Date date_fin = Date.valueOf(date_fin_projet.getValue());

        try {
            projectService.create(new Project(title, description, "inactve", budget, date_debut, date_fin));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Modals.displayError("Error occurred while adding project", e.getMessage());
        }

        add_project_btn.getScene().getWindow().hide();
    }

    private void resetInputsErrors() {
        nom_projet_error.setText("");
        description_projet_error.setText("");
        budget_projet_error.setText("");
        date_debut_projet_error.setText("");
        date_fin_projet_error.setText("");
    }

    public void initialize() {
        date_debut_projet.setValue(LocalDate.now());
        date_fin_projet.setValue(LocalDate.now().plusYears(1));
    }

}
