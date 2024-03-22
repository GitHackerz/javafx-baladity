package org.example.javafxbaladity.gui;

import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.example.javafxbaladity.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import io.github.palexdev.materialfx.enums.NotificationPos;
import io.github.palexdev.materialfx.filter.BooleanFilter;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import io.github.palexdev.materialfx.notifications.MFXNotificationCenterSystem;
import io.github.palexdev.materialfx.notifications.base.INotification;
import io.github.palexdev.materialfx.utils.others.observables.When;
import javafx.animation.FadeTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.javafxbaladity.Services.Document_ServiceDoc;
import org.example.javafxbaladity.Services.Document_request_ServiceDoc;
import org.example.javafxbaladity.models.Document;
import org.example.javafxbaladity.models.ExampleNotification;
import org.example.javafxbaladity.utils.Navigate;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.ResourceBundle;

public class DocumentsController implements Initializable {


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
    @FXML
    private Button CinBTN;






    @FXML
    void onAssociationsButtonClick(ActionEvent event) throws IOException {
        /*FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/association.fxml"));
        Parent root = fxmlLoader.load();
        Stage window = (Stage) Associations_Btn.getScene().getWindow();
        window.setScene(new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT));*/
        Setup_Page("views/associations.fxml",Associations_Btn);

        System.out.println(userId);
    }

    @FXML
    void onEventsButtonClick(ActionEvent event) throws IOException {
        Navigate.toEvent(Events_Btn, userRole);
    }
    @FXML
    void onProjectsButtonClick(ActionEvent event) throws IOException {
       /* FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/projects.fxml"));
        Parent root = fxmlLoader.load();
        Stage window = (Stage) Projects_Btn.getScene().getWindow();
        window.setScene(new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT));*/
        Navigate.toProject(Projects_Btn, userRole);
    }

    @FXML
    void onReclamationsButtonClick(ActionEvent event) throws IOException {
        /*FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/reclamations.fxml"));
        Parent root = fxmlLoader.load();
        Stage window = (Stage) Reclamations_Btn.getScene().getWindow();
        window.setScene(new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT));*/
//        Setup_Page("views/reclamations.fxml",Reclamations_Btn);
        Navigate.toReclamation(Reclamations_Btn, userRole);
    }


    public void onHomeButtonClick() throws Exception {
        /*FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/home.fxml"));
        Parent root = fxmlLoader.load();
        Stage window = (Stage) Home_Btn.getScene().getWindow();
        window.setScene(new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT));*/
//        Setup_Page("views/home.fxml",Home_Btn);
        Navigate.toHome(Home_Btn);
    }

    public void onUserButtonClick() throws Exception {
        /*FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/users.fxml"));
        Parent root = fxmlLoader.load();
        Stage window = (Stage) Users_Btn.getScene().getWindow();
        window.setScene(new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT));*/
        //Setup_Page("views/users.fxml",Users_Btn);

        Navigate.toUser(Users_Btn, userRole);

    }


    /* public void onDocumentsButtonClick() throws Exception {
         FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/documents.fxml"));
         Parent root = fxmlLoader.load();
         Stage window = (Stage) Documents_Btn.getScene().getWindow();
         window.setScene(new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT));
     }*/
    public void onDocumentsButtonClick() throws Exception {
        Navigate.toDocument(Documents_Btn, userRole);
    }

    public void Setup_Page(String path,Button Btn) throws IOException {
        // Load the loading screen
        FXMLLoader loadingFXMLLoader = new FXMLLoader(Main.class.getResource("views/loading.fxml"));
        Parent loadingRoot = loadingFXMLLoader.load();
        Stage loadingStage = new Stage();
        loadingStage.initModality(Modality.APPLICATION_MODAL);
        loadingStage.initStyle(StageStyle.UNDECORATED);
        loadingStage.setScene(new Scene(loadingRoot));
        loadingStage.setX(1100);
        loadingStage.setY(445);
        loadingStage.show();

        // Load the actual content in the background
        Task<Void> loadContentTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(path));
                Parent root = fxmlLoader.load();
                Platform.runLater(() -> {
                    // Switch to the loaded content
                    Stage window = (Stage) Btn.getScene().getWindow();
                    window.setScene(new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT));
                    // Close the loading screen
                    loadingStage.close();
                });
                return null;
            }
        };
        Thread thread = new Thread(loadContentTask);
        thread.setDaemon(true);
        thread.start();
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
    private MFXButton Clear_Btn;
    @FXML
    private MFXDatePicker DateEmission_DatePicker;
    @FXML
    private MFXDatePicker DateExpritation_DatePicker;
    @FXML
    private MFXButton Delete_Btn;

    @FXML
    private MFXFilterComboBox<String> EstArchive_ComboBox;

    @FXML
    private MFXButton Save_btn;
    @FXML
    private MFXFilterComboBox<String> Statut_ComboBox;
    @FXML
    private MFXFilterComboBox<String> Type_ComboBox;
    @FXML
    private MFXButton Update_Btn;

    @FXML
    private MFXPaginatedTableView<Document> table_doc;
    Document_ServiceDoc doc_service = new Document_ServiceDoc();
    Document_request_ServiceDoc ddoc_service = new Document_request_ServiceDoc();
    private int id_doc = 0;
    @FXML
    private MFXGenericDialog add_pane;
    @FXML
    private MFXButton Add_Btn;
    @FXML
    private Pane MainPain;
    @FXML
    private Pane list_docs_pane;
    @FXML
    private Pane nb_docs_pane;
    @FXML
    private Pane nb_req_docs_pane;


    @FXML
    private Text NbDocs_Text;
    @FXML
    private Text NbReqDocs_text;
    @FXML
    private MFXComboBox<String> showNbRows;
    @FXML
    private MFXTextField Search_field;
    @FXML
    private MFXButton Manage_DocsReq;
    Document doc;
    @FXML
    Button Users_Btn1;


    @FXML
    private Label date_emission_alert,user_name;

    @FXML
    private Label date_expiration_alert;
    @FXML
    private Label estarchive_alert;
    @FXML
    private Label statut_alert;

    @FXML
    private Label type_alert;

    @FXML
    private Label details_alert, exists_alert;

    String dateRegex = "^(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\\s\\d{1,2},\\s\\d{4}$";

//    // PUSH A NEW NOTIFICATION
//        MFXNotificationCenterSystem.instance()
//                .setPosition(NotificationPos.TOP_RIGHT)
//                .publish(createNotification(new SimpleStringProperty("Header1"), new SimpleStringProperty("Ceci est le contenu de la notification")));


    public DocumentsController() {

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getUserSession();

        Statut_ComboBox.setItems(FXCollections.observableArrayList("Public", "Private"));
        Type_ComboBox.setItems(FXCollections.observableArrayList("Plans Urbanisme", "Autorisations construction", "Registre etat civil", "Actes administratifs", "Règlements municipaux", "Permis de construction", "Rapports financiers", "Contrats et marchés publics", "Bulletins d'information municipaux"));
        EstArchive_ComboBox.setItems(FXCollections.observableArrayList("true", "false"));
        showNbRows.setItems(FXCollections.observableArrayList("1", "2", "3", "4", "5"));
        add_pane.setVisible(false);
        exists_alert.setVisible(false);
        user_name.setText(userName);
        getUserSession();
        //Update_Btn.setVisible(false);


        try {
            setupPaginatedTableView_Doc();
            table_doc.autosizeColumnsOnInitialization();
            When.onChanged(table_doc.currentPageProperty())
                    .then((oldValue, newValue) -> table_doc.autosizeColumns())
                    .listen();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }





       /* DialogDragHandler dialogDragHandler = new DialogDragHandler(); //make the add pane dragabble
        dialogDragHandler.makeDraggable(add_pane);*/


        try {
            NbDocs_Text.setText(doc_service.getNbDocs());
            NbReqDocs_text.setText(ddoc_service.getNbReqDocs());
            //NbReqDocs_text.setText("-"); // the same as the previous but with docs req
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }





    public static INotification createNotification(StringProperty Header, StringProperty Body) throws IOException {
        ExampleNotification notification = new ExampleNotification(Header, Body);
        return notification;
    }


    @FXML
    void onClearBtnClick(ActionEvent event) {
        ClearAllElements();
    }


    @FXML
    void onDeleteBtnClick(ActionEvent event) throws SQLException, IOException {

        doc_service.supprimer(id_doc);
        refreshTableView_Doc();

        Save_btn.setDisable(false);
        Update_Btn.setDisable(false);

        // PUSH A NEW NOTIFICATION
        MFXNotificationCenterSystem.instance()
                .setPosition(NotificationPos.TOP_RIGHT)
                .publish(createNotification(new SimpleStringProperty("Delete Notification"), new SimpleStringProperty("The delete operation has been succesfully done  ")));


        try {
            NbDocs_Text.setText(doc_service.getNbDocs());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onDocumentsButtonClick(ActionEvent event) {

    }




    @FXML
    void onSaveBtnClick(ActionEvent event) throws SQLException {

        type_alert.setVisible(false);
        statut_alert.setVisible(false);
        estarchive_alert.setVisible(false);
        date_expiration_alert.setVisible(false);
        date_emission_alert.setVisible(false);
        exists_alert.setVisible(false);

        if (Type_ComboBox.getText().isEmpty()) {
            type_alert.setVisible(true);
        }
        if (Statut_ComboBox.getText().isEmpty()) {
            statut_alert.setVisible(true);
        }
        if (DateEmission_DatePicker.getText().isEmpty()) {
            date_emission_alert.setVisible(true);
        }
        if (!(DateEmission_DatePicker.getText().matches(dateRegex))) {
            date_emission_alert.setVisible(true);
        }
        if (DateExpritation_DatePicker.getText().isEmpty()) {
            date_expiration_alert.setVisible(true);
        }
        if (!(DateExpritation_DatePicker.getText().matches(dateRegex))) {
            date_expiration_alert.setVisible(true);
        }
        if (EstArchive_ComboBox.getText().isEmpty()) {
            estarchive_alert.setVisible(true);
        }
        if (((DateEmission_DatePicker.getText().matches(dateRegex)) && (DateExpritation_DatePicker.getText().matches(dateRegex)) && !(Type_ComboBox.getText().isEmpty() || Statut_ComboBox.getText().isEmpty() || DateEmission_DatePicker.getText().isEmpty() || DateExpritation_DatePicker.getText().isEmpty() || EstArchive_ComboBox.getText().isEmpty()))) {
            Document doc = new Document(Type_ComboBox.getText(), Statut_ComboBox.getText(), DateEmission_DatePicker.getText(), DateExpritation_DatePicker.getText(), Boolean.parseBoolean(EstArchive_ComboBox.getText()));
            if (doc_service.RechercherDoc(doc)) {
                System.out.println("Doc existant");
                exists_alert.setVisible(true);
            } else {
                try {
                    doc_service.ajouter(doc);
                    refreshTableView_Doc();
                    add_pane.setVisible(false);
                    MainPain.setStyle(null);

                    statut_alert.setVisible(false);
                    type_alert.setVisible(false);
                    date_emission_alert.setVisible(false);
                    date_expiration_alert.setVisible(false);
                    estarchive_alert.setVisible(false);

                    list_docs_pane.setEffect(null);
                    nb_req_docs_pane.setEffect(null);
                    nb_docs_pane.setEffect(null);
                    MainPain.setBackground(null);

                    Update_Btn.setDisable(false);
                    Save_btn.setDisable(false);
                    NbDocs_Text.setText(doc_service.getNbDocs());

                    // PUSH A NEW NOTIFICATION
                    MFXNotificationCenterSystem.instance()
                            .setPosition(NotificationPos.TOP_RIGHT)
                            .publish(createNotification(new SimpleStringProperty("Ajout Notification"), new SimpleStringProperty("Ajout effectué avec succee")));


                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }

    @FXML
    void onUpdateBtnClick(ActionEvent event) throws SQLException {
        getaVoid();
        statut_alert.setVisible(false);
        estarchive_alert.setVisible(false);
        date_expiration_alert.setVisible(false);
        date_emission_alert.setVisible(false);
        exists_alert.setVisible(false);


        if (Type_ComboBox.getText().isEmpty()) {
            type_alert.setVisible(true);
        }
        if (Statut_ComboBox.getText().isEmpty()) {
            statut_alert.setVisible(true);
        }
        if (DateEmission_DatePicker.getText().isEmpty()) {
            date_emission_alert.setVisible(true);
        }
        if (!(DateEmission_DatePicker.getText().matches(dateRegex))) {
            date_emission_alert.setVisible(true);
        }
        if (DateExpritation_DatePicker.getText().isEmpty()) {
            date_expiration_alert.setVisible(true);
        }
        if (!(DateExpritation_DatePicker.getText().matches(dateRegex))) {
            date_expiration_alert.setVisible(true);
        }
        if (EstArchive_ComboBox.getText().isEmpty()) {
            estarchive_alert.setVisible(true);
        }


        if (((DateEmission_DatePicker.getText().matches(dateRegex)) && (DateExpritation_DatePicker.getText().matches(dateRegex))) && !(Type_ComboBox.getText().isEmpty() || Statut_ComboBox.getText().isEmpty() || DateEmission_DatePicker.getText().isEmpty() || DateExpritation_DatePicker.getText().isEmpty() || EstArchive_ComboBox.getText().isEmpty())) {

            doc.setType_doc(Type_ComboBox.getText());
            doc.setStatut_doc(Statut_ComboBox.getText());
            doc.setDate_emission_doc(DateEmission_DatePicker.getText());
            doc.setDate_expiration_doc(DateExpritation_DatePicker.getText());
            doc.setEstarchive(Boolean.parseBoolean(EstArchive_ComboBox.getText()));


            doc_service.modifier(doc);
            ClearAllElements();
            add_pane.setVisible(false);
            refreshTableView_Doc();

            list_docs_pane.setEffect(null);
            nb_req_docs_pane.setEffect(null);
            nb_docs_pane.setEffect(null);

            statut_alert.setVisible(false);
            type_alert.setVisible(false);
            date_emission_alert.setVisible(false);
            date_expiration_alert.setVisible(false);
            estarchive_alert.setVisible(false);


        }
    }

    private void getaVoid() {
        type_alert.setVisible(false);
    }

    @FXML
    void getData(MouseEvent event) {
        /*List<Document> L_doc = table_doc.getSelectionModel().getSelectedValues(); // getselectedItem() moch mawjouda
        doc = L_doc.get(0);*/

        doc = table_doc.getSelectionModel().getSelectedValue();
        if (doc != null) {
            id_doc = doc.getId_doc();
            Type_ComboBox.setValue(doc.getType_doc());
            Statut_ComboBox.setText(doc.getStatut_doc());
            DateEmission_DatePicker.setText(doc.getDate_emission_doc());
            DateExpritation_DatePicker.setText(doc.getDate_expiration_doc());
            EstArchive_ComboBox.setText(Boolean.toString(doc.isEstarchive()));
        }


    }

    private void setupPaginatedTableView_Doc() throws SQLException {
        MFXTableColumn<Document> id_doc_col = new MFXTableColumn<>("ID Document", false, Comparator.comparing(Document::getId_doc));
        MFXTableColumn<Document> type_doc_col = new MFXTableColumn<>("Type ", false, Comparator.comparing(Document::getType_doc));
        MFXTableColumn<Document> statut_doc_col = new MFXTableColumn<>("Statut ", false, Comparator.comparing(Document::getStatut_doc));
        MFXTableColumn<Document> date_emission_doc_col = new MFXTableColumn<>("Date Emission ", false, Comparator.comparing(Document::getDate_emission_doc));
        MFXTableColumn<Document> date_expritation_doc_col = new MFXTableColumn<>("Date Expiration", false, Comparator.comparing(Document::getDate_expiration_doc));
        MFXTableColumn<Document> estarchive_doc_col = new MFXTableColumn<>("isArchived", false, Comparator.comparing(Document::isEstarchive));
        MFXTableColumn<Document> nb_Req_Doc = new MFXTableColumn<>("Total Requests", false, Comparator.comparing(Document::getNb_req));


        id_doc_col.setRowCellFactory(device -> new MFXTableRowCell<>(Document::getId_doc));
        type_doc_col.setRowCellFactory(device -> new MFXTableRowCell<>(Document::getType_doc) {{
            setFont(Font.font("System", FontWeight.BOLD, 14));
        }});
        statut_doc_col.setRowCellFactory(device -> new MFXTableRowCell<>(Document::getStatut_doc) {{
            setAlignment(Pos.BASELINE_CENTER);
            //setStyle("-fx-background-color: red");
            setHeight(200);
            setWidth(200);
        }});
        date_emission_doc_col.setRowCellFactory(device -> new MFXTableRowCell<>(Document::getDate_emission_doc));
        date_expritation_doc_col.setRowCellFactory(device -> new MFXTableRowCell<>(Document::getDate_expiration_doc));
        id_doc_col.setAlignment(Pos.BASELINE_CENTER);
        estarchive_doc_col.setRowCellFactory(device -> new MFXTableRowCell<>(Document::isEstarchive) {{
            setFont(Font.font("System", FontWeight.BOLD, 14));
        }});

        nb_Req_Doc.setRowCellFactory(device -> new MFXTableRowCell<>(Document::getNb_req) {{
            setFont(Font.font("System", FontWeight.BOLD, 14));
        }});


        //ADJUSTING THE COLUMNS WIDTH
        id_doc_col.prefWidthProperty().bind(table_doc.widthProperty().multiply(0.10)); // 15% of width
        type_doc_col.prefWidthProperty().bind(table_doc.widthProperty().multiply(0.25)); // 15% of width
        statut_doc_col.prefWidthProperty().bind(table_doc.widthProperty().multiply(0.1)); // 15% of width
        date_emission_doc_col.prefWidthProperty().bind(table_doc.widthProperty().multiply(0.15)); // 20% of width
        date_expritation_doc_col.prefWidthProperty().bind(table_doc.widthProperty().multiply(0.15)); // 20% of width
        estarchive_doc_col.prefWidthProperty().bind(table_doc.widthProperty().multiply(0.10)); // 15% of width
        nb_Req_Doc.prefWidthProperty().bind(table_doc.widthProperty().multiply(0.1)); // 15% of width


        // Add columns to the table
        table_doc.getTableColumns().addAll(id_doc_col, type_doc_col, statut_doc_col, date_emission_doc_col, date_expritation_doc_col, estarchive_doc_col, nb_Req_Doc);

        table_doc.getFilters().addAll(
                new IntegerFilter<>("Identifiant", Document::getId_doc),
                new StringFilter<>("Type", Document::getType_doc),
                new StringFilter<>("Statut", Document::getStatut_doc),
                new StringFilter<>("Date emission", Document::getDate_emission_doc),
                new StringFilter<>("Date expitation", Document::getDate_expiration_doc),
                new BooleanFilter<>("Est archive", Document::isEstarchive)
        );
        table_doc.setItems(doc_service.afficher());


        DynamicFilter();
    }

    private void DynamicFilter() throws SQLException {
        /*----------------DynamicSearch---------------------------*/
        FilteredList<Document> filterData = new FilteredList<>(doc_service.afficher());
        Search_field.textProperty().addListener((observable, oldValue, newValue) -> {
            filterData.setPredicate(Document -> {
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();
                if (String.valueOf(Document.getId_doc()).toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (Document.getStatut_doc().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (Document.getDate_expiration_doc().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (Document.getDate_emission_doc().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (Document.getType_doc().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (Boolean.toString(Document.isEstarchive()).toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else
                    return false;

            });
        });

        SortedList<Document> sortedData = new SortedList<>(filterData);
        sortedData.comparatorProperty().bind(table_doc.getTransformableList().comparatorProperty());
        table_doc.setItems(sortedData);
    }

    private void ClearAllElements() {
        Type_ComboBox.setText("");
        Statut_ComboBox.setText("");
        DateExpritation_DatePicker.setText("");
        DateEmission_DatePicker.setText("");
        EstArchive_ComboBox.setText("");

        estarchive_alert.setVisible(false);
        date_expiration_alert.setVisible(false);
        date_emission_alert.setVisible(false);
        type_alert.setVisible(false);
        statut_alert.setVisible(false);
        exists_alert.setVisible(false);
    }


    private void refreshTableView_Doc() throws SQLException {

        table_doc.setItems(doc_service.afficher());
        DynamicFilter();
    }


    @FXML
    void onAddBtnClick(ActionEvent event) throws IOException {
        add_pane.setOpacity(0);
        add_pane.setVisible(true);
        Update_Btn.setDisable(true);

        // Créer une transition de fondu pour simuler l'effet de coup d'éponge
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), add_pane);
        fadeIn.setToValue(1.0);
        fadeIn.play();

        BoxBlur boxBlur = new BoxBlur();
        boxBlur.setWidth(5);
        boxBlur.setHeight(5);
        boxBlur.setIterations(3);
        list_docs_pane.setEffect(boxBlur);
        nb_docs_pane.setEffect(boxBlur);
        nb_req_docs_pane.setEffect(boxBlur);
        Users_Btn1.setEffect(boxBlur);
        add_pane.setEffect(null);


    }

    @FXML
    void onDetailsBtnClick(ActionEvent event) {

        if (doc == null) {
            details_alert.setVisible(true);
        } else {
            add_pane.setOpacity(0);
            add_pane.setVisible(true);

            // Créer une transition de fondu pour simuler l'effet de coup d'éponge
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), add_pane);
            fadeIn.setToValue(1.0);
            fadeIn.play();

            BoxBlur boxBlur = new BoxBlur();
            boxBlur.setWidth(5);
            boxBlur.setHeight(5);
            boxBlur.setIterations(3);
            list_docs_pane.setEffect(boxBlur);
            nb_docs_pane.setEffect(boxBlur);
            nb_req_docs_pane.setEffect(boxBlur);
            Users_Btn1.setEffect(boxBlur);
            add_pane.setEffect(null);

            Save_btn.setDisable(true);
            Update_Btn.setDisable(false);
        }

    }


    @FXML
    void closeAddPane(MouseEvent event) {
        add_pane.setVisible(false);

        MainPain.setStyle(null);
        list_docs_pane.setEffect(null);
        nb_req_docs_pane.setEffect(null);
        nb_docs_pane.setEffect(null);
        MainPain.setBackground(null);
        Users_Btn1.setEffect(null);

        statut_alert.setVisible(false);
        type_alert.setVisible(false);
        date_emission_alert.setVisible(false);
        date_expiration_alert.setVisible(false);
        estarchive_alert.setVisible(false);
        exists_alert.setVisible(false);


        Update_Btn.setDisable(false);
        Save_btn.setDisable(false);

    }


    @FXML
    void onshowNbRowsChange(ActionEvent event) throws SQLException {
        table_doc.rowsPerPageProperty().setValue(Integer.parseInt(showNbRows.getValue()));
        refreshTableView_Doc();
    }

    @FXML
    void DynamicSearch(InputMethodEvent event) {


    }

    @FXML
    void onManageDocsReqClick(ActionEvent event) throws IOException {

        Setup_Page("views/documents_requests_manager.fxml",Manage_DocsReq);
    }









}