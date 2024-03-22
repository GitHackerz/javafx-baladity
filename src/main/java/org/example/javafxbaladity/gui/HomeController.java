package org.example.javafxbaladity.gui;

import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.example.javafxbaladity.Main;
import org.example.javafxbaladity.Services.CitoyenService;
import org.example.javafxbaladity.Services.Document_request_ServiceDoc;
import org.example.javafxbaladity.Services.UserService;
import org.example.javafxbaladity.utils.Navigate;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class HomeController implements Initializable {
    //Calling for user session

    CitoyenService c = new CitoyenService();
    UserService u = new UserService();
    static UserSession us = ApplicationContext.getInstance().getUserSession();


    int userId;
    String userName;
    String userPrenom;
    String userRole;
    public void getUserSession() {
        UserSession userSession = ApplicationContext.getInstance().getUserSession();
        if (userSession != null) {
            userId = userSession.getUserId();
            userName = userSession.getUserName();
            userPrenom = userSession.getPrenom();
            userRole = userSession.getRole();
        }
    }


    int SCREEN_WIDTH = 1200;
    int SCREEN_HEIGHT = 700;


    @FXML
    Button Users_Btn, Home_Btn, Documents_Btn;

    @FXML
    private Button Associations_Btn;


    @FXML
    private Button Events_Btn;


    @FXML
    private Button Logout_Btn;

    @FXML
    private Button Projects_Btn;

    @FXML
    private Button Reclamations_Btn;

    @FXML
    private Pane statuser;


    @FXML
    private PieChart pieChaet_ddoc;


































































































    Document_request_ServiceDoc ddoc_service = new Document_request_ServiceDoc();

    @FXML
    void onAssociationsButtonClick(ActionEvent event) throws IOException {
        if (userRole.equals("employe"))
        {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/association/association.fxml"));
            Parent root = fxmlLoader.load();
            Stage window = (Stage) Associations_Btn.getScene().getWindow();
            window.setScene(new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT));

        }

        else
        {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/association/association.fxml"));
        Parent root = fxmlLoader.load();
        Stage window = (Stage) Associations_Btn.getScene().getWindow();
        window.setScene(new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT));  }  }
    @FXML
    private Label citoyen1;

    @FXML
    private Label female;

    @FXML
    private Label labelEmp;

    @FXML
    private Label labelUsers;

    @FXML
    private Label males;
    @FXML
    void onEventsButtonClick(ActionEvent event) throws IOException {
        if(userRole.equals("employe"))
        {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/events/events.fxml"));
            Parent root = fxmlLoader.load();
            Stage window = (Stage) Events_Btn.getScene().getWindow();
            window.setScene(new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT));
        }
        else
        {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/events/eventsfront.fxml"));
            Parent root = fxmlLoader.load();
            Stage window = (Stage) Events_Btn.getScene().getWindow();
            window.setScene(new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT));
        }
        //Navigate.toEvent(Events_Btn, userRole);
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getUserSession();
        try {
            citoyen1.setText(String.valueOf(c.countCitoyens()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            labelUsers.setText(String.valueOf(u.countusers()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            labelEmp.setText(String.valueOf(u.countUsersEMP()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            female.setText(String.valueOf(c.countCitoyenFemale()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            males.setText(String.valueOf(c.countCitoyenMale()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }




//        ObservableList<PieChart.Data> pieChartData = null;
//        try {
//            pieChartData = FXCollections.observableArrayList(
//                    new PieChart.Data("Pending", ddoc_service.getNbenAttente()),
//                    new PieChart.Data("Accepted", ddoc_service.getNbAccepted()),
//                    new PieChart.Data("Rejected", ddoc_service.getNbRejected()));
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//
//        pieChartData.forEach(data ->
//                data.nameProperty().bind(
//                        Bindings.concat(
//                                data.getName(), " amount: ", data.pieValueProperty()
//                        )
//                )
//        );
//        //pieChaet_ddoc.getData().clear(); // Clear existing data
//        pieChaet_ddoc.getData().addAll(pieChartData); // Add new data



    }
    }
