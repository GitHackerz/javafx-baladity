package org.example.javafxbaladity.gui;

import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import io.github.palexdev.materialfx.filter.BooleanFilter;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.example.javafxbaladity.Main;
import org.example.javafxbaladity.Services.EventService;
import org.example.javafxbaladity.Services.MembreService;
import org.example.javafxbaladity.models.evenement;
import org.example.javafxbaladity.utils.Navigate;


import javax.mail.MessagingException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;


public class EventController implements Initializable {
    MembreService ms = new MembreService();
    public static evenement selectedEvent;
    int SCREEN_WIDTH = 1200;
    int SCREEN_HEIGHT = 700;

    @FXML
    private MFXPaginatedTableView<evenement> tableviewE;

    @FXML
    private MFXTextField recherche_Textfield;

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
    private Label labelLieu;

    @FXML
    private Label labelNOm;

    @FXML
    private MFXGenericDialog DialogN;

    @FXML
    private MFXButton showMemberStatistics;

    EventService ev = new EventService();

    EventService eventService = new EventService();

    static UserSession us = ApplicationContext.getInstance().getUserSession();

    String userRole;

    public void getUserSession() {
        UserSession userSession = ApplicationContext.getInstance().getUserSession();
        if (userSession != null) {
            userRole = userSession.getRole();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getUserSession();
        DialogN.setVisible(false);
        try {
            showEvenements();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        recherche_Textfield.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                String query = newValue.trim(); // Obtenez le texte de recherche et supprimez les espaces blancs
                ObservableList<evenement> searchResults = eventService.searchEvents(query); // Recherchez les événements correspondants
                tableviewE.setItems(searchResults); // Mettez à jour la TableView avec les résultats de la recherche
            } catch (SQLException e) {
                System.err.println(e.getMessage()); // Gérez les exceptions de manière appropriée
            }
        });

        try {
            checkForTodayEvents();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }


        showMemberStatistics.setOnAction(event -> {
            try {
                showMemberStatistics(event);
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        });

//        try {
//            eventService.sendEmailForTodayEvents();
//        } catch (MessagingException e) {
//            System.err.println(e.getMessage());
//        }
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

        // Add columns to the table
        tableviewE.getTableColumns().addAll(id_eve_col, titre_eve_col, description_eve_col, date_eve_col, lieu_eve_col, nomContact_eve_col, emailContact_eve_col, statut_eve_col);

        tableviewE.getFilters().addAll(
                new IntegerFilter<>("id", evenement::getId),
                new StringFilter<>("titre", evenement::getTitre),
                new StringFilter<>("description", evenement::getDescription),
                new StringFilter<>("date", evenement::getDate),
                new StringFilter<>("lieu", evenement::getLieu),
                new StringFilter<>("nomContact", evenement::getNomContact),
                new StringFilter<>("emailContact", evenement::getEmailContact),
                new BooleanFilter<>("statut", evenement::isStatut)
        );
        tableviewE.setItems(ev.readAll());

    }
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
    void onEventAddBtnClick(ActionEvent event) throws IOException, SQLException {
        BoxBlur blur = new BoxBlur(3, 3, 3);
        Node source = (Node) event.getSource();
        Stage primaryStage = (Stage) source.getScene().getWindow();

        primaryStage.getScene().getRoot().setEffect(blur);

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("views/events/addEvent.fxml"));
        Parent root = loader.load();

        AddEventController controller = loader.getController();
        controller.setSelectedEvent(selectedEvent);

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);
        stage.setScene(new Scene(root));

        stage.showAndWait();
        refreshTABLEviewEvent();
        primaryStage.getScene().getRoot().setEffect(null);
    }

    @FXML
    void onEventDeleteBtnClick(ActionEvent event) throws SQLException {
        ev.delete(selectedEvent.getId());
        refreshTABLEviewEvent();

    }

    @FXML
    void onEventUpdateBtnClick(ActionEvent event) throws SQLException, IOException {
        BoxBlur blur = new BoxBlur(3, 3, 3);
        Node source = (Node) event.getSource();
        Stage primaryStage = (Stage) source.getScene().getWindow();

        primaryStage.getScene().getRoot().setEffect(blur);

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("views/events/addEvent.fxml"));
        Parent root = loader.load();

        AddEventController controller = loader.getController();
        controller.setSelectedEvent(selectedEvent);

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);
        stage.setScene(new Scene(root));

        stage.showAndWait();
        refreshTABLEviewEvent();
        primaryStage.getScene().getRoot().setEffect(null);
    }

    private void refreshTABLEviewEvent() throws SQLException {

        tableviewE.setItems(ev.readAll());
    }

    @FXML
    public void getData(javafx.scene.input.MouseEvent mouseEvent) {
        selectedEvent = tableviewE.getSelectionModel().getSelectedValue();
    }

    @FXML
    void setOnAction(ActionEvent event) {
        recherche_Textfield.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                String query = newValue.trim(); // Obtenez le texte de recherche et supprimez les espaces blancs
                ObservableList<evenement> searchResults = ev.searchEvents(query); // Recherchez les événements correspondants
                tableviewE.setItems(searchResults); // Mettez à jour la TableView avec les résultats de la recherche

            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        });
    }

    @FXML
    void DetailsClick(ActionEvent event) throws SQLException, IOException {
        BoxBlur blur = new BoxBlur(3, 3, 3);
        Node source = (Node) event.getSource();
        Stage primaryStage = (Stage) source.getScene().getWindow();

        primaryStage.getScene().getRoot().setEffect(blur);

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("views/events/membersList.fxml"));
        Parent root = loader.load();

        MembersListController controller = loader.getController();
        controller.setSelectedEvent(selectedEvent);

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);
        stage.setScene(new Scene(root));

        stage.showAndWait();
        refreshTABLEviewEvent();
        primaryStage.getScene().getRoot().setEffect(null);

        refreshTABLEviewEvent();

    }

    @FXML
    public void onCheckTodayEvents(javafx.scene.input.MouseEvent mouseEvent) {
    }

    private void checkForTodayEvents() throws SQLException {
        List<evenement> events = eventService.getTodayEvents(); // Obtenez les événements pour la date d'aujourd'hui

        if (!events.isEmpty()) { // S'il y a des événements pour aujourd'hui
            // Affichez le dialogN et mettez à jour les labels avec les valeurs du premier événement trouvé
            DialogN.setVisible(true);
            labelLieu.setText(events.getFirst().getLieu());
            labelNOm.setText(events.getFirst().getTitre());
        }
    }

    @FXML
    void onbtnNotif(ActionEvent event) {
        DialogN.setVisible(false);

    }

    @FXML
    void showMemberStatistics(ActionEvent event) throws SQLException {
        Map<Integer, Integer> statistics = ms.getMemberStatisticsByEvent();

        // Créer un graphique à barres
        BarChart<String, Number> barChart = new BarChart<>(new CategoryAxis(), new NumberAxis());

        // Ajouter les données au graphique à barres
        for (Map.Entry<Integer, Integer> entry : statistics.entrySet()) {
            int eventId = entry.getKey();
            String eventName = ev.getEventNameById(eventId); // Assurez-vous d'implémenter cette méthode
            int memberCount = entry.getValue();

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName(eventName);
            series.getData().add(new XYChart.Data<>(eventName, memberCount));

            barChart.getData().add(series);
        }

        // Créer une nouvelle fenêtre pour afficher le graphique à barres
        Stage stage = new Stage();
        Scene scene = new Scene(barChart, 800, 600);
        stage.setScene(scene);
        stage.show();
    }
}










