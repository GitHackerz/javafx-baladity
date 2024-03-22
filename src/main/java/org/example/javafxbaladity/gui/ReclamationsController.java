package org.example.javafxbaladity.gui;

//import com.sun.javafx.charts.Legend;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import javax.imageio.ImageIO;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import io.github.palexdev.materialfx.filter.BooleanFilter;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.example.javafxbaladity.Main;
import org.example.javafxbaladity.Services.ServiceReclamation;
import org.example.javafxbaladity.models.Document;
import org.example.javafxbaladity.models.Reclamation;
import org.example.javafxbaladity.utils.Database;
import org.example.javafxbaladity.utils.Navigate;

import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ReclamationsController implements Initializable {

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
    private Button Load_Btn;
    @FXML
    private Button Reclamations_Btn;

    @FXML
    private Button generateQRCodeBtn;
    @FXML
    private MFXGenericDialog add_pane;

    @FXML
    void onAssociationsButtonClick(ActionEvent event) throws IOException {
//        Setup_Page("views/associations.fxml",Associations_Btn);
        Navigate.toAssociation(Associations_Btn, userRole);


    }

    @FXML
    void onEventsButtonClick(ActionEvent event) throws IOException {
//        Setup_Page("views/eventsfront.fxml",Events_Btn);
        Navigate.toEvent(Events_Btn, userRole);


    }




    @FXML
    void nextPage(ActionEvent event) throws IOException {
        Setup_Page("views/reclamation2.fxml",nextpage);
    }

    @FXML
    void onProjectsButtonClick(ActionEvent event) throws IOException {
//        Setup_Page("views/projets.fxml",Projects_Btn);
       // Navigate.toProject(Projects_Btn, userRole);

    }

    @FXML
    void onReclamationsButtonClick(ActionEvent event) throws IOException {

        Navigate.toReclamation(Reclamations_Btn, userRole);

    }



    @FXML

    public void onHomeButtonClick() throws Exception {

//        Setup_Page("views/home.fxml",Home_Btn);
        Navigate.toHome(Home_Btn);

    }
    @FXML

    public void onUserButtonClick() throws Exception {
        Navigate.toUser(Users_Btn, userRole);




    }
    @FXML
    public void onDocumentsButtonClick() throws Exception {
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

    ServiceReclamation sr = new ServiceReclamation();
    Connection conn = null;
    PreparedStatement rec = null;
    ResultSet rs = null;

    @FXML
    private MFXButton btnAjouterReclamation,Close_User1;

    @FXML
    private MFXButton btnModifierReclamation;

    @FXML
    private Button btnSupprimerAllRec;

    @FXML
    private MFXButton btnSupprimerReclamation;

    @FXML
    private TextField tf_DeleteReclamation;

    @FXML
    private TableColumn<Reclamation,String> col_date;

    @FXML
    private TableColumn <Reclamation,String> col_descrip;

    @FXML
    private TableColumn<Reclamation,Integer> col_id;

    @FXML
    private TableColumn <Reclamation,String> col_statut;

    @FXML
    private TableColumn <Reclamation,String> col_type;

    @FXML
    private MFXComboBox<String> statutTxt;

    @FXML
    private TableView<Reclamation> tableReclamations;

    @FXML
    private MFXPaginatedTableView<Reclamation> tableReclamations1;

    int id_rec=0;
    String typeRec=null;
    String dateRec=null;
    int x;
    @FXML
    private Label DescripAlerte;

    @FXML
    private Label StatutAlerte;

    @FXML
    private Label dateAlerte;
    @FXML
    private Label typeAlerte;


    @FXML
    private MFXTextField tf_Date;

    @FXML
    private MFXTextField tf_Descrip;

    @FXML
    private Button nextpage;

    @FXML
    private MFXTextField tf_Statut;

    @FXML
    private MFXTextField tf_Type;

    @FXML
    private MFXTextField tfSearch;

    @FXML
    private Button select_file_btn;





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        statutTxt.setItems(FXCollections.observableArrayList("Non Traitée"));
        getUserSession();
        try {
            //showReclamations();
            //tableReclamations.refresh();
            setupmfxrecl();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }




    }
/*
    @FXML
    public void showReclamations() throws SQLException {
        ObservableList<Reclamation> list = sr.recuperer();


        //col_id.setCellValueFactory (new PropertyValueFactory<Reclamation,Integer>("idReclamation"));
        col_type.setCellValueFactory (new PropertyValueFactory<Reclamation,String>("typeReclamation"){

        });
        col_descrip.setCellValueFactory (new PropertyValueFactory<Reclamation,String >("descriptionReclamation"));
        col_statut.setCellValueFactory (new PropertyValueFactory<Reclamation,String>("statutReclamation"));
        col_date.setCellValueFactory (new PropertyValueFactory<Reclamation,String>("dateReclamation"));

        System.out.println(list);
        tableReclamations.setItems(list);

    }*/


    private void setupmfxrecl() throws SQLException {
        MFXTableColumn<Reclamation> id_doc_col = new MFXTableColumn<>("ID Document", false, Comparator.comparing(Reclamation::getTypeReclamation));
        MFXTableColumn<Reclamation> type_doc_col = new MFXTableColumn<>("Type ", false, Comparator.comparing(Reclamation::getDescriptionReclamation));
        MFXTableColumn<Reclamation> statut_doc_col = new MFXTableColumn<>("Statut ", false, Comparator.comparing(Reclamation::getStatutReclamation));
        MFXTableColumn<Reclamation> date_emission_doc_col = new MFXTableColumn<>("Date Emission ", false, Comparator.comparing(Reclamation::getDateReclamation));


        id_doc_col.setRowCellFactory(device -> new MFXTableRowCell<>(Reclamation::getTypeReclamation));
        type_doc_col.setRowCellFactory(device -> new MFXTableRowCell<>(Reclamation::getDescriptionReclamation) {{
            setFont(Font.font("System", FontWeight.BOLD, 14));
        }});
        statut_doc_col.setRowCellFactory(device -> new MFXTableRowCell<>(Reclamation::getStatutReclamation) {{
            setAlignment(Pos.BASELINE_CENTER);
            //setStyle("-fx-background-color: red");
            setHeight(200);
            setWidth(200);
        }});
        date_emission_doc_col.setRowCellFactory(device -> new MFXTableRowCell<>(Reclamation::getDateReclamation));



        // Add columns to the table
        tableReclamations1.getTableColumns().addAll(id_doc_col, type_doc_col, statut_doc_col, date_emission_doc_col);

        tableReclamations1.setItems(sr.recuperer());


    }

    void refreshtableau() throws SQLException {
        tableReclamations1.setItems(sr.recuperer());
    }





    @FXML
    void ajouterReclamation(ActionEvent event) {
        try {

            String type = tf_Type.getText();
            String description = tf_Descrip.getText();
            String statut = statutTxt.getValue();
            String date = tf_Date.getText();

            typeAlerte.setVisible(false);
            DescripAlerte.setVisible(false);
            StatutAlerte.setVisible(false);
            dateAlerte.setVisible(false);


            if(tf_Type.getText().isEmpty())
            {
                typeAlerte.setVisible(true);
            }
            if(tf_Descrip.getText().isEmpty())
            {
                DescripAlerte.setVisible(true);
            }
            if(statutTxt.getText().isEmpty())
            {
                StatutAlerte.setVisible(true);
            }
            if(tf_Date.getText().isEmpty())
            {
                dateAlerte.setVisible(true);
            }

            if( !(tf_Type.getText().isEmpty() || tf_Descrip.getText().isEmpty() || statutTxt.getValue().isEmpty() ||  tf_Date.getText().isEmpty()))
            {

                Reclamation reclam = new Reclamation(type, description, statut, date);
                sr.ajouter(reclam);


                tf_Type.clear();
                tf_Descrip.clear();
                statutTxt.setValue(null);
                tf_Date.clear();

                refreshtableau();
                //showReclamations();
            }
        } catch (SQLException e) {
            // Gérer les erreurs de base de données
            throw new RuntimeException(e);
        }
    }
    @FXML
    void ajouterReclamation1(ActionEvent event) {
        add_pane.setVisible(true);
    }

    @FXML
    void closeaddpane(ActionEvent event) {
        add_pane.setVisible(false);
    }

    public ObservableList <Reclamation> getReclamation () {
        ObservableList <Reclamation> reclamations = FXCollections.observableArrayList ();
        String query = "select * from reclamation";
        conn = Database.getConnection();
        try {
            rec = conn.prepareStatement(query);
            rs = rec.executeQuery();
            while (rs.next()){
                Reclamation rec= new Reclamation();
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
        Reclamation reclamation=tableReclamations1.getSelectionModel().getSelectedValue();
        id_rec= reclamation.getIdReclamation();
        typeRec=reclamation.getTypeReclamation();
        dateRec=reclamation.getDateReclamation();
        x=id_rec;
        tf_Type.setText(reclamation.getTypeReclamation());
        tf_Descrip.setText(reclamation.getDescriptionReclamation());
        statutTxt.setValue(reclamation.getStatutReclamation());
        tf_Date.setText(reclamation.getDateReclamation());

    }


    @FXML
    void modifierReclamation(ActionEvent event) {
        add_pane.setVisible(true);

    }
    @FXML
    void modifierReclamation1(ActionEvent event) {
        add_pane.setVisible(false);
        try {

            String type=tf_Type.getText();
            String description=tf_Descrip.getText();
            String statut=statutTxt.getValue();
            String date=tf_Date.getText();
            Reclamation reclamationUpdate= new Reclamation(x,type,description,statut,date);
            sr.modifier(reclamationUpdate);
            refreshtableau();

            //showReclamations();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


    @FXML
    void supprimerReclamation(ActionEvent event) throws SQLException {
        sr.supprimer(x);
        //showReclamations();
        refreshtableau();


    }


    @FXML
    public void searchReclamations(ActionEvent event) {
        String searchTerm = tfSearch.getText();
        ObservableList<Reclamation> filteredReclamations = FXCollections.observableArrayList();

        for (Reclamation reclamation : tableReclamations1.getItems()) {
            if (reclamation.getTypeReclamation().toLowerCase().contains(searchTerm.toLowerCase()) ||
                    reclamation.getDescriptionReclamation().toLowerCase().contains(searchTerm.toLowerCase()) ||
                    reclamation.getStatutReclamation().toLowerCase().contains(searchTerm.toLowerCase()) ||
                    reclamation.getDateReclamation().toLowerCase().contains(searchTerm.toLowerCase())) {
                filteredReclamations.add(reclamation);
            }
            else {

            }
        }

        tableReclamations1.setItems(filteredReclamations);
    }

    @FXML
    void generateQRCodeForDemand(ActionEvent event) {
        try {


            String qrData = "Reclamation Type: " + typeRec+ "\nDate: " + dateRec; // You can customize the QR code data for the demand
            String filePath = "C:\\Users\\21624\\Desktop\\Integration\\javafx-baladity-document\\src\\main\\resources\\org\\example\\javafxbaladity\\assets"; // Specify the path where you want to save the QR codes

            // Generate QR code
            generateQRCode(qrData, filePath);

            // Show notification
            //MFXNotification notification = new MFXNotification("QR Code Generated", "QR code for demand of reclamation has been generated and saved.", MFXNotification.NotificationType.SUCCESS);
            //notification.show();

        } catch (IOException | WriterException e) {
            e.printStackTrace();
            // Handle exceptions
        }
    }

    private void generateQRCode(String data, String filePath) throws WriterException, IOException {
        int width = 300;
        int height = 300;

        Map<EncodeHintType, Object> hintMap = new HashMap<>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        hintMap.put(EncodeHintType.MARGIN, 2);

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        com.google.zxing.common.BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, width, height, hintMap);

        // Créer une BufferedImage à partir de la BitMatrix
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                bufferedImage.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }

        // Enregistrer l'image
        Path path = FileSystems.getDefault().getPath(filePath, "QRCode_Reclamation_Demand_" + id_rec + ".png");
        ImageIO.write(bufferedImage, "PNG", path.toFile());
    }






}