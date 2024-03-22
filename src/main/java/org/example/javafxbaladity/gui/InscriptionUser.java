package org.example.javafxbaladity.gui;


import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
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
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;
import org.example.javafxbaladity.Main;
import org.example.javafxbaladity.Services.CitoyenService;
import org.example.javafxbaladity.Services.UserService;
import org.example.javafxbaladity.models.Citoyen;
import org.example.javafxbaladity.models.User;
import org.example.javafxbaladity.utils.Navigate;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;


public class InscriptionUser implements Initializable {
    CitoyenService c = new CitoyenService();
    UserService u = new UserService();
    @FXML
    private CheckBox check_male;

    @FXML
    private CheckBox check_female;

    @FXML
    private MFXGenericDialog Dialog_UserCitoyen;

    @FXML
    private MFXTextField tf_PrenomCitoyen;

    @FXML
    private Button Close_User,Close_User1;
    @FXML
    private MFXTextField tf_adresseCitoyen;

    @FXML
    private MFXTextField tf_cinCitoyen;

    @FXML
    private MFXTextField tf_dateCitoyen;

    @FXML
    private MFXTextField tf_idCitoyen;

    @FXML
    private MFXTextField tf_nomCitoyen;

    @FXML
    private TextField tf_PrenomCitoyen1;


    @FXML
    private TextField tf_adresseCitoyen1;

    @FXML
    private TextField tf_cinCitoyen1;

    @FXML
    private TextField tf_dateCitoyen1;

    @FXML
    private TextField tf_idCitoyen1;

    @FXML
    private TextField tf_nomCitoyen1;

    @FXML
    private TableView<Citoyen> tv_Citoyen;

    @FXML
    private TableColumn<Citoyen, String> tv_Citoyen_Adresse;

    @FXML
    private TableColumn<Citoyen, Integer> tv_Citoyen_Cin;

    @FXML
    private TableColumn<Citoyen, Date> tv_Citoyen_DateN;

    @FXML
    private TableColumn<Citoyen, Integer> tv_Citoyen_Id;

    @FXML
    private TableColumn<Citoyen, String> tv_Citoyen_Nom;

    @FXML
    private TableColumn<Citoyen, String> tv_Citoyen_Prenom;

    @FXML
    private Button update_Citoyen_btn;

    @FXML
    private Button add_Citoyen_btn;

    @FXML
    private Button delete_Citoyen_btn;

    @FXML
    private Button Documents_Btn;

    @FXML
    private Button Home_Btn;

    @FXML
    private Button Users_Btn;
    @FXML
    private TextField tf_Citoyen_deleted;

    @FXML
    private TableColumn<User, Integer> tv_user_email;

    @FXML
    private TableColumn<User, Integer> tv_user_id;

    @FXML
    private TableColumn<User, String> tv_user_idC;

    @FXML
    private TableColumn<User, String> tv_user_numero;

    @FXML
    private TableColumn<User, String> tv_user_password;

    @FXML
    private TableView<User> tv_users;

    @FXML
    private TableColumn<User, String> tv_user_button;

    @FXML
    private Button Annuler_Emp;

    @FXML
    private Button Confirmer_Emp;

    @FXML
    private MFXGenericDialog Dialog_emp;

    @FXML
    private TextField tf_HeureDebutUser;

    @FXML
    private TextField tf_HeureFinUser;

    @FXML
    private TextField tf_IdEmpUser;

    @FXML
    private TableColumn<Citoyen, String> tv_Citoyen_genre;
    @FXML
    private Button Afficher_Emp_BTN;
    @FXML
    private MFXComboBox<String> combo_genre;
    @FXML
    private MFXGenericDialog Dialog_viewEmp;
    static UserSession us = ApplicationContext.getInstance().getUserSession();

    @FXML
    private MFXButton addcitoyen_btn;


    @FXML
    private MFXGenericDialog add_citoyen_pane;
    @FXML
    private Button CinBTN;

    @FXML
    private Pane general_pane;
    int userId;
    String userName;
    String userPrenom;
    String userRole;

    int test ;



    public void getUserSession() {
        UserSession userSession = ApplicationContext.getInstance().getUserSession();
        if (userSession != null) {
            userId = userSession.getUserId();
            userName = userSession.getUserName();
            userPrenom = userSession.getPrenom();
            userRole = userSession.getRole();
        }
    }

    public void showCitoyen() {
        ObservableList<Citoyen> y = c.getCitoyenList();
        System.out.println(y);
        tv_Citoyen_Id.setCellValueFactory(new PropertyValueFactory<Citoyen, Integer>("idCitoyen"));
        tv_Citoyen_Cin.setCellValueFactory(new PropertyValueFactory<Citoyen, Integer>("cinCitoyen"));
        tv_Citoyen_Nom.setCellValueFactory(new PropertyValueFactory<Citoyen, String>("nomCitoyen"));
        tv_Citoyen_Prenom.setCellValueFactory(new PropertyValueFactory<Citoyen, String>("prenomCitoyen"));
        tv_Citoyen_Adresse.setCellValueFactory(new PropertyValueFactory<Citoyen, String>("adresseCitoyen"));
        tv_Citoyen_DateN.setCellValueFactory(new PropertyValueFactory<Citoyen, Date>("dateNCitoyen"));
        tv_Citoyen_genre.setCellValueFactory(new PropertyValueFactory<Citoyen, String>("genre"));


        tv_Citoyen.setItems(y);
    }

    @FXML
    private void addCitoyen_BTN(ActionEvent event) throws SQLException, ParseException {
        if (event.getSource() == add_Citoyen_btn) {
            if (isInputValid()) {

                String cin = tf_cinCitoyen.getText();
                if (c.cinExists(cin)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Le CIN existe déjà dans la base de données!");
                    alert.showAndWait();
                    return;
                }


                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = dateFormat.parse(tf_dateCitoyen.getText());
                Citoyen b1 = new Citoyen(Integer.parseInt(tf_cinCitoyen.getText()), tf_nomCitoyen.getText(), tf_PrenomCitoyen.getText(), tf_adresseCitoyen.getText(),
                        date,combo_genre.getSelectionModel().getSelectedItem().toString());
                c.ajouterCitoyen(b1);
                showCitoyen();
                vider();
            }
        }
    }


    private boolean isInputValid() {
        String errorMessage = "";

        String cin = tf_cinCitoyen.getText();
        if (cin == null || cin.isEmpty()) {
            errorMessage += "CIN cannot be empty!\n";
        } else if (!cin.matches("\\d{8}")) {
            errorMessage += "CIN must be an 8-digit number!\n";
        }
        String nom = tf_nomCitoyen.getText();
        if (nom == null || nom.isEmpty()) {
            errorMessage += "Name cannot be empty!\n";
        }

        String dateStr = tf_dateCitoyen.getText();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        try {
            Date date = dateFormat.parse(dateStr);
            if (!dateFormat.format(date).equals(dateStr)) {
                throw new ParseException("Invalid date format", 0);
            }
        } catch (ParseException e) {
            errorMessage += "Date must be in the format YYYY-MM-DD!\n";
        }

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText(null);
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }



    @FXML
    private void DeleteCitoyen_BTN(ActionEvent event) throws SQLException {
        int id = Integer.parseInt(tf_Citoyen_deleted.getText());
        if (event.getSource() == delete_Citoyen_btn) {
            c.supprimerCitoyen2(id);
            showCitoyen();
            showUsers();
            vider();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Le citoyen a été supprimé avec succès !");
            alert.showAndWait();
        }
    }


    @FXML
    private void UpdateCitoyen_BTN(ActionEvent event) throws SQLException, ParseException {
        if (!isInputValid()) {
            return;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new java.sql.Date(dateFormat.parse(tf_dateCitoyen.getText()).getTime());

        if (event.getSource() == update_Citoyen_btn) {
            c.modify(test,
                    Integer.parseInt(tf_cinCitoyen.getText()),
                    tf_nomCitoyen.getText(),
                    tf_PrenomCitoyen.getText(),
                    tf_adresseCitoyen.getText(),
                    (java.sql.Date) date,combo_genre.getSelectionModel().getSelectedItem().toString());

            showCitoyen();
            showUsers();
            vider();
        }
    }



    public void vider() {

        tf_PrenomCitoyen.setText("");
        tf_dateCitoyen.setText("");
        tf_nomCitoyen.setText("");
        tf_adresseCitoyen.setText("");
        tf_cinCitoyen.setText("");
        System.out.println(userId);
    }
    private String selectedImagePath;

    public void convertimageToCin()
{
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open a file");
    fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Desktop"));
    fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("JPEG Image", "*.jpeg"),
            new FileChooser.ExtensionFilter("PNG Image", "*.png", "*.PNG"),
            new FileChooser.ExtensionFilter("All image files", "*.jpg", "*.png")
    );

    // Show the file chooser dialog
    File selectedFile = fileChooser.showOpenDialog(null);

    if (selectedFile != null) {
        // Store the path of the selected file in the global variable
        selectedImagePath = selectedFile.getAbsolutePath();
        String text = OCRUtility.extractTextFromImage(selectedImagePath);

        // Use the updated method to extract the number
        String number = OCRUtility.extractNumberFromText(text);
        tf_cinCitoyen.setText(number);
    } else {
        System.out.println("No file has been selected");
    }
}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> list= FXCollections.observableArrayList("female","male");
        combo_genre.setItems(list);

        if (Dialog_emp != null) {

            Dialog_emp.setVisible(false);
        } else {
            System.err.println("Dialog_user is not properly initialized!");
        }
        if (Dialog_UserCitoyen != null) {

            Dialog_UserCitoyen.setVisible(false);
        } else {
            System.err.println("Dialog_user is not properly initialized!");
        }
        tv_Citoyen.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                tf_Citoyen_deleted.setText(String.valueOf(newSelection.getIdCitoyen()));
            }
        });

        tv_Citoyen.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                Citoyen selectedCitoyen = tv_Citoyen.getSelectionModel().getSelectedItem();
                if (selectedCitoyen != null) {
                    tf_cinCitoyen.setText(String.valueOf(selectedCitoyen.getCinCitoyen()));
                    tf_nomCitoyen.setText(selectedCitoyen.getNomCitoyen());
                    tf_PrenomCitoyen.setText(selectedCitoyen.getPrenomCitoyen());
                    tf_adresseCitoyen.setText(selectedCitoyen.getAdresseCitoyen());
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String formattedDate = dateFormat.format(selectedCitoyen.getDateNCitoyen());
                    test = selectedCitoyen.getIdCitoyen() ;
                    System.out.println(test);
                    tf_dateCitoyen.setText(formattedDate);

                    add_citoyen_pane.setOpacity(0);
                    add_citoyen_pane.setVisible(true);
                    // Créer une transition de fondu pour simuler l'effet de coup d'éponge
                    FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), add_citoyen_pane);
                    fadeIn.setToValue(1.0);
                    fadeIn.play();

                    BoxBlur boxBlur = new BoxBlur();
                    boxBlur.setWidth(5);
                    boxBlur.setHeight(5);
                    boxBlur.setIterations(3);
                    general_pane.setEffect(boxBlur);

                }
            }
        });
        tv_users.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                User a = tv_users.getSelectionModel().getSelectedItem();
                if (a != null) {
                    Dialog_emp.setOpacity(0);
                    Dialog_emp.setVisible(true);
                    // Créer une transition de fondu pour simuler l'effet de coup d'éponge
                    FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), Dialog_emp);
                    fadeIn.setToValue(1.0);
                    fadeIn.play();

                    BoxBlur boxBlur = new BoxBlur();
                    boxBlur.setWidth(5);
                    boxBlur.setHeight(5);
                    boxBlur.setIterations(3);
                    general_pane.setEffect(boxBlur);


                    tf_HeureDebutUser.setText(String.valueOf(a.getHeureDebut()));
                    tf_IdEmpUser.setText(String.valueOf(a.getId()));
                    tf_HeureFinUser.setText(String.valueOf(a.getHeureFin()));

                }
            }
        });

        showCitoyen();
        showUsers();
        getUserSession();
    }

    public void annulerEmpBTN() {
        Dialog_emp.setVisible(false);
        general_pane.setEffect(null);
    }

    public void annulerUserBTN() {
        Dialog_UserCitoyen.setVisible(false);
        general_pane.setEffect(null);
    }


    @FXML
    private void AddEmploye_BTN(ActionEvent event) throws SQLException, ParseException {
        if (event.getSource() == Confirmer_Emp) {
            u.updateEmployee(Integer.parseInt(tf_IdEmpUser.getText()), "employe", tf_HeureDebutUser.getText(), tf_HeureFinUser.getText());
            Dialog_emp.setVisible(false);

        }
    }

    @FXML
    private void viewEmp() throws SQLException, ParseException, IOException {
        Setup_Page("views/Employe.fxml",Afficher_Emp_BTN);

    }

    public void showUsers() {
        ObservableList<User> y = u.getUsersList();
        tv_user_id.setCellValueFactory(new PropertyValueFactory<User, Integer>("id"));
        tv_user_email.setCellValueFactory(new PropertyValueFactory<User, Integer>("email"));
        tv_user_password.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
        tv_user_numero.setCellValueFactory(new PropertyValueFactory<User, String>("numero"));
        tv_user_idC.setCellValueFactory(new PropertyValueFactory<User, String>("idCitoyen"));
        Callback<TableColumn<User, String>, TableCell<User, String>> cellFactory = (param) ->
        {
            final TableCell<User, String> cell = new TableCell<User, String>() {

                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button details = new Button("details ");
                        details.setOnAction(event -> {
                            User u1 = getTableView().getItems().get(getIndex());


                            Citoyen c2 = u.HetCitoyen(u1.getIdCitoyen());

                            Dialog_UserCitoyen.setOpacity(0);
                            Dialog_UserCitoyen.setVisible(true);
                            // Créer une transition de fondu pour simuler l'effet de coup d'éponge
                            FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), Dialog_UserCitoyen);
                            fadeIn.setToValue(1.0);
                            fadeIn.play();

                            BoxBlur boxBlur = new BoxBlur();
                            boxBlur.setWidth(5);
                            boxBlur.setHeight(5);
                            boxBlur.setIterations(3);
                            general_pane.setEffect(boxBlur);

                            tf_idCitoyen1.setText(String.valueOf(c2.getIdCitoyen()));
                            tf_cinCitoyen1.setText(String.valueOf(c2.getCinCitoyen()));
                            tf_nomCitoyen1.setText(c2.getNomCitoyen());
                            tf_PrenomCitoyen1.setText(c2.getPrenomCitoyen());
                            tf_adresseCitoyen1.setText(c2.getAdresseCitoyen());
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            String formattedDate = dateFormat.format(c2.getDateNCitoyen());

                            tf_dateCitoyen1.setText(formattedDate);


                        });
                        setGraphic(details);
                        setText(null);
                    }


                }

                ;


            };


            return cell;
        };
        tv_user_button.setCellFactory(cellFactory);

        tv_users.setItems(y);
    }



    int SCREEN_WIDTH = 1200;
    int SCREEN_HEIGHT = 700;




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
    void onAssociationsButtonClick(ActionEvent event) throws IOException {
        /*FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/association.fxml"));
        Parent root = fxmlLoader.load();
        Stage window = (Stage) Associations_Btn.getScene().getWindow();
        window.setScene(new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT));*/
//        Setup_Page("views/associations.fxml",Associations_Btn);
        Navigate.toAssociation(Associations_Btn, userRole);


    }

    @FXML
    void onEventsButtonClick(ActionEvent event) throws IOException {
        /*FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/events.fxml"));
        Parent root = fxmlLoader.load();
        Stage window = (Stage) Events_Btn.getScene().getWindow();
        window.setScene(new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT));*/
//        Setup_Page("views/eventsfront.fxml",Events_Btn);
        Navigate.toEvent(Events_Btn, userRole);


    }

    @FXML
    void onProjectsButtonClick(ActionEvent event) throws IOException {
       /* FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/projects.fxml"));
        Parent root = fxmlLoader.load();
        Stage window = (Stage) Projects_Btn.getScene().getWindow();
        window.setScene(new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT));*/
//        Setup_Page("views/projets.fxml",Projects_Btn);
        Navigate.toProject(Projects_Btn, userRole);

    }

    @FXML
    void onReclamationsButtonClick(ActionEvent event) throws IOException {
        /*FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/reclamations.fxml"));
        Parent root = fxmlLoader.load();
        Stage window = (Stage) Reclamations_Btn.getScene().getWindow();
        window.setScene(new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT));*/
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
    void closeaddpane(ActionEvent event) {

        add_citoyen_pane.setVisible(false);
        general_pane.setEffect(null);
    }

    @FXML
    void onaddcitoyenbtnclick(ActionEvent event) {
        add_citoyen_pane.setOpacity(0);
        add_citoyen_pane.setVisible(true);
        // Créer une transition de fondu pour simuler l'effet de coup d'éponge
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), add_citoyen_pane);
        fadeIn.setToValue(1.0);
        fadeIn.play();

        BoxBlur boxBlur = new BoxBlur();
        boxBlur.setWidth(5);
        boxBlur.setHeight(5);
        boxBlur.setIterations(3);
        general_pane.setEffect(boxBlur);


    }


}
