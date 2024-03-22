package org.example.javafxbaladity.gui;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPaginatedTableView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import io.github.palexdev.materialfx.filter.BooleanFilter;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import io.github.palexdev.materialfx.controls.MFXNotificationCenter;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.javafxbaladity.Main;
import org.example.javafxbaladity.Services.EventService;
import org.example.javafxbaladity.models.evenement;
import javafx.scene.control.Button;
import org.example.javafxbaladity.utils.Navigate;


import javax.print.Doc;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.ResourceBundle;

public class EventFrontController implements Initializable {
    EventService ev = new EventService();
    private int ide=0;
    static UserSession us = ApplicationContext.getInstance().getUserSession();
    int userId;
    String userName;
    String userPrenom;
    String userRole;
    public void getUserSession() {
        if (us != null) {
            userId = us.getUserId();
            userName = us.getUserName();
            userPrenom = us.getPrenom();
            userRole = us.getRole();
        }
    }


    int SCREEN_WIDTH = 1200;
    int SCREEN_HEIGHT = 700;


    @FXML
    private MFXTextField rechercheF_Textfield;

    @FXML
    private MFXPaginatedTableView<evenement> tableviewF;
    @FXML
    private Button Documents_Btn;

    @FXML
    private Button Home_Btn;

    @FXML
    private Button Users_Btn,Logout_Btn,Reclamations_Btn,Projects_Btn,Events_Btn,Associations_Btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       rechercheF_Textfield.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                String query = newValue.trim(); // Obtenez le texte de recherche et supprimez les espaces blancs
                ObservableList<evenement> searchResults = ev.searchEvents(query); // Recherchez les événements correspondants
                tableviewF.setItems(searchResults); // Mettez à jour la TableView avec les résultats de la recherche
            } catch (SQLException e) {
                e.printStackTrace(); // Gérez les exceptions de manière appropriée
            }
        });
        try {
            showEvenements();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        getUserSession();

    }



    @FXML
    void onAssociationsButtonClick(ActionEvent event) throws IOException {
        Navigate.toAssociation(Associations_Btn, userRole);
    }

    @FXML
    void onEventsButtonClick(ActionEvent event) throws IOException {
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



    @FXML
    void onDocumentsButtonClick(ActionEvent event) throws IOException {
        Navigate.toDocument(Documents_Btn, userRole);

    }



    @FXML
    void onHomeButtonClick(ActionEvent event) throws IOException {
        Navigate.toHome(Home_Btn);

    }

    @FXML
    void onUserButtonClick(ActionEvent event) throws IOException {
        Navigate.toUser(Users_Btn,userRole);

    }

    @FXML
    void setOnAction(ActionEvent event) {
        /*rechercheF_Textfield.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                String query = newValue.trim(); // Obtenez le texte de recherche et supprimez les espaces blancs
                ObservableList<evenement> searchResults = ev.searchEvents(query); // Recherchez les événements correspondants
                tableviewF.setItems(searchResults); // Mettez à jour la TableView avec les résultats de la recherche

            } catch (SQLException e) {
                e.printStackTrace(); // Gérez les exceptions de manière appropriée
            }
        });*/

    }

    public void showEvenements() throws SQLException {
        MFXTableColumn<evenement> id_eve_col = new MFXTableColumn<>("ID ", false, Comparator.comparing(evenement::getId));
        MFXTableColumn<evenement> titre_eve_col = new MFXTableColumn<>("Titre ", false, Comparator.comparing(evenement::getTitre));
        MFXTableColumn<evenement> description_eve_col = new MFXTableColumn<>("description ", false, Comparator.comparing(evenement::getDescription));
        MFXTableColumn<evenement> date_eve_col = new MFXTableColumn<>("date ", false, Comparator.comparing(evenement::getDate));
        MFXTableColumn<evenement> lieu_eve_col = new MFXTableColumn<>("lieu", false, Comparator.comparing(evenement::getLieu));
        MFXTableColumn<evenement> nomContact_eve_col = new MFXTableColumn<>("nomContact", false, Comparator.comparing(evenement::getNomContact));
        MFXTableColumn<evenement> emailContact_eve_col = new MFXTableColumn<>("emailContact", false, Comparator.comparing(evenement::getEmailContact));
        MFXTableColumn<evenement> statut_eve_col = new MFXTableColumn<>("statut", false, Comparator.comparing(evenement::isStatut));

        // Create CheckBox column



        id_eve_col.setRowCellFactory(device -> new MFXTableRowCell<>(evenement::getId));
        titre_eve_col.setRowCellFactory(device -> new MFXTableRowCell<>(evenement::getTitre));
        description_eve_col.setRowCellFactory(device -> new MFXTableRowCell<>(evenement::getDescription) {{
            setAlignment(Pos.BASELINE_CENTER);
        }});
        date_eve_col.setRowCellFactory(device -> new MFXTableRowCell<>(evenement::getDate));
        lieu_eve_col.setRowCellFactory(device -> new MFXTableRowCell<>(evenement::getLieu));
        id_eve_col.setAlignment(Pos.BASELINE_CENTER);
        nomContact_eve_col.setRowCellFactory(device -> new MFXTableRowCell<>(evenement::getNomContact));
        emailContact_eve_col.setRowCellFactory(device -> new MFXTableRowCell<>(evenement::getEmailContact));
        statut_eve_col.setRowCellFactory(device -> new MFXTableRowCell<>(evenement::isStatut));



        //checkbox_col.setRowCellFactory(device -> new MFXTableRowCell<>(Document::getCheckbox));


        // Add columns to the table
        tableviewF.getTableColumns().addAll( id_eve_col, titre_eve_col, description_eve_col, date_eve_col, lieu_eve_col, nomContact_eve_col,emailContact_eve_col,statut_eve_col);

        tableviewF.getFilters().addAll(
                new IntegerFilter<>("id", evenement::getId),
                new StringFilter<>("titre", evenement::getTitre),
                new StringFilter<>("description", evenement::getDescription),
                new StringFilter<>("date", evenement::getDate),
                new StringFilter<>("lieu", evenement::getLieu),
                new StringFilter<>("nomContact",evenement::getNomContact),
                new StringFilter<>("emailContact", evenement::getEmailContact),
                new BooleanFilter<>("statut", evenement::isStatut)

        );
        tableviewF.setItems(ev.readAll());

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


    @FXML
    void getData(MouseEvent event) {
        evenement even = tableviewF.getSelectionModel().getSelectedValue();
        //evenement even = Levent.get(0);
        ide = even.getId();
    }



















}



