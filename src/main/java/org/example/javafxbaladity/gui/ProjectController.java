package org.example.javafxbaladity.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.javafxbaladity.Main;
import org.example.javafxbaladity.models.Project;
import org.example.javafxbaladity.services.ProjectService;

import java.io.IOException;
import java.time.LocalDateTime;

public class ProjectController {

    ProjectService projectService = new ProjectService();
    Project selectedProject = null;

    @FXML
    private TableColumn<Project, Float> budget_col;

    @FXML
    private TableColumn<Project, LocalDateTime> date_debut_col;

    @FXML
    private TableColumn<Project, LocalDateTime> date_fin_col;

    @FXML
    private TableColumn<Project, String> description_col;

    @FXML
    private TableColumn<Project, Integer> id_col;

    @FXML
    private TableColumn<Project, String> statut_col;

    @FXML
    private TableColumn<Project, String> title_col;

    @FXML
    private Text numOfProjects;

    @FXML
    private Text numOfActiveProjects;

    @FXML
    private Text numOfInactiveProjects;

    @FXML
    private Button Home_Btn, User_Btn, Document_Btn, Project_Button, Reclamation_Btn, Association_Btn, Event_Btn, detailsProject_Btn, deleteProject_Btn, updateProject_Btn, open_modal_btn;

    @FXML
    private TableView<Project> Projects_Table;

    @FXML
    void onHomeButtonClick(ActionEvent event) {

    }

    @FXML
    void onUserButtonClick(ActionEvent event) {

    }

    @FXML
    void onReclamationButtonClick(ActionEvent event) {

    }

    @FXML
    void onDocumentsButtonClick(ActionEvent event) {

    }

    @FXML
    void onEventButtonClick(ActionEvent event) {

    }

    @FXML
    void onAssociationButtonClick(ActionEvent event) {

    }

    @FXML
    void onUpdateProject(ActionEvent event) {
    }

    @FXML
    void onDeleteProject(ActionEvent event) throws IOException {
        if (selectedProject == null) {
            Stage stage = new Stage();
            stage.setTitle("Error");
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("views/alert.fxml"));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setResizable(false);
            stage.setScene(new Scene(loader.load()));
            stage.show();
            stage.setX(600);
            stage.setY(400);
            return;
        }
        projectService.delete(selectedProject.getId());
        showProjects();
    }

    @FXML
    void onDetailsProject(ActionEvent event) {

    }

    @FXML
    void onOpenAddModal(ActionEvent event) throws IOException {
        BoxBlur blur = new BoxBlur(3, 3, 3);
        Node source = (Node) event.getSource();
        Stage primaryStage = (Stage) source.getScene().getWindow();

        primaryStage.getScene().getRoot().setEffect(blur);

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("views/project/addProject.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);
        stage.setScene(new Scene(root));

        stage.showAndWait();

        primaryStage.getScene().getRoot().setEffect(null);
    }

    @FXML
    void getProjectRow(MouseEvent event) {
        selectedProject = Projects_Table.getSelectionModel().getSelectedItem();
    }

    public void showProjects() {
        ObservableList<Project> projects = FXCollections.observableArrayList(projectService.readAll());

        Projects_Table.setItems(projects);
        id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        title_col.setCellValueFactory(new PropertyValueFactory<>("titre"));
        description_col.setCellValueFactory(new PropertyValueFactory<>("description"));
        date_debut_col.setCellValueFactory(new PropertyValueFactory<>("date_debut"));
        date_fin_col.setCellValueFactory(new PropertyValueFactory<>("date_fin"));
        budget_col.setCellValueFactory(new PropertyValueFactory<>("budget"));
        statut_col.setCellValueFactory(new PropertyValueFactory<>("statut"));

    }

    public void initialize() {
        showProjects();
        numOfProjects.setText(String.valueOf(projectService.readAll().size()));
        numOfActiveProjects.setText(String.valueOf(projectService.readActiveProjects().size()));
        numOfInactiveProjects.setText(String.valueOf(projectService.readInactiveProjects().size()));
    }


}
