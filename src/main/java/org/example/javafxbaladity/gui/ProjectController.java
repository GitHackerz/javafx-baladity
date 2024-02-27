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
import org.example.javafxbaladity.utils.Modals;

import java.io.IOException;
import java.sql.Date;

public class ProjectController {

    ProjectService projectService = new ProjectService();
    Project selectedProject = null;

    @FXML
    private TableView<Project> Projects_Table;

    @FXML
    private TableColumn<Project, Float> budget_col;

    @FXML
    private TableColumn<Project, Date> date_debut_col, date_fin_col;

    @FXML
    private TableColumn<Project, String> title_col, description_col, statut_col;

    @FXML
    private TableColumn<Project, Integer> id_col;

    @FXML
    private Text numOfProjects, numOfActiveProjects, numOfInactiveProjects, statut_txt;

    @FXML
    private Button Home_Btn, User_Btn, Document_Btn, Project_Button, Reclamation_Btn, Association_Btn, Event_Btn, detailsProject_Btn, deleteProject_Btn, updateProject_Btn, open_modal_btn, statut_btn;

    @FXML
    void onHomeButtonClick(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("views/home.fxml"));
        Stage stage = (Stage) Home_Btn.getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
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
    void onUpdateProject(ActionEvent event) throws Exception {
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

        BoxBlur blur = new BoxBlur(3, 3, 3);
        Node source = (Node) event.getSource();
        Stage primaryStage = (Stage) source.getScene().getWindow();

        primaryStage.getScene().getRoot().setEffect(blur);

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("views/project/editProject.fxml"));

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);
        stage.setScene(new Scene(loader.load()));

        EditProjectController editProjectController = loader.getController();
        editProjectController.setSelectedProject(selectedProject);

        stage.showAndWait();
        showProjects();

        primaryStage.getScene().getRoot().setEffect(null);
    }

    @FXML
    void onDeleteProject() throws IOException, Exception {
        if (selectedProject == null) {
            Modals.displayError("Please select a project to delete");
            return;
        }
        projectService.delete(selectedProject.getId());
        showProjects();
    }

    @FXML
    void onDetailsProject(ActionEvent event) {

    }

    @FXML
    void onOpenAddModal(ActionEvent event) throws Exception {
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
        showProjects();
        primaryStage.getScene().getRoot().setEffect(null);
    }

    @FXML
    void getProjectRow(MouseEvent event) {
        selectedProject = Projects_Table.getSelectionModel().getSelectedItem();
        statut_txt.setText("Projet (" + selectedProject.getTitre() +"): " + selectedProject.getStatut());
        statut_btn.setDisable(false);
        statut_btn.setText(selectedProject.getStatut().equals("active") ? "Terminer" : "Activer");
    }

    public void showProjects() throws Exception {
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

    @FXML
    void changeStatut(ActionEvent event) throws Exception {
        if (selectedProject == null) {
            Modals.displayError("Please select a project to change its status");
            return;
        }

        if (selectedProject.getStatut().equals("active")) {
            projectService.updateStatut(selectedProject.getId(), "inactive");
        } else {
            projectService.updateStatut(selectedProject.getId(), "active");
        }

        showProjects();

        selectedProject = null;
        statut_txt.setText("");
        statut_btn.setText("Statut");
        statut_btn.setDisable(true);
    }


    public void initialize() throws Exception {
        showProjects();
        numOfProjects.setText(String.valueOf(projectService.readAll().size()));
        numOfActiveProjects.setText(String.valueOf(projectService.readActiveProjects().size()));
        numOfInactiveProjects.setText(String.valueOf(projectService.readInactiveProjects().size()));
        statut_btn.setDisable(true);
    }


}
