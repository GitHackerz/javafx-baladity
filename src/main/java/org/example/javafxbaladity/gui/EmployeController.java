package org.example.javafxbaladity.gui;


import io.github.palexdev.materialfx.controls.MFXPaginatedTableView;
import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import io.github.palexdev.materialfx.filter.BooleanFilter;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;
import org.example.javafxbaladity.Main;
import org.example.javafxbaladity.Services.CitoyenService;
import org.example.javafxbaladity.Services.UserService;
import org.example.javafxbaladity.models.Citoyen;
import org.example.javafxbaladity.models.Document;
import org.example.javafxbaladity.models.User;
import org.example.javafxbaladity.utils.Navigate;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.ResourceBundle;


public class EmployeController implements Initializable {

    int SCREEN_WIDTH = 1200;
    int SCREEN_HEIGHT = 700;

    static UserSession us = ApplicationContext.getInstance().getUserSession();
    int userId;
    String userName;
    String userPrenom;
    String userRole;

    CitoyenService c6 = new CitoyenService() ;
    UserService u = new UserService() ;

    @FXML
    private Button Documents_Btn;

    @FXML
    private Button Home_Btn;

    @FXML
    private Button Users_Btn;

    @FXML
    private TableView<User> tv_Employe;

    @FXML
    private TableColumn<User, String> tv_HeureDebut_Employe, tv_HeureFin_Employe, tv_role_Emplye, tv_Action_Emplye;

    @FXML
    private TableColumn<User, Integer> tv_IdCitoyen_Employe, tv_id_Employe;

    @FXML
    private MFXPaginatedTableView<User> tv_employee;

    @FXML
    private MFXTextField search;

    @FXML
    private Button Associations_Btn, Events_Btn, Logout_Btn, Projects_Btn, Reclamations_Btn;

    @FXML
    void onAssociationsButtonClick(ActionEvent event) throws IOException {
        Navigate.toAssociation(Associations_Btn, userRole);
    }

    @FXML
    void onEventsButtonClick(ActionEvent event) throws IOException {
        Navigate.toEvent(Events_Btn, userRole);
    }

    @FXML
    void onProjectsButtonClick(ActionEvent event) throws IOException {
        Navigate.toProject(Projects_Btn, userRole);
    }

    @FXML
    void onReclamationsButtonClick(ActionEvent event) throws IOException {
        Navigate.toReclamation(Reclamations_Btn, userRole);
    }

    public void onHomeButtonClick() throws Exception {
        Navigate.toHome(Home_Btn);
    }

    public void onUserButtonClick() throws Exception {
        Navigate.toUser(Users_Btn, userRole);
    }

    public void onDocumentsButtonClick() throws Exception {
        Navigate.toDocument(Documents_Btn, userRole);
    }

    public void getUserSession() {
        UserSession userSession = ApplicationContext.getInstance().getUserSession();
        if (userSession != null) {
            userId = userSession.getUserId();
            userName = userSession.getUserName();
            userPrenom = userSession.getPrenom();
            userRole = userSession.getRole();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //showEmploye();
        try {
            showUsers2();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    private void DynamicFilter() throws SQLException {
        /*----------------DynamicSearch---------------------------*/
        FilteredList<User> filterData = new FilteredList<>(u.getEmployeList());
        search.textProperty().addListener((observable, oldValue, newValue) -> {
            filterData.setPredicate(User -> {
                if (newValue.isEmpty() || newValue.isBlank()) {
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();
                if (String.valueOf(User.getId()).toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (User.getRole().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (User.getHeureDebut().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (User.getHeureFin().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else return String.valueOf(User.getIdCitoyen()).toLowerCase().contains(searchKeyword);
            });
        });

        SortedList<User> sortedData = new SortedList<>(filterData);
        sortedData.comparatorProperty().bind(tv_employee.getTransformableList().comparatorProperty());
        tv_employee.setItems(sortedData);
    }
    private void showUsers2() throws SQLException {
        MFXTableColumn<User> tv_id_Employe = new MFXTableColumn<>("ID Document", false, Comparator.comparing(User::getId));
        MFXTableColumn<User> tv_role_Emplye = new MFXTableColumn<>("role ", false, Comparator.comparing(User::getRole));
        MFXTableColumn<User> tv_HeureDebut_Employe = new MFXTableColumn<>("heure debut ", false, Comparator.comparing(User::getHeureDebut));
        MFXTableColumn<User> tv_HeureFin_Employe = new MFXTableColumn<>("heure fin ", false, Comparator.comparing(User::getHeureFin));
        MFXTableColumn<User> tv_IdCitoyen_Employe = new MFXTableColumn<>("id citoyen", false, Comparator.comparing(User::getIdCitoyen));


        tv_id_Employe.setRowCellFactory(device -> new MFXTableRowCell<>(User::getId));
        tv_role_Emplye.setRowCellFactory(device -> new MFXTableRowCell<>(User::getRole) {{
            setFont(Font.font("System", FontWeight.BOLD, 14));
        }});
        tv_HeureDebut_Employe.setRowCellFactory(device -> new MFXTableRowCell<>(User::getHeureDebut) {{
            setAlignment(Pos.BASELINE_CENTER);
            //setStyle("-fx-background-color: red");
            setHeight(200);
            setWidth(200);
        }});
        tv_HeureFin_Employe.setRowCellFactory(device -> new MFXTableRowCell<>(User::getHeureFin));
        tv_IdCitoyen_Employe.setRowCellFactory(device -> new MFXTableRowCell<>(User::getIdCitoyen));



        //ADJUSTING THE COLUMNS WIDTH
        tv_id_Employe.prefWidthProperty().bind(tv_employee.widthProperty().multiply(0.10)); // 15% of width
        tv_role_Emplye.prefWidthProperty().bind(tv_employee.widthProperty().multiply(0.25)); // 15% of width
        tv_HeureDebut_Employe.prefWidthProperty().bind(tv_employee.widthProperty().multiply(0.1)); // 15% of width
        tv_HeureFin_Employe.prefWidthProperty().bind(tv_employee.widthProperty().multiply(0.15)); // 20% of width
        tv_IdCitoyen_Employe.prefWidthProperty().bind(tv_employee.widthProperty().multiply(0.15)); // 20% of width


        // Add columns to the table
        tv_employee.getTableColumns().addAll(tv_id_Employe, tv_role_Emplye, tv_HeureDebut_Employe, tv_HeureFin_Employe, tv_IdCitoyen_Employe);

        tv_employee.getFilters().addAll(
                new IntegerFilter<>("ID", User::getId),
                new StringFilter<>("Role", User::getRole),
                new StringFilter<>("Heure debut", User::getHeureDebut),
                new StringFilter<>("Heure Fin", User::getHeureFin),
                new IntegerFilter<>("ID Citoyen", User::getIdCitoyen)
        );
        tv_employee.setItems(u.getEmployeList());
        DynamicFilter();

    }

    @FXML
    void onLogoutButtonClick(ActionEvent event) throws IOException {
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

}
