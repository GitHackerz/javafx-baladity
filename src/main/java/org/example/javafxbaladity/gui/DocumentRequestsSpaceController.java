package org.example.javafxbaladity.gui;


import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
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
import net.sourceforge.tess4j.TesseractException;
import org.example.javafxbaladity.Main;
import org.example.javafxbaladity.Services.Document_ServiceDoc;
import org.example.javafxbaladity.Services.*;
import org.example.javafxbaladity.models.Document;
import org.example.javafxbaladity.models.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;


public class DocumentRequestsSpaceController implements Initializable {
    @FXML
    private MFXButton Clear_Btn;
    @FXML
    private MFXDatePicker DateEmission_DatePicker;
    @FXML
    private Button Documents_Btn;
    @FXML
    private Button Home_Btn;
    @FXML
    private MFXButton PDF_Btn;
    @FXML
    private Pane MainPain;
    @FXML
    private Text NbDocs_Text;
    @FXML
    private Text NbReqDocs_text;
    @FXML
    private MFXGenericDialog RequestDoc_pane;
    @FXML
    private MFXButton Save_btn;
    @FXML
    private MFXTextField Search_field;
    @FXML
    private MFXFilterComboBox<?> Type_ComboBox;
    @FXML
    private Button Users_Btn;
    @FXML
    private Button Users_Btn1;
    @FXML
    private Pane all_docs_pane;
    @FXML
    private Pane available_docs_pane;
    @FXML
    private MFXButton demdanderDoc_Btn;
    @FXML
    private Label done;
    @FXML
    private Pane list_docs_pane;
    @FXML
    private Label not_done;
    @FXML
    private MFXComboBox<String> showNbRows;
    @FXML
    private MFXPaginatedTableView<Document> table_ddoc;
    @FXML
    private MFXDatePicker Date_field;
    @FXML
    private MFXTextField Description_field;
    @FXML
    private MFXGenericDialog add_pane;
    @FXML
    private MFXButton ManageDocs_Req;
    @FXML
    private Label type_label;
    @FXML
    private MFXTableView<Document_request> DocReqTable;
    @FXML
    private Label date_doc_alert;
    @FXML
    private Label description_alert;
    Document_request_ServiceDoc ddoc_service = new Document_request_ServiceDoc();
    Document_ServiceDoc doc_service = new Document_ServiceDoc();
    Document DocSelected=null;
    Document_request DocReqSelected=null;
    @FXML
    private Label doc_alert;
    @FXML
    private VBox Left_pannel;
    @FXML
    private Label docSelected_alert;
    @FXML
    private MFXButton CloseDocReq,CloseDocReq1;
    @FXML
    private MFXButton imageToText_Btn;

    String dateRegex = "^(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\\s\\d{1,2},\\s\\d{4}$";

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

    int SCREEN_WIDTH = 1200;
    int SCREEN_HEIGHT = 700;

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
        try {
            setupDocsReqTable();
            setupPaginatedTableView_Doc();
            doc_alert.setVisible(false);
            add_pane.setVisible(false);
            RequestDoc_pane.setVisible(false);
            showNbRows.setItems(FXCollections.observableArrayList("1","2","3","4","5","6","7","8","9"));
            NbReqDocs_text.setText(ddoc_service.getNbAccepted()+"");
            NbDocs_Text.setText(ddoc_service.getNbReqDocs());
            getUserSession();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void DynamicSearch(InputMethodEvent event) {

    }
    @FXML
    void closeAddReq(ActionEvent event) {
        RequestDoc_pane.setVisible(false);

        list_docs_pane.setEffect(null);
        available_docs_pane.setEffect(null);
        all_docs_pane.setEffect(null);
        Users_Btn1.setEffect(null);
    }
    @FXML
    void getData(MouseEvent event) {
        List<Document> L_doc = table_ddoc.getSelectionModel().getSelectedValues(); // getselectedItem() moch mawjouda
        DocSelected = L_doc.get(0);
    }
    @FXML
    void getDataReq(MouseEvent event) {
        List<Document_request> L_doc = DocReqTable.getSelectionModel().getSelectedValues(); // getselectedItem() moch mawjouda
        DocReqSelected = L_doc.get(0);
    }
    @FXML
    void onClearBtnClick(ActionEvent event) {

        Description_field.setText("");
        Date_field.setText("");

        date_doc_alert.setVisible(false);
        description_alert.setVisible(false);
    }
    @FXML
    void onDemanderDocBtnClick(ActionEvent event) throws SQLException {

        if (DocSelected != null) {
            RequestDoc_pane.setVisible(true);
            type_label.setText(DocSelected.getType_doc());
            //RequestDoc_pane.setOpacity(0);

            // Créer une transition de fondu pour simuler l'effet de coup d'éponge
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), RequestDoc_pane);
            fadeIn.setToValue(1.0);
            fadeIn.play();

            BoxBlur boxBlur = new BoxBlur();
            boxBlur.setWidth(5);
            boxBlur.setHeight(5);
            boxBlur.setIterations(3);
            list_docs_pane.setEffect(boxBlur);
            available_docs_pane.setEffect(boxBlur);
            all_docs_pane.setEffect(boxBlur);
            Users_Btn1.setEffect(boxBlur);
            //Left_pannel.setEffect(boxBlur);
            RequestDoc_pane.setEffect(null);
            NbDocs_Text.setText(ddoc_service.getNbReqDocs());
        } else {
            doc_alert.setVisible(true);
            System.out.println("tu dois selectionner un doc a demander");
        }
    }



        @FXML
        void onPDFBtnClick(ActionEvent event) {


        if(DocReqSelected.getStatut_ddoc().equals("rejected"))
        {
            docSelected_alert.setText("You document got Rejected , you cannot Email/PDF it ");
            docSelected_alert.setVisible(true);
        }
        else if(DocReqSelected.getStatut_ddoc().equals("en attente"))
        {
                docSelected_alert.setText("You document is still pending approval , you will receive an Whatsup message \n when its status is updated ");
                docSelected_alert.setVisible(true);
        }
        else
        {
            PDFGenerator.GeneratePDF(DocReqSelected);
            add_pane.setVisible(false);
            docSelected_alert.setVisible(false);

            list_docs_pane.setEffect(null);
            list_docs_pane.setEffect(null);
            available_docs_pane.setEffect(null);
            all_docs_pane.setEffect(null);
            Users_Btn1.setEffect(null);

            //Openning PDF File in new Window
            try {
                File pdfFile = new File("C:\\Users\\21624\\Desktop\\Integration\\javafx-baladity-document\\src\\main\\resources\\org\\example\\javafxbaladity\\assets\\PDFGenerated.pdf");
                if (Desktop.isDesktopSupported()) {
                    Desktop desktop = Desktop.getDesktop();
                    if (pdfFile.exists()) {
                        desktop.open(pdfFile);
                    } else {
                        System.out.println("File not found: " + pdfFile.getAbsolutePath());
                    }
                } else {
                    System.out.println("Desktop class is not supported on this platform.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        }

        @FXML
        void onSaveBtnClick(ActionEvent event) throws SQLException {
        description_alert.setVisible(false);
        date_doc_alert.setVisible(false);
        if(Description_field.getText().isEmpty())
        {
           description_alert.setVisible(true);
        }
        if(Date_field.getText().isEmpty())
        {
            date_doc_alert.setVisible(true);
        }
        if(! (Date_field.getText().matches(dateRegex)))
        {
            date_doc_alert.setVisible(true);
        }

        if((Date_field.getText().matches(dateRegex)) &&!(Description_field.getText().isEmpty() ||Date_field.getText().isEmpty()))
        {
                RequestDoc_pane.setVisible(false);
                Document_request doc_req = new Document_request(DocSelected.getType_doc(), Description_field.getText(), "en attente", Date_field.getText(), DocSelected.getId_doc());
                ddoc_service.ajouter(doc_req);
                refreshTableView_Doc();

                list_docs_pane.setEffect(null);
                available_docs_pane.setEffect(null);
                all_docs_pane.setEffect(null);
                Users_Btn1.setEffect(null);
                add_pane.setEffect(null);

                description_alert.setVisible(false);
                date_doc_alert.setVisible(false);
            }
        }
        @FXML
        void onUserButtonClick(ActionEvent event) {

        }
        @FXML
        void onshowNbRowsChange(ActionEvent event) throws SQLException {
            table_ddoc.rowsPerPageProperty().setValue(Integer.parseInt(showNbRows.getValue()));
            refreshTableView_Doc();
        }
        private void setupPaginatedTableView_Doc() throws SQLException {
            MFXTableColumn<Document> id_doc_col = new MFXTableColumn<>("ID Document", false, Comparator.comparing(Document::getId_doc));
            MFXTableColumn<Document> type_doc_col = new MFXTableColumn<>("Type ", false, Comparator.comparing(Document::getType_doc));
            MFXTableColumn<Document> statut_doc_col = new MFXTableColumn<>("Statut ", false, Comparator.comparing(Document::getStatut_doc));
            MFXTableColumn<Document> date_emission_doc_col = new MFXTableColumn<>("Date Emission ", false, Comparator.comparing(Document::getDate_emission_doc));
            MFXTableColumn<Document> date_expritation_doc_col = new MFXTableColumn<>("Date Expiration", false, Comparator.comparing(Document::getDate_expiration_doc));

            id_doc_col.setRowCellFactory(device -> new MFXTableRowCell<>(Document::getId_doc){{
                setAlignment(Pos.BASELINE_CENTER);
            }});
            type_doc_col.setRowCellFactory(device -> new MFXTableRowCell<>(Document::getType_doc){{
                setFont(Font.font("System", FontWeight.BOLD,14));
            }});
            statut_doc_col.setRowCellFactory(device -> new MFXTableRowCell<>(Document::getStatut_doc) {{
                setAlignment(Pos.BASELINE_CENTER);
            }});
            date_emission_doc_col.setRowCellFactory(device -> new MFXTableRowCell<>(Document::getDate_emission_doc) {{
                setAlignment(Pos.BASELINE_CENTER);
            }});
            date_expritation_doc_col.setRowCellFactory(device -> new MFXTableRowCell<>(Document::getDate_expiration_doc) {{
                setAlignment(Pos.BASELINE_CENTER);
                setFont(Font.font("System", FontWeight.BOLD,14));
            }});


            id_doc_col.prefWidthProperty().bind(table_ddoc.widthProperty().multiply(0.1)); // 15% of width
            type_doc_col.prefWidthProperty().bind(table_ddoc.widthProperty().multiply(0.25)); // 15% of width
            statut_doc_col.prefWidthProperty().bind(table_ddoc.widthProperty().multiply(0.15)); // 15% of width
            date_emission_doc_col.prefWidthProperty().bind(table_ddoc.widthProperty().multiply(0.2)); // 20% of width
            date_expritation_doc_col.prefWidthProperty().bind(table_ddoc.widthProperty().multiply(0.2)); // 20% of width

            // Add columns to the table
            table_ddoc.getTableColumns().addAll(id_doc_col, type_doc_col, statut_doc_col, date_emission_doc_col, date_expritation_doc_col);
            table_ddoc.getFilters().addAll(
                    new IntegerFilter<>("Identifiant", Document::getId_doc),
                    new StringFilter<>("Type", Document::getType_doc),
                    new StringFilter<>("Statut", Document::getStatut_doc),
                    new StringFilter<>("Date emission", Document::getDate_emission_doc),
                    new StringFilter<>("Date expitation", Document::getDate_expiration_doc)
            );
            table_ddoc.setItems(doc_service.afficherCitoyen());
            DynamicFilter();
        }

        private void setupDocsReqTable() throws SQLException {
            MFXTableColumn<Document_request> type_ddoc_col = new MFXTableColumn<>("Type ", false, Comparator.comparing(Document_request::getType_ddoc));
            MFXTableColumn<Document_request> description_ddoc_col = new MFXTableColumn<>("Description ", false, Comparator.comparing(Document_request::getDescription_ddoc));
            MFXTableColumn<Document_request> date_ddoc_col = new MFXTableColumn<>("Date Demande", false, Comparator.comparing(Document_request::getDate_ddoc));
            MFXTableColumn<Document_request> statut_ddoc_col = new MFXTableColumn<>("Statut Demande ", false, Comparator.comparing(Document_request::getStatut_ddoc));
            MFXTableColumn<Document_request> date_traitement = new MFXTableColumn<>("Date Traitement ", false, Comparator.comparing(Document_request::getDate_traitement_ddoc));

            type_ddoc_col.setRowCellFactory(device -> new MFXTableRowCell<>(Document_request::getType_ddoc));
            description_ddoc_col.setRowCellFactory(device -> new MFXTableRowCell<>(Document_request::getDescription_ddoc) {{
                setAlignment(Pos.BASELINE_CENTER);
            }});
            date_ddoc_col.setRowCellFactory(device -> new MFXTableRowCell<>(Document_request::getDate_ddoc) {{
                setAlignment(Pos.BASELINE_CENTER);
            }});
            statut_ddoc_col.setRowCellFactory(device -> new MFXTableRowCell<>(Document_request::getStatut_ddoc) {{
                setAlignment(Pos.BASELINE_CENTER);
                setFont(Font.font("System", FontWeight.BOLD,14));
            }});
            date_traitement.setRowCellFactory(device -> new MFXTableRowCell<>(Document_request::getDate_traitement_ddoc) {{
                setAlignment(Pos.BASELINE_CENTER);
            }});

            // Add columns to the table
            DocReqTable.getTableColumns().addAll(type_ddoc_col, description_ddoc_col, date_ddoc_col, date_traitement ,statut_ddoc_col);
            DocReqTable.getFilters().addAll(
                    new StringFilter<>("Statut", Document_request::getStatut_ddoc)
            );

            DocReqTable.setItems(ddoc_service.afficherDocsRequests());


        }
    private void refreshReqTableView_Doc() throws SQLException {

        DocReqTable.setItems(ddoc_service.afficherDocsRequests());
        DynamicFilter();
    }


    private void DynamicFilter() throws SQLException {
        /*----------------DynamicSearch---------------------------*/
        FilteredList<Document> filterData = new FilteredList<>(doc_service.afficher());
        Search_field.textProperty().addListener((observable,oldValue,newValue) -> {
            filterData.setPredicate(Document -> {
                if(newValue.isEmpty() || newValue.isBlank() || newValue == null)
                {
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();
                if(String.valueOf(Document.getId_doc()).toLowerCase().indexOf(searchKeyword)> -1)
                {
                    return true;
                }
                else if(Document.getStatut_doc().toLowerCase().indexOf(searchKeyword)> -1)
                {
                    return true;
                }
                else if(Document.getDate_expiration_doc().toLowerCase().indexOf(searchKeyword)> -1)
                {
                    return true;
                }
                else if(Document.getDate_emission_doc().toLowerCase().indexOf(searchKeyword)> -1)
                {
                    return true;
                }
                else if(Document.getType_doc().toLowerCase().indexOf(searchKeyword)> -1)
                {
                    return true;
                }
                else if(Boolean.toString(Document.isEstarchive()).toLowerCase().indexOf(searchKeyword)> -1)
                {
                    return true;
                } else
                    return false;

            });
        });

        SortedList<Document> sortedData = new SortedList<>(filterData);
        sortedData.comparatorProperty().bind(table_ddoc.getTransformableList().comparatorProperty());
        table_ddoc.setItems(sortedData);
    }

        private void refreshTableView_Doc() throws SQLException {

            table_ddoc.setItems(doc_service.afficherCitoyen());
            DynamicFilter();
        }

        @FXML
        void onManageDocsReqClick(ActionEvent event) throws SQLException {
            add_pane.setVisible(true);
            add_pane.setVisible(true);
            //setupDocsReqTable();
refreshReqTableView_Doc();
            // Créer une transition de fondu pour simuler l'effet de coup d'éponge
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), add_pane);
            fadeIn.setToValue(1.0);
            fadeIn.play();

            BoxBlur boxBlur = new BoxBlur();
            boxBlur.setWidth(5);
            boxBlur.setHeight(5);
            boxBlur.setIterations(3);
            list_docs_pane.setEffect(boxBlur);
            available_docs_pane.setEffect(boxBlur);
            all_docs_pane.setEffect(boxBlur);
            Users_Btn1.setEffect(boxBlur);
            add_pane.setEffect(null);

        }

        @FXML
        void closeAddPane(ActionEvent event) {
            add_pane.setVisible(false);

            list_docs_pane.setEffect(null);
            available_docs_pane.setEffect(null);
            all_docs_pane.setEffect(null);
            Users_Btn1.setEffect(null);
            add_pane.setEffect(null);
        }

    @FXML
    void onCloseDocReqClick(ActionEvent event) {

        RequestDoc_pane.setVisible(false);

        list_docs_pane.setEffect(null);
        available_docs_pane.setEffect(null);
        all_docs_pane.setEffect(null);
        Users_Btn1.setEffect(null);
        add_pane.setEffect(null);
    }

    @FXML
    void onCloseDocReqClick1(ActionEvent event) {

        add_pane.setVisible(false);

        list_docs_pane.setEffect(null);
        available_docs_pane.setEffect(null);
        all_docs_pane.setEffect(null);
        Users_Btn1.setEffect(null);
        add_pane.setEffect(null);
    }


    @FXML
    void onImgToTextClick(ActionEvent event) throws TesseractException {
        Description_field.setText(ImageToText.ReturnText());
    }


    @FXML
    void onAssociationsButtonClick(ActionEvent event) throws IOException {
        /*FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/association.fxml"));
        Parent root = fxmlLoader.load();
        Stage window = (Stage) Associations_Btn.getScene().getWindow();
        window.setScene(new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT));*/
        Setup_Page("views/associations.fxml",Associations_Btn);

    }

    @FXML
    void onEventsButtonClick(ActionEvent event) throws IOException {
        /*FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/events.fxml"));
        Parent root = fxmlLoader.load();
        Stage window = (Stage) Events_Btn.getScene().getWindow();
        window.setScene(new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT));*/
        Setup_Page("views/events/eventsfront.fxml",Events_Btn);

    }



    @FXML
    void onProjectsButtonClick(ActionEvent event) throws IOException {
       /* FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/projects.fxml"));
        Parent root = fxmlLoader.load();
        Stage window = (Stage) Projects_Btn.getScene().getWindow();
        window.setScene(new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT));*/
        Setup_Page("views/projets.fxml",Projects_Btn);
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

    @FXML
    public void onHomeButtonClick() throws Exception {
        /*FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/home.fxml"));
        Parent root = fxmlLoader.load();
        Stage window = (Stage) Home_Btn.getScene().getWindow();
        window.setScene(new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT));*/
        Setup_Page("views/home.fxml",Home_Btn);
    }
    @FXML
    public void onUserButtonClick() throws Exception {
        /*FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/users.fxml"));
        Parent root = fxmlLoader.load();
        Stage window = (Stage) Users_Btn.getScene().getWindow();
        window.setScene(new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT));*/
        if(userRole.equals("employe"))
        {
            Setup_Page("views/users.fxml",Users_Btn);

        }
        else
        {
            Setup_Page("views/useraccount.fxml",Users_Btn);

        }



    }


    /* public void onDocumentsButtonClick() throws Exception {
         FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/documents.fxml"));
         Parent root = fxmlLoader.load();
         Stage window = (Stage) Documents_Btn.getScene().getWindow();
         window.setScene(new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT));
     }*/
    @FXML
    public void onDocumentsButtonClick() throws Exception {
        if(userRole.equals("employe"))
        {
            Setup_Page("views/documents.fxml",Documents_Btn);

        }
        else
        {
            Setup_Page("views/documents_requests_space.fxml",Documents_Btn);

        }
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
