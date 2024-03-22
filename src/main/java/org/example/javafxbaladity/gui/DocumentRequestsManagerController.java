package org.example.javafxbaladity.gui;


import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
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
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.example.javafxbaladity.Main;
import org.example.javafxbaladity.Services.Document_request_ServiceDoc;
import org.example.javafxbaladity.models.Document_request;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class DocumentRequestsManagerController implements Initializable {


    int SCREEN_WIDTH = 1200;
    int SCREEN_HEIGHT = 700;

    @FXML
    private MFXButton Accept_Btn;
    @FXML
    private MFXButton Clear_Btn;
    @FXML
    private MFXDatePicker DateEmission_DatePicker;
    @FXML
    private MFXDatePicker DateExpritation_DatePicker;
    @FXML
    private Button Documents_Btn;
    @FXML
    private MFXFilterComboBox<?> EstArchive_ComboBox;
    @FXML
    private MFXButton History_Btn;
    @FXML
    private Button Home_Btn;
    @FXML
    private Pane MainPain;
    @FXML
    private Text NbDocs_Text;
    @FXML
    private Text NbReqDocs_text;
    @FXML
    private MFXButton Reject_Btn;


    @FXML
    Button Users_Btn;

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
    private MFXButton Save_btn;
    @FXML
    private MFXTextField Search_field;
    @FXML
    private MFXFilterComboBox<?> Statut_ComboBox;
    @FXML
    private MFXFilterComboBox<?> Type_ComboBox;
    @FXML
    private MFXButton Update_Btn;
    @FXML
    private Button Users_Btn1;
    @FXML
    private MFXGenericDialog add_pane;
    @FXML
    private Label done;
    @FXML
    private Pane list_docs_pane;
    @FXML
    private Pane nb_docs_pane;
    @FXML
    private Pane nb_req_docs_pane;
    @FXML
    private Label not_done, history_alert;
    @FXML
    private MFXComboBox<String> showNbRows;
    @FXML
    private MFXPaginatedTableView<Document_request> table_ddoc;
    Document_request_ServiceDoc ddoc_service = new Document_request_ServiceDoc();
    Document_request DocReqSelected = null;
    Document_request DocReqHistorySelected = null;
    @FXML
    private MFXTableView<Document_request> table_history;
    @FXML
    private MFXButton Refactor_Btn, stats_btn, CloseStatsPane;
    @FXML
    private Label select_alert, accept_rate_label;
    @FXML
    private MFXGenericDialog stats_pane;
    @FXML
    private PieChart pieChaet_ddoc;
    DecimalFormat decimalFormat = new DecimalFormat("#.##"); // Define format to keep only two decimal places
    String formattedValue;


    //Calling for user session
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getUserSession();
        add_pane.setVisible(false);
        try {
            select_alert.setVisible(false);
            SetupHistoryTableDdocView();
            SetupTableDdocView();
            showNbRows.setItems(FXCollections.observableArrayList("1", "2", "3", "4", "5"));
            history_alert.setVisible(false);

            SetupPieChart();
            stats_pane.setVisible(false);
            //System.out.println(ddoc_service.AcceptRate());
            formattedValue = decimalFormat.format(ddoc_service.AcceptRate()); // Format the float value
            accept_rate_label.setText(formattedValue + "%");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void DynamicSearch(InputMethodEvent event) {
    }

    @FXML
    void getData(MouseEvent event) {
        List<Document_request> L_doc = table_ddoc.getSelectionModel().getSelectedValues(); // getselectedItem() moch mawjouda
        DocReqSelected = L_doc.get(0);
    }

    @FXML
    void onAcceptBtnClick(ActionEvent event) throws SQLException {
        if (DocReqSelected != null) {

            if (DocReqSelected.getStatut_ddoc().equals("accepted")) {
                //doc already accepted
                System.out.println("doc already accepted");
            } else {
                // Get the current system date and time
                LocalDateTime currentDateTime = LocalDateTime.now();

                // Define the date and time format
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy HH:mm");

                // Format the current date and time as a string
                String dateTimeString = currentDateTime.format(formatter);


                DocReqSelected.setStatut_ddoc("accepted");

                DocReqSelected.setDate_traitement_ddoc(dateTimeString);

                ddoc_service.modifierDocsRequest(DocReqSelected);
                refreshTableddocView();
                refreshHistoryTableddocView();
                WhatsUpMessenger.SendWhatsUpMessage("Hi there \nYou have a new update on your Document Request, Make sure to check your client space in order to claim the document.\nBadality.Tn", "24821257");
                select_alert.setVisible(false);
                DocReqSelected = null;
                SetupPieChart();
                formattedValue = decimalFormat.format(ddoc_service.AcceptRate()); // Format the float value
                accept_rate_label.setText(formattedValue + "%");
            }

        } else {
            //faire une alerte
            System.out.println("NULL");
            select_alert.setVisible(true);
        }
    }

    @FXML
    void onRejectBtnClick(ActionEvent event) throws SQLException {
        if (DocReqSelected != null) {
            // Get the current system date and time
            LocalDateTime currentDateTime = LocalDateTime.now();

            // Define the date and time format
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy HH:mm");

            // Format the current date and time as a string
            String dateTimeString = currentDateTime.format(formatter);


            DocReqSelected.setStatut_ddoc("rejected");
            DocReqSelected.setDate_traitement_ddoc(dateTimeString);
            ddoc_service.modifierDocsRequest(DocReqSelected);
            refreshTableddocView();
            refreshHistoryTableddocView();
            select_alert.setVisible(false);
            DocReqSelected = null;
            SetupPieChart();
            formattedValue = decimalFormat.format(ddoc_service.AcceptRate()); // Format the float value
            accept_rate_label.setText(formattedValue + "%");
        } else {
            System.out.println("NULL");
            select_alert.setVisible(true);

        }
    }

    @FXML
    void onClearBtnClick(ActionEvent event) {

    }

    @FXML
    void onDocumentsButtonClick(ActionEvent event) throws IOException {
//        Parent root = FXMLLoader.load(getClass().getResource("Documents.fxml"));
////            Parent root = FXMLLoader.load(getClass().getResource("Document_requests_Space.fxml"));
//        Stage window = (Stage) Documents_Btn.getScene().getWindow();
//        window.setScene(new Scene(root, 1098, 667));
        if(userRole.equals("employe"))
        {
            Setup_Page("views/documents.fxml",Documents_Btn);

        }
        else
        {
            Setup_Page("views/documents_requests_space.fxml",Documents_Btn);

        }
    }

    @FXML
    void onHomeButtonClick(ActionEvent event) {

    }

    @FXML
    void onSaveBtnClick(ActionEvent event) {

    }

    @FXML
    void onUpdateBtnClick(ActionEvent event) {

    }

    @FXML
    void onUserButtonClick(ActionEvent event) throws IOException {
        if(userRole.equals("employe"))
        {
            Setup_Page("views/users.fxml",Users_Btn);

        }
        else
        {
            Setup_Page("views/useraccount.fxml",Users_Btn);

        }
    }

    @FXML
    void onshowNbRowsChange(ActionEvent event) throws SQLException {
        table_ddoc.rowsPerPageProperty().setValue(Integer.parseInt(showNbRows.getValue()));
        refreshTableddocView();
    }

    void SetupTableDdocView() throws SQLException {
        MFXTableColumn<Document_request> id_ddoc_col = new MFXTableColumn<>("ID", false, Comparator.comparing(Document_request::getId_ddoc));
        MFXTableColumn<Document_request> type_ddoc_col = new MFXTableColumn<>("Type ", false, Comparator.comparing(Document_request::getType_ddoc));
        MFXTableColumn<Document_request> description_ddoc_col = new MFXTableColumn<>("Description ", false, Comparator.comparing(Document_request::getDescription_ddoc));
        MFXTableColumn<Document_request> date_ddoc_col = new MFXTableColumn<>("Date Demande", false, Comparator.comparing(Document_request::getDate_ddoc));
        MFXTableColumn<Document_request> statut_ddoc_col = new MFXTableColumn<>("Statut Demande ", false, Comparator.comparing(Document_request::getStatut_ddoc));


        id_ddoc_col.setRowCellFactory(device -> new MFXTableRowCell<>(Document_request::getId_ddoc) {{
            setAlignment(Pos.BASELINE_CENTER);
        }});
        type_ddoc_col.setRowCellFactory(device -> new MFXTableRowCell<>(Document_request::getType_ddoc) {{
            setFont(Font.font("System", FontWeight.BOLD, 14));
        }});
        description_ddoc_col.setRowCellFactory(device -> new MFXTableRowCell<>(Document_request::getDescription_ddoc) {{
            setAlignment(Pos.BASELINE_CENTER);
        }});
        date_ddoc_col.setRowCellFactory(device -> new MFXTableRowCell<>(Document_request::getDate_ddoc) {{
            setAlignment(Pos.BASELINE_CENTER);
        }});
        statut_ddoc_col.setRowCellFactory(device -> new MFXTableRowCell<>(Document_request::getStatut_ddoc) {{
            setAlignment(Pos.BASELINE_CENTER);
            setFont(Font.font("System", FontWeight.BOLD, 14));

        }});

        id_ddoc_col.prefWidthProperty().bind(table_ddoc.widthProperty().multiply(0.05)); // 15% of width
        type_ddoc_col.prefWidthProperty().bind(table_ddoc.widthProperty().multiply(0.25)); // 15% of width
        description_ddoc_col.prefWidthProperty().bind(table_ddoc.widthProperty().multiply(0.3)); // 15% of width
        date_ddoc_col.prefWidthProperty().bind(table_ddoc.widthProperty().multiply(0.2)); // 20% of width
        statut_ddoc_col.prefWidthProperty().bind(table_ddoc.widthProperty().multiply(0.1)); // 20% of width

        // Add columns to the table
        table_ddoc.getTableColumns().addAll(id_ddoc_col, type_ddoc_col, description_ddoc_col, date_ddoc_col, statut_ddoc_col);
        table_ddoc.getFilters().addAll(
                new StringFilter<>("Date", Document_request::getDate_ddoc)
        );
        table_ddoc.setItems(ddoc_service.afficherEnAttente());
        DynamicFilter();

    }

    private void DynamicFilter() throws SQLException {
        /*----------------DynamicSearch---------------------------*/
        FilteredList<Document_request> filterData = new FilteredList<>(ddoc_service.afficherEnAttente());
        Search_field.textProperty().addListener((observable, oldValue, newValue) -> {
            filterData.setPredicate(DDocument -> {
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();
                if (String.valueOf(DDocument.getId_ddoc()).toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (DDocument.getStatut_ddoc().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (DDocument.getDescription_ddoc().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (DDocument.getDate_ddoc().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (DDocument.getType_ddoc().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else
                    return false;

            });
        });

        SortedList<Document_request> sortedData = new SortedList<>(filterData);
        sortedData.comparatorProperty().bind(table_ddoc.getTransformableList().comparatorProperty());
        table_ddoc.setItems(sortedData);
    }

    void refreshTableddocView() throws SQLException {
        table_ddoc.setItems(ddoc_service.afficherEnAttente());
        DynamicFilter();
    }

    /*--------------------------------------------------HISTORY-----------------------------------------------------------------------*/
    @FXML
    void onHistoryBtnClick(ActionEvent event) throws SQLException {

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
    }

    @FXML
    void closeAddPane(MouseEvent event) {
        add_pane.setVisible(false);
        list_docs_pane.setEffect(null);
        nb_req_docs_pane.setEffect(null);
        nb_docs_pane.setEffect(null);
        Users_Btn.setEffect(null);
    }

    @FXML
    void onRefactorBtnClick(ActionEvent event) throws SQLException {
        history_alert.setVisible(false);
        if (DocReqHistorySelected == null) {
            history_alert.setVisible(true);
        } else {
            ddoc_service.RefactorDocsRequest(DocReqHistorySelected);
            refreshHistoryTableddocView();
            refreshTableddocView();
            add_pane.setVisible(false);
            list_docs_pane.setEffect(null);
            nb_req_docs_pane.setEffect(null);
            nb_docs_pane.setEffect(null);
            Users_Btn.setEffect(null);
            history_alert.setVisible(false);
            SetupPieChart();
            formattedValue = decimalFormat.format(ddoc_service.AcceptRate()); // Format the float value
            accept_rate_label.setText(formattedValue + "%");

        }
    }

    @FXML
    void getHisotryData(MouseEvent event) {
        List<Document_request> L_doc = table_history.getSelectionModel().getSelectedValues(); // getselectedItem() moch mawjouda
        DocReqHistorySelected = L_doc.get(0);
    }

    void SetupHistoryTableDdocView() throws SQLException {
        MFXTableColumn<Document_request> id_ddoc_col = new MFXTableColumn<>("ID", false, Comparator.comparing(Document_request::getId_ddoc));
        MFXTableColumn<Document_request> type_ddoc_col = new MFXTableColumn<>("Type ", false, Comparator.comparing(Document_request::getType_ddoc));
        MFXTableColumn<Document_request> description_ddoc_col = new MFXTableColumn<>("Description ", false, Comparator.comparing(Document_request::getDescription_ddoc));
        MFXTableColumn<Document_request> date_traitement_col = new MFXTableColumn<>("Date Traitement", false, Comparator.comparing(Document_request::getDate_traitement_ddoc));
        MFXTableColumn<Document_request> statut_ddoc_col = new MFXTableColumn<>("Statut Demande ", false, Comparator.comparing(Document_request::getStatut_ddoc));


        id_ddoc_col.setRowCellFactory(device -> new MFXTableRowCell<>(Document_request::getId_ddoc) {{
            setAlignment(Pos.BASELINE_CENTER);
        }});
        type_ddoc_col.setRowCellFactory(device -> new MFXTableRowCell<>(Document_request::getType_ddoc));
        description_ddoc_col.setRowCellFactory(device -> new MFXTableRowCell<>(Document_request::getDescription_ddoc) {{
            setAlignment(Pos.BASELINE_CENTER);
        }});
        date_traitement_col.setRowCellFactory(device -> new MFXTableRowCell<>(Document_request::getDate_traitement_ddoc) {{
            setAlignment(Pos.BASELINE_CENTER);
        }});
        statut_ddoc_col.setRowCellFactory(device -> new MFXTableRowCell<>(Document_request::getStatut_ddoc) {{
            setAlignment(Pos.BASELINE_CENTER);
            setFont(Font.font("System", FontWeight.BOLD, 14));
        }});

        id_ddoc_col.prefWidthProperty().bind(table_history.widthProperty().multiply(0.1)); // 15% of width
        type_ddoc_col.prefWidthProperty().bind(table_history.widthProperty().multiply(0.2)); // 15% of width
        description_ddoc_col.prefWidthProperty().bind(table_history.widthProperty().multiply(0.1)); // 15% of width
        date_traitement_col.prefWidthProperty().bind(table_history.widthProperty().multiply(0.4)); // 20% of width
        statut_ddoc_col.prefWidthProperty().bind(table_history.widthProperty().multiply(0.1)); // 20% of width

        // Add columns to the table
        table_history.getTableColumns().addAll(id_ddoc_col, type_ddoc_col, description_ddoc_col, date_traitement_col, statut_ddoc_col);
        table_history.getFilters().addAll(
                new StringFilter<>("Statut", Document_request::getStatut_ddoc)
        );
        table_history.setItems(ddoc_service.afficherAccepAndRejec());
    }

    void refreshHistoryTableddocView() throws SQLException {
        table_history.setItems(ddoc_service.afficherAccepAndRejec());
    }

    public void  SetupPieChart() throws SQLException {
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Pending", ddoc_service.getNbenAttente()),
                        new PieChart.Data("Accepted", ddoc_service.getNbAccepted()),
                        new PieChart.Data("Rejected", ddoc_service.getNbRejected()));


        pieChartData.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(
                                data.getName(), " amount: ", data.pieValueProperty()
                        )
                )
        );
        pieChaet_ddoc.getData().clear(); // Clear existing data
        pieChaet_ddoc.getData().addAll(pieChartData); // Add new data
    }

    public static void  SetupPieChartforHome( PieChart pieChaet_ddoc,Document_request_ServiceDoc ddoc_service) throws SQLException {
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Pending", ddoc_service.getNbenAttente()),
                        new PieChart.Data("Accepted", ddoc_service.getNbAccepted()),
                        new PieChart.Data("Rejected", ddoc_service.getNbRejected()));


        pieChartData.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(
                                data.getName(), " amount: ", data.pieValueProperty()
                        )
                )
        );
        pieChaet_ddoc.getData().clear(); // Clear existing data
        pieChaet_ddoc.getData().addAll(pieChartData); // Add new data
    }



    @FXML
    void onStatsBtnClick(ActionEvent event) {
        stats_pane.setVisible(true);

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
    void onCloseStatsPaneClick(ActionEvent event) {
        stats_pane.setVisible(false);

        list_docs_pane.setEffect(null);
        nb_docs_pane.setEffect(null);
        nb_req_docs_pane.setEffect(null);
        Users_Btn1.setEffect(null);
        add_pane.setEffect(null);

    }



    @FXML
    void onAssociationsButtonClick(ActionEvent event) throws IOException {
        /*FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/association.fxml"));
        Parent root = fxmlLoader.load();
        Stage window = (Stage) Associations_Btn.getScene().getWindow();
        window.setScene(new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT));*/

        if(userRole.equals("employe"))
        {
            Setup_Page("views/associations.fxml",Associations_Btn);
        }
        else
        {
            Setup_Page("views/associations.fxml",Associations_Btn);
        }

    }

    @FXML
    void onEventsButtonClick(ActionEvent event) throws IOException {
        /*FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/events.fxml"));
        Parent root = fxmlLoader.load();
        Stage window = (Stage) Events_Btn.getScene().getWindow();
        window.setScene(new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT));*/

        if(userRole.equals("employe"))
        {
            Setup_Page("views/eventsfront.fxml",Events_Btn);

        }
        else
        {
            Setup_Page("views/eventsfront.fxml",Events_Btn);

        }

    }



    @FXML
    void onProjectsButtonClick(ActionEvent event) throws IOException {
       /* FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/projects.fxml"));
        Parent root = fxmlLoader.load();
        Stage window = (Stage) Projects_Btn.getScene().getWindow();
        window.setScene(new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT));*/

        if(userRole.equals("employe"))
        {
            Setup_Page("views/projets.fxml",Projects_Btn);

        }
        else
        {
            Setup_Page("views/projets.fxml",Projects_Btn);

        }
    }

    @FXML
    void onReclamationsButtonClick(ActionEvent event) throws IOException {
        /*FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/reclamations.fxml"));
        Parent root = fxmlLoader.load();
        Stage window = (Stage) Reclamations_Btn.getScene().getWindow();
        window.setScene(new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT));*/
        if(userRole.equals("employe"))
        {
            Setup_Page("views/reclamation2.fxml",Reclamations_Btn);

        }
        else
        {
            Setup_Page("views/reclamations.fxml",Reclamations_Btn);

        }
    }


    public void onHomeButtonClick() throws Exception {
        /*FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/home.fxml"));
        Parent root = fxmlLoader.load();
        Stage window = (Stage) Home_Btn.getScene().getWindow();
        window.setScene(new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT));*/
        Setup_Page("views/home.fxml",Home_Btn);
    }

    public void onUserButtonClick() throws Exception {
        /*FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/users.fxml"));
        Parent root = fxmlLoader.load();
        Stage window = (Stage) Users_Btn.getScene().getWindow();
        window.setScene(new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT));*/
        Setup_Page("views/users.fxml",Users_Btn);



    }


    /* public void onDocumentsButtonClick() throws Exception {
         FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/documents.fxml"));
         Parent root = fxmlLoader.load();
         Stage window = (Stage) Documents_Btn.getScene().getWindow();
         window.setScene(new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT));
     }*/
    public void onDocumentsButtonClick() throws Exception {
        Setup_Page("views/documents.fxml",Documents_Btn);
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



}
