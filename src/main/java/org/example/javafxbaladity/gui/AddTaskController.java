package org.example.javafxbaladity.gui;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.example.javafxbaladity.models.ProjectTask;
import org.example.javafxbaladity.services.ProjectTaskService;
import org.example.javafxbaladity.utils.KeyValuePair;
import org.example.javafxbaladity.utils.Modals;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddTaskController {
    private int project_id;
    private final ProjectTaskService projectTaskService = new ProjectTaskService();

    @FXML
    private MFXButton add_task_btn;

    @FXML
    private MFXDatePicker date_input;

    @FXML
    private MFXTextField description_input;

    @FXML
    private MFXTextField titre_input;

    @FXML
    private MFXComboBox<KeyValuePair<Integer>> user_input;

    @FXML
    private Label description_error;

    @FXML
    private Label date_error;

    @FXML
    private Label titre_error;

    @FXML
    private Label user_error;

    @FXML
    void addTask() {
        boolean isError = false;

        resetInputsErrors();

        if (titre_input.getText().isEmpty()) {
            titre_error.setText("Title is required");
            isError = true;
        }

        if (description_input.getText().isEmpty()) {
            description_error.setText("Description is required");
            isError = true;
        }

        if (date_input.getValue() == null) {
            date_error.setText("Date is required");
            isError = true;
        }

        if (date_input.getValue() != null && date_input.getValue().isBefore(LocalDate.now())) {
            date_error.setText("Date should be in the future");
            isError = true;
        }

        if (user_input.getSelectionModel().getSelectedItem() == null) {
            user_error.setText("User is required");
            isError = true;
        }

        if (isError) {
            return;
        }

        try {
            projectTaskService.addTask(
                    new ProjectTask(
                            titre_input.getText(),
                            description_input.getText(),
                            Date.valueOf(date_input.getValue()),
                            ProjectTask.Statut.TODO,
                            project_id,
                            user_input.getSelectionModel().getSelectedItem().getActualValue()
                    )
            );
            add_task_btn.getScene().getWindow().hide();
            Modals.displaySuccess("Task added successfully", "Task has been added successfully");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void onClose() {
        add_task_btn.getScene().getWindow().hide();
    }

    @FXML
    void onMinimize(ActionEvent event) {
    }

    public int getProjectId() {
        return project_id;
    }

    public void setProjectId(int project_id) {
        this.project_id = project_id;
    }

    public void initialize() {
        date_input.setValue(LocalDate.now());
        List<KeyValuePair<Integer>> users = new ArrayList<>();
        users.add(new KeyValuePair<>("User 1", 1));
        users.add(new KeyValuePair<>("User 2", 2));
        users.add(new KeyValuePair<>("User 3", 3));
        user_input.getItems().addAll(users);
    }

    private void resetInputsErrors() {
        titre_error.setText("");
        description_error.setText("");
        date_error.setText("");
        user_error.setText("");
    }
}