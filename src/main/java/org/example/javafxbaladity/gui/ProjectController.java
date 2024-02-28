package org.example.javafxbaladity.gui;

import javafx.beans.property.ReadOnlyStringWrapper;
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
import org.example.javafxbaladity.models.ProjectParticipant;
import org.example.javafxbaladity.services.ProjectParticipantService;
import org.example.javafxbaladity.services.ProjectService;
import org.example.javafxbaladity.utils.Modals;

import java.sql.Date;

public class ProjectController {

    public Button participate_btn;
    public Button tasks_btn;
    public Button Event_Btn;
    ProjectService projectService = new ProjectService();
    ProjectParticipantService projectParticipantService = new ProjectParticipantService();
    Project selectedProject = null;

    private String userRole = "admin";

    @FXML
    private TableView<Project> Projects_Table;

    @FXML
    private TableColumn<Project, Float> budget_col;

    @FXML
    private TableColumn<Project, Date> date_debut_col, date_fin_col;

    @FXML
    private TableColumn<Project, String> title_col, description_col, statut_col, participant_col;

    @FXML
    private Text numOfProjects, numOfActiveProjects, numOfInactiveProjects, statut_txt;

    @FXML
    private Button Home_Btn, User_Btn, Document_Btn, Project_Button, Reclamation_Btn, Association_Btn, Event_Btnf, deleteProject_Btn, updateProject_Btn, open_modal_btn, statut_btn;

    @FXML
    void onHomeButtonClick() throws Exception {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("views/home.fxml"));
        Stage stage = (Stage) Home_Btn.getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
    }

    @FXML
    void onUserButtonClick() {

    }

    @FXML
    void onReclamationButtonClick() {

    }

    @FXML
    void onDocumentsButtonClick() {

    }

    @FXML
    void onEventButtonClick() {

    }

    @FXML
    void onAssociationButtonClick() {

    }

    @FXML
    void onUpdateProject(ActionEvent event) throws Exception {
        if (selectedProject == null) {
            Modals.displayError("Error occurred while updating project", "Please select a project to update");
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
    void onDeleteProject() throws Exception {
        if (selectedProject == null) {
            Modals.displayError("Error occurred while deleting project", "Please select a project to delete");
            return;
        }
        projectService.delete(selectedProject.getId());
        showProjects();
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
    void getProjectRow() {
        selectedProject = Projects_Table.getSelectionModel().getSelectedItem();
        if (statut_txt != null) {
            statut_txt.setText("Projet (" + selectedProject.getTitre() + "): " + selectedProject.getStatut());
            statut_btn.setDisable(false);
            statut_btn.setText(selectedProject.getStatut().equals("active") ? "Terminer" : "Activer");
        }
    }

    public void showProjects() throws Exception {
        ObservableList<Project> projects = FXCollections.observableArrayList(projectService.readAll());

        Projects_Table.setItems(projects);
        title_col.setCellValueFactory(new PropertyValueFactory<>("titre"));
        date_debut_col.setCellValueFactory(new PropertyValueFactory<>("date_debut"));
        description_col.setCellValueFactory(new PropertyValueFactory<>("description"));
        date_fin_col.setCellValueFactory(new PropertyValueFactory<>("date_fin"));
        budget_col.setCellValueFactory(new PropertyValueFactory<>("budget"));
        statut_col.setCellValueFactory(new PropertyValueFactory<>("statut"));
        participant_col.setCellValueFactory(cellData -> {
            try {
                Project project = cellData.getValue();
                return projectParticipantService.readByParticipantAndProject(1, project.getId()) != null ? new ReadOnlyStringWrapper("participant") : new ReadOnlyStringWrapper("non participant");
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
            return null;
        });
    }

    @FXML
    void changeStatut() throws Exception {
        if (selectedProject == null) {
            Modals.displayError("Error occurred while changing statut", "Please select a project to change statut");
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
        if (statut_btn != null)
            statut_btn.setDisable(true);
    }

    public void onParticipate() throws Exception {
        if (selectedProject == null) {
            Modals.displayError("Error occurred while participating", "Please select a project to participate in");
            return;
        }
        if (projectParticipantService.readByParticipantAndProject(1, selectedProject.getId()) != null) {
            Modals.displayError("Error occurred while participating", "You have already participated in this project");
            return;
        }
        projectParticipantService.create(new ProjectParticipant(selectedProject.getId(), 1));
        Modals.displaySuccess("Success", "You have successfully participated in the project");

    }


    public void onTasksDetails() {
    }

    public void onOpenAddTacheModal(ActionEvent event) throws Exception {
        if (selectedProject == null) {
            Modals.displayError("Error occurred while adding task", "Please select a project to add task");
            return;
        }
        BoxBlur blur = new BoxBlur(3, 3, 3);
        Node source = (Node) event.getSource();
        Stage primaryStage = (Stage) source.getScene().getWindow();

        primaryStage.getScene().getRoot().setEffect(blur);

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("views/project/addTask.fxml"));
        Parent root = loader.load();
        AddTaskController addTaskController = loader.getController();
        addTaskController.setProjectId(selectedProject.getId());

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);
        stage.setScene(new Scene(root));

        stage.showAndWait();
        showProjects();
        primaryStage.getScene().getRoot().setEffect(null);
    }


}
