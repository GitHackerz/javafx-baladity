package org.example.javafxbaladity.gui;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import javafx.scene.chart.*;
import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.example.javafxbaladity.Main;
import org.example.javafxbaladity.Services.SMSService;
import org.example.javafxbaladity.Services.ServiceRepReclamation;
import org.example.javafxbaladity.models.Reclamation;
import org.example.javafxbaladity.utils.Database;

import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.layout.AnchorPane;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.javafxbaladity.utils.Navigate;

import java.io.IOException;
import java.util.List;






public class RepReclamationController implements Initializable {
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
    ServiceRepReclamation sr = new ServiceRepReclamation();
    Connection conn = null;
    PreparedStatement rec = null;
    ResultSet rs = null;

    @FXML
    private MFXButton btndetails;
    int SCREEN_WIDTH = 1200;
    int SCREEN_HEIGHT = 700;

    ObservableList<PieChart.Data> info = FXCollections.observableArrayList();
    @FXML
    private Button btnAjouterReclamation;

    @FXML
    private Button btnModifierReclamation;

    @FXML
    private Button btnSupprimerAllRec;
    @FXML
    private MFXButton Close_User1;
    @FXML
    private Button btnSupprimerReclamation;

    @FXML
    private TextField tf_DeleteReclamation;

    @FXML
    private MFXTextField phoneNumberTextField;

    @FXML
    private MFXButton sms_bttn;

    @FXML
    private Button exportToPdfButton;

    @FXML
    private TableColumn<Reclamation, String> col_date;

    @FXML
    private TableColumn<Reclamation, String> col_descrip;

    @FXML
    private TableColumn<Reclamation, Integer> col_id;

    @FXML
    private TableColumn<Reclamation, String> col_statut;

    @FXML
    private TableColumn<Reclamation, String> col_type;

    @FXML
    private TableView<Reclamation> tableReclamations;
    @FXML
    private MFXComboBox<String> statutTxt;
    @FXML
    private Button Load_Btn;

    @FXML
    private MFXGenericDialog add_pane;

    int id_rec = 0;
    int x;

    @FXML
    private AnchorPane chartPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        statutTxt.setItems(FXCollections.observableArrayList("Non traitée", "Traitée"));
        try {
            showRepReclamations();
            getUserSession();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

       }

    @FXML
    Button Users_Btn, Home_Btn, Documents_Btn;


    @FXML
    private Button Associations_Btn;

    @FXML
    private PieChart piechart;


    @FXML
    private Button Events_Btn;


    @FXML
    private Button Logout_Btn;

    @FXML
    private Button Projects_Btn;

    @FXML
    private Button Reclamations_Btn;

    @FXML
    private MFXButton xl_bttn;


    @FXML
    void onAssociationsButtonClick(ActionEvent event) throws IOException {
        /*FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/association.fxml"));
        Parent root = fxmlLoader.load();
        Stage window = (Stage) Associations_Btn.getScene().getWindow();
        window.setScene(new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT));*/
//        Setup_Page("views/associations.fxml", Associations_Btn);
        Navigate.toAssociation(Associations_Btn, userRole);


    }

    @FXML
    void onEventsButtonClick(ActionEvent event) throws IOException {
        /*FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/events.fxml"));
        Parent root = fxmlLoader.load();
        Stage window = (Stage) Events_Btn.getScene().getWindow();
        window.setScene(new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT));*/
        Navigate.toEvent(Events_Btn, userRole);


    }


    @FXML
    void onProjectsButtonClick(ActionEvent event) throws IOException {
       /* FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/projects.fxml"));
        Parent root = fxmlLoader.load();
        Stage window = (Stage) Projects_Btn.getScene().getWindow();
        window.se        Navigate.toEvent(Events_Btn, userRole);
tScene(new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT));*/
        Navigate.toProject(Projects_Btn, userRole);

    }

    @FXML
    void onReclamationsButtonClick(ActionEvent event) throws IOException {
        /*FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/reclamations.fxml"));
        Parent root = fxmlLoader.load();
        Stage window = (Stage) Reclamations_Btn.getScene().getWindow();
        window.setScene(new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT));*/
//        Setup_Page("views/reclamations.fxml", Reclamations_Btn);
        Navigate.toReclamation(Reclamations_Btn, userRole);

    }


    public void onHomeButtonClick() throws Exception {
        /*FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/home.fxml"));
        Parent root = fxmlLoader.load();
        Stage window = (Stage) Home_Btn.getScene().getWindow();
        window.setScene(new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT));*/
//        Setup_Page("views/home.fxml", Home_Btn);
        Navigate.toHome(Home_Btn);

    }

    public void onUserButtonClick() throws Exception {
        /*FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/users.fxml"));
        Parent root = fxmlLoader.load();
        Stage window = (Stage) Users_Btn.getScene().getWindow();
        window.setScene(new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT));*/
//        Setup_Page("views/users.fxml", Users_Btn);
        Navigate.toUser(Users_Btn, userRole);



    }


    /* public void onDocumentsButtonClick() throws Exception {
         FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/documents.fxml"));
         Parent root = fxmlLoader.load();
         Stage window = (Stage) Documents_Btn.getScene().getWindow();
         window.setScene(new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT));
     }*/
    public void onDocumentsButtonClick() throws Exception {
//        Setup_Page("views/documents.fxml", Documents_Btn);
        Navigate.toDocument(Documents_Btn, userRole);

    }

    public void Setup_Page(String path, Button Btn) throws IOException {
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
    private MFXTextField tf_Date;

    @FXML
    private MFXTextField tf_Descrip;


    @FXML
    private MFXTextField tf_Statut;

    @FXML
    private MFXTextField tf_Type;

    @FXML
    public void showRepReclamations() throws SQLException {
        ObservableList<Reclamation> list = sr.recuperer();
        System.out.println(list);

        col_id.setCellValueFactory(new PropertyValueFactory<Reclamation, Integer>("idReclamation"));
        col_type.setCellValueFactory(new PropertyValueFactory<Reclamation, String>("typeReclamation"));
        col_descrip.setCellValueFactory(new PropertyValueFactory<Reclamation, String>("descriptionReclamation"));
        col_statut.setCellValueFactory(new PropertyValueFactory<Reclamation, String>("statutReclamation"));
        col_date.setCellValueFactory(new PropertyValueFactory<Reclamation, String>("dateReclamation"));
        tableReclamations.setItems(list);
    }

    @FXML
    void ajouterReclamation(ActionEvent event) {

        try {
            Reclamation reclam = new Reclamation(tf_Type.getText(), tf_Descrip.getText(), statutTxt.getValue(), tf_Date.getText());
            sr.ajouter(reclam);
            showRepReclamations();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public ObservableList<Reclamation> getReclamation() {
        ObservableList<Reclamation> reclamations = FXCollections.observableArrayList();
        String query = "select * from reclamation";
        conn = Database.getConnection();
        try {
            rec = conn.prepareStatement(query);
            rs = rec.executeQuery();
            while (rs.next()) {
                Reclamation rec = new Reclamation();
                rec.setIdReclamation(rs.getInt("idReclamation"));
                rec.setTypeReclamation(rs.getString("typeReclamation"));
                rec.setDescriptionReclamation(rs.getString("descriptionReclamation"));
                rec.setStatutReclamation(rs.getString("statutReclamation"));
                rec.setDateReclamation(rs.getString("dateReclamation"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reclamations;
    }


    @FXML
    void getData(MouseEvent event) {
        Reclamation reclamation = tableReclamations.getSelectionModel().getSelectedItem();
        id_rec = reclamation.getIdReclamation();
        x = id_rec;
        tf_Type.setText(reclamation.getTypeReclamation());
        tf_Descrip.setText(reclamation.getDescriptionReclamation());
        statutTxt.setValue(reclamation.getStatutReclamation());
        tf_Date.setText(reclamation.getDateReclamation());

    }


    @FXML
    void modifierReclamation(ActionEvent event) {

        try {
            String type=tf_Type.getText();
            String description=tf_Descrip.getText();
            String statut = statutTxt.getValue();
            String date=tf_Date.getText();
            Reclamation reclamationUpdate = new Reclamation(x,tf_Type.getText(),tf_Descrip.getText(),statutTxt.getText(),tf_Date.getText());
            sr.modifier(reclamationUpdate);
            showRepReclamations();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void exportToExcel(ActionEvent event) {
        List<Reclamation> reclamations = null;
        try {
            reclamations = sr.recuperer();
        } catch (Exception e) {
            e.printStackTrace();
            return; // or show an error message to the user
        }


        String filePath = "C:\\Users\\21624\\Desktop\\Integration\\javafx-baladity-document\\src\\main\\resources\\org\\example\\javafxbaladity\\assets\\GeneratedExcelReclm.xlsx";

        File directory = new File(filePath).getParentFile();
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                System.out.println("Failed to create directory!");
                return; // or show an error message to the user
            }
        }

        // Initialize workbook and sheet
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Reclamations");

            // Create the header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("idReclamation");
            headerRow.createCell(1).setCellValue("typeReclamation");
            headerRow.createCell(2).setCellValue("descriptionReclamation");
            headerRow.createCell(3).setCellValue("statutReclamation");
            headerRow.createCell(4).setCellValue("dateReclamation");

            // Fill data
            int rowNum = 1;
            for (Reclamation reclamation : reclamations) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(reclamation.getIdReclamation());
                row.createCell(1).setCellValue(reclamation.getTypeReclamation());
                row.createCell(2).setCellValue(reclamation.getDescriptionReclamation());
                row.createCell(3).setCellValue(reclamation.getStatutReclamation());
                row.createCell(4).setCellValue(reclamation.getDateReclamation().toString()); // assuming getDateReclamation returns a Date object
            }

            // Save the Excel file
            try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
                workbook.write(outputStream);
            }
            System.out.println("Export successful to Excel file: " + filePath);

        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
            // Show an error message to the user
        } catch (IOException e) {
            System.err.println("IO Exception: " + e.getMessage());
            // Show an error message to the user
        } catch (Exception e) {
            System.err.println(e.getMessage());
            // Show a generic error message to the user
        }
    }


    @FXML
    private void sendSMSButtonClicked(ActionEvent event) {
        // Récupérez les informations nécessaires (numéro de téléphone du destinataire, message, etc.)
        String recipientNumber = "+216"+phoneNumberTextField.getText(); // Remplacez par le numéro de téléphone réel
        String messageBody = "Nous sommes ravis de vous informer que votre réclamation a été traitée";

        // Utilisez votre service d'envoi de SMS
        SMSService.sendSMS(recipientNumber, messageBody);

        // Ajoutez des actions supplémentaires si nécessaire (notifications, mises à jour de l'interface utilisateur, etc.)
    }

    @FXML
    void ondetails(ActionEvent event) {
        add_pane.setVisible(true);
    }


    @FXML
    void closeaddpane(ActionEvent event) {
        add_pane.setVisible(false);
    }






}
















