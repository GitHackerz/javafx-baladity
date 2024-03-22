package org.example.javafxbaladity.gui;

import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.example.javafxbaladity.Main;
import org.example.javafxbaladity.Services.*;
import org.example.javafxbaladity.models.*;
import org.example.javafxbaladity.utils.KeyValuePair;
import org.example.javafxbaladity.utils.Modals;
import org.example.javafxbaladity.utils.Navigate;
import org.example.javafxbaladity.utils.TaskUpdateListener;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;


public class ProjectController implements TaskUpdateListener {
    public static final int SCREEN_WIDTH = 1200;
    public static final int SCREEN_HEIGHT = 700;

    @FXML
    private Button Association_Btn, Event_Btn, Logout_Btn, Reclamation_Btn, User_Btn, Document_Btn;
    public Button participate_btn;
    public Button tasks_btn;
    ProjectService projectService = new ProjectService();
    ProjectTaskService projectTaskService = new ProjectTaskService();
    ProjectParticipantService projectParticipantService = new ProjectParticipantService();
    CitoyenService citoyenService = new CitoyenService();
    UserService userService = new UserService();
    Project selectedProject = null;

    @FXML
    private MFXScrollPane done_tasks;

    @FXML
    private MFXScrollPane inprogress_tasks;

    @FXML
    private MFXScrollPane todo_tasks;

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
    private Button Home_Btn, statut_btn;

    @FXML
    private MFXComboBox<KeyValuePair<Integer>> user_select;

    String userRole;

    @FXML
    void onAssociationButtonClick(ActionEvent event) throws IOException {
        Navigate.toAssociation(Association_Btn, userRole);
    }

    @FXML
    void onEventButtonClick(ActionEvent event) throws IOException {
        Navigate.toEvent(Event_Btn, userRole);
    }

    @FXML
    void onProjectsButtonClick(ActionEvent event) throws IOException {
       // Navigate.toProject(Project_Btn, userRole);
    }

    @FXML
    void onReclamationButtonClick(ActionEvent event) throws IOException {
        Navigate.toReclamation(Reclamation_Btn, userRole);
    }
    @FXML

    public void onHomeButtonClick() throws Exception {
        Navigate.toHome(Home_Btn);
    }
    @FXML

    public void onUserButtonClick() throws Exception {
        Navigate.toUser(User_Btn, userRole);
    }
    @FXML

    public void onDocumentsButtonClick() throws Exception {
        Navigate.toDocument(Document_Btn, userRole);
    }

    public void getUserSession() {
        UserSession userSession = ApplicationContext.getInstance().getUserSession();
        if (userSession != null) {
            userRole = userSession.getRole();
        }
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
        List<User> users = userService.getUsersList();

        for (User user : users) {
            Citoyen citoyen = citoyenService.getCitoyen(user.getIdCitoyen());
            user_select.getItems().addAll(new KeyValuePair<>(citoyen.getNomCitoyen(), user.getId()));
        }
        numOfProjects.setText(String.valueOf(projectService.readAll().size()));
        numOfActiveProjects.setText(String.valueOf(projectService.readActiveProjects().size()));
        numOfInactiveProjects.setText(String.valueOf(projectService.readInactiveProjects().size()));
        if (statut_btn != null)
            statut_btn.setDisable(true);

        showProjects();
        getUserSession();
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

    @FXML
    public void showTasks() {
        System.out.println("show");
        VBox toDovBox = new VBox();
        toDovBox.setSpacing(10);
        toDovBox.setAlignment(Pos.CENTER);
        toDovBox.setPrefWidth(230);
        toDovBox.setMaxHeight(100);

        VBox inProgressvBox = new VBox();
        inProgressvBox.setSpacing(10);
        inProgressvBox.setAlignment(Pos.CENTER);
        inProgressvBox.setPrefWidth(230);
        inProgressvBox.setMaxHeight(100);

        VBox donevBox = new VBox();
        donevBox.setSpacing(10);
        donevBox.setAlignment(Pos.CENTER);
        donevBox.setPrefWidth(230);
        donevBox.setMaxHeight(100);

        try {
            List<ProjectTask> tasks = projectTaskService.getTasksByUser(user_select.getSelectionModel().getSelectedItem().getActualValue());

            for (ProjectTask task : tasks) {
                FXMLLoader loader = new FXMLLoader(Main.class.getResource("views/project/task.fxml"));
                Parent taskLoader = loader.load();
                TaskController taskController = loader.getController();

                taskController.setTask(task);
                taskController.setUpdateListener(this);

                switch (task.getStatut()) {
                    case TODO:
                        toDovBox.getChildren().add(taskLoader);
                        break;
                    case IN_PROGRESS:
                        inProgressvBox.getChildren().add(taskLoader);
                        break;
                    case DONE:
                        donevBox.getChildren().add(taskLoader);
                        break;
                }
            }

            todo_tasks.setContent(toDovBox);
            inprogress_tasks.setContent(inProgressvBox);
            done_tasks.setContent(donevBox);
        } catch (Exception e) {
            System.err.println("error: " + e.getMessage());
        }
    }

    public void openCalendar() throws SQLException {
        CalendarController.openCalendar();
    }

    public void onLogoutButtonClick(ActionEvent event) throws IOException {
        MFXProgressSpinner progressIndicator = new MFXProgressSpinner();
        progressIndicator.setPrefSize(70, 70);
        progressIndicator.setStyle("-fx-progress-color: #0C162C;");
        VBox container = new VBox(progressIndicator);
        container.setAlignment(Pos.CENTER); // Center align the content
        Stage window = (Stage) Logout_Btn.getScene().getWindow();
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), window.getScene().getRoot());
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/login.fxml"));
        Parent root = fxmlLoader.load();
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), root);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        // Timeline for the progressIndicator
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.5), eventt -> {
                    window.setScene(new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT));
                    fadeIn.play();
                })
        );
        fadeOut.setOnFinished(eventt -> timeline.play());
        fadeOut.play();
        window.setScene(new Scene(container, SCREEN_WIDTH, SCREEN_HEIGHT));
    }

    @Override
    public void onTaskUpdated(ProjectTask task) {
        showTasks();
    }
}
