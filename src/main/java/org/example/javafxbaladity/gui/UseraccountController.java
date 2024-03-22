package org.example.javafxbaladity.gui;


import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.*;
import org.bytedeco.opencv.opencv_face.LBPHFaceRecognizer;
import org.bytedeco.opencv.opencv_objdetect.CascadeClassifier;
import org.bytedeco.opencv.opencv_videoio.VideoCapture;
import org.example.javafxbaladity.Main;
import org.example.javafxbaladity.Services.CitoyenService;
import org.example.javafxbaladity.Services.UserService;
import org.example.javafxbaladity.models.User;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import static org.bytedeco.opencv.global.opencv_imgcodecs.imencode;
import static org.bytedeco.opencv.global.opencv_imgproc.*;


public class UseraccountController implements Initializable {

    @FXML
    private MFXButton ConfirmerD0_AccountUser1;

    @FXML
    private MFXButton Confirmer_AccountUser;
    @FXML
    private MFXButton Update_AccountUser;
    @FXML
    private MFXTextField tf_UserAccountEmail;
    @FXML
    private MFXTextField tf_UserAccountNumero;

    @FXML
    private MFXButton AnnulerUpdateP_User;

    @FXML
    private Button Annuler_AccountUser;
    @FXML
    private Pane general_pane;


    @FXML
    private MFXGenericDialog Dialog_AccountUser;

    @FXML
    private MFXGenericDialog Dialog_UpdatePassword_User;

    @FXML
    private Button Documents_Btn;

    @FXML
    private Button Home_Btn;

    @FXML
    private ImageView ImageviewUser;

    @FXML
    private MFXButton UpdatePassword_User;

    @FXML
    private Button Users_Btn;

    @FXML
    private TextField tf_UserNewPassword;

    @FXML
    private MFXButton AnnulerDelete1_User;
    @FXML
    private Button ConfirmDelete1_User;
    @FXML
    private MFXGenericDialog DialogConfirm_Delete;
    @FXML
    private MFXGenericDialog Dialog_Camera;

    @FXML
    private MFXTextField tf_UserOldPassword;
    static UserSession us = ApplicationContext.getInstance().getUserSession();
    CitoyenService c = new CitoyenService() ;
    UserService u = new UserService() ;
    int userId ;
    String userName ;
    String userPrenom ;
    String userRole ;
    final  int x = 1315 ;
    final  int y = 890 ;

    @FXML
    private ImageView Camer_Label;
    @FXML
    private Button Capture_BTN;
    private int idCounter = 1;


    @FXML
    private Label counter_Label;

    private VideoCapture webSource;
    private CascadeClassifier cascade;
    private LBPHFaceRecognizer recognizer;

    private int numSamples = 25;
    private int sample = 1;
    private boolean capturing = false;
    private int[] personCounters = new int[500]; // Define MAX_PERSONS according to your requirements



    public void getUserSession() {
        UserSession userSession = ApplicationContext.getInstance().getUserSession();
        if(userSession != null) {
            userId = userSession.getUserId() ;
            userName = userSession.getUserName() ;
            userPrenom = userSession.getPrenom() ;
            userRole = userSession.getRole() ;
        }
    }

    @FXML
    void ondialogueupdatebtnClick(ActionEvent event) {
        Dialog_UpdatePassword_User.setVisible(false);
        general_pane.setEffect(null);

    }

    public void annuler1()
    {
        Dialog_AccountUser.setVisible(false);
    }
    void afficherdetails() {
        User a = u.HetUser2(userId);
        if (a.getPathImg() != null) {
            try {
                Image image = new Image("file:" + a.getPathImg());
                ImageviewUser.setImage(image);
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("Error loading image: " + ex.getMessage());
            }
        }
        tf_UserAccountEmail.setText(String.valueOf(a.getEmail()));
        tf_UserAccountNumero.setText(String.valueOf(a.getNumero()));
    }

    public void updateUser(ActionEvent event) throws SQLException {
        User a = u.HetUser2(userId);
        if(event.getSource() == Confirmer_AccountUser) {
            String email = tf_UserAccountEmail.getText();
            if (!isValidEmail(email)) {
                showAlert("Invalid Email", "Please enter a valid email address.");
                return;
            }

            String numero = tf_UserAccountNumero.getText();
            if (!isValidPhoneNumber(numero)) {
                showAlert("Invalid Phone Number", "Please enter a valid phone number with 8 digits.");
                return;
            }

            a.setEmail(email);
            a.setNumero(Integer.parseInt(numero));
            u.modify(a);
            afficherdetails();
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]+$";
        return email.matches(emailRegex);
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Fonction pour vérifier si le numéro de téléphone est valide en utilisant une expression régulière
    private boolean isValidPhoneNumber(String phoneNumber) {
        String phoneRegex = "^\\d{8}$";
        return phoneNumber.matches(phoneRegex);
    }
    public void deleteUser(ActionEvent event) throws SQLException, IOException {
        if(event.getSource()==ConfirmDelete1_User)
        {
            u.SupprimerUser(userId);
            Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
            Stage window = (Stage) ConfirmDelete1_User.getScene().getWindow();
            window.setScene(new Scene(root,x,y));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getUserSession();
        Dialog_Camera.setVisible(false);
        System.out.println(userId);
        //Dialog_AccountUser.setVisible(true);
        afficherdetails();
        Dialog_UpdatePassword_User.setVisible(false);
        DialogConfirm_Delete.setVisible(false);
    }
    public void afficherDeleteDialog()
    {
        DialogConfirm_Delete.setOpacity(0);
        DialogConfirm_Delete.setVisible(true);
        // Créer une transition de fondu pour simuler l'effet de coup d'éponge
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), DialogConfirm_Delete);
        fadeIn.setToValue(1.0);
        fadeIn.play();

        BoxBlur boxBlur = new BoxBlur();
        boxBlur.setWidth(5);
        boxBlur.setHeight(5);
        boxBlur.setIterations(3);
        general_pane.setEffect(boxBlur);

    }
    public void closeDeleteD()
    {

        DialogConfirm_Delete.setVisible(false);
        general_pane.setEffect(null);


    }
    public void afficherUpdate()
    {
        loadFaceRecognizer();
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                cameraTask();
                return null;
            }
        };

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
        Dialog_Camera.setVisible(true);


        BoxBlur boxBlur = new BoxBlur();
        boxBlur.setWidth(5);
        boxBlur.setHeight(5);
        boxBlur.setIterations(3);
        general_pane.setEffect(boxBlur);
        Dialog_Camera.setEffect(null);


        Dialog_UpdatePassword_User.setOpacity(0);
        Dialog_UpdatePassword_User.setVisible(true);
        // Créer une transition de fondu pour simuler l'effet de coup d'éponge
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), Dialog_UpdatePassword_User);
        fadeIn.setToValue(1.0);
        fadeIn.play();

        general_pane.setEffect(boxBlur);




    }
    public void closeUpdateP()
    {
        Dialog_UpdatePassword_User.setVisible(false);
        general_pane.setEffect(null);
    }

    public void  UpdatePassword(ActionEvent event)
    {
        if(event.getSource()==UpdatePassword_User)
        {
            if(u.validerPassword(userId,tf_UserOldPassword.getText()))
            {
                u.modifyPassword(userId,tf_UserNewPassword.getText());
            }
        }
    }

    private void loadFaceRecognizer() {
        recognizer = LBPHFaceRecognizer.create();
        recognizer.read("doc/Classifierlbph.yml");
    }
    private static final double CONFIDENCE_THRESHOLD = 100.0;
    private void cameraTask() {
        webSource = new VideoCapture(0);
        if (!webSource.isOpened()) {
            System.err.println("Error: Could not open camera.");
            return;
        }

        Mat cameraImage = new Mat();
        cascade = new CascadeClassifier("doc/haarcascade_frontalface_alt.xml");
        BytePointer mem = new BytePointer();

        // Load the LBPH face recognizer
        loadFaceRecognizer();

        while (true) {
            if (webSource.grab()) {
                webSource.retrieve(cameraImage);

                Mat imageGray = new Mat();
                cvtColor(cameraImage, imageGray, COLOR_BGRA2GRAY);

                RectVector detectedFaces = new RectVector();
                cascade.detectMultiScale(cameraImage, detectedFaces, 1.1, 1, 1, new Size(150, 150), new Size(500, 500));

                for (int i = 0; i < detectedFaces.size(); i++) {
                    Rect dadosFace = detectedFaces.get(i);
                    rectangle(cameraImage, dadosFace, new Scalar(255, 255, 0, 2), 3, 0, 0);

                    Mat face = new Mat(imageGray, dadosFace);
                    opencv_imgproc.resize(face, face, new Size(160, 160));

                    if (!capturing) {
                        // Recognize the face
                        IntPointer label = new IntPointer(1);
                        DoublePointer confidence = new DoublePointer(1);
                        recognizer.predict(face, label, confidence);
                        int predictedLabel = label.get(0);
                        double predictedConfidence = confidence.get(0);

                        // Check if the face is recognized with sufficient confidence
                        if (predictedLabel != -1 && predictedConfidence < CONFIDENCE_THRESHOLD) {
                            // Face recognized, update UI accordingly
                            String recognizedPersonId = "person" + predictedLabel; // Assuming the labels correspond to person IDs
                            System.out.println("Recognized face with label: " + recognizedPersonId);
                            // Update UI with recognized face details
                            updateCounterLabel(recognizedPersonId);
                                 Dialog_Camera.setVisible(false);
                                 //Dialog_UpdatePassword_User.setVisible(true);
                            Dialog_UpdatePassword_User.setOpacity(0);
                            Dialog_UpdatePassword_User.setVisible(true);
                            // Créer une transition de fondu pour simuler l'effet de coup d'éponge
                            FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), Dialog_UpdatePassword_User);
                            fadeIn.setToValue(1.0);
                            fadeIn.play();

                            BoxBlur boxBlur = new BoxBlur();
                            boxBlur.setWidth(5);
                            boxBlur.setHeight(5);
                            boxBlur.setIterations(3);
                            general_pane.setEffect(boxBlur);


                            general_pane.setEffect(null);
                            Dialog_Camera.setEffect(null);


                                 return;
                        }else {
                            System.out.println("Unknown face.");
                            // Unknown face detected
                            updateCounterLabel(0); // Update UI indicating unknown face
                        }

                    } else {
                        // Capturing mode is on
                        if (sample <= numSamples) {
                            // Save the captured face for training
                            // Use the idCounter and sample to generate a unique file name
                            String idLabel = Integer.toString(idCounter); // Convert the integer to string for the label

                            // Generate a timestamp to ensure each file name is unique
                            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

                            // Incorporate the timestamp into the file name
                            String cropped = "doc//person." + idLabel + "." + sample + "_" + timeStamp + ".jpg";

                            opencv_imgcodecs.imwrite(cropped, face);
                            updateCounterLabel(sample);
                            sample++;
                        }
                    }
                }

                // Encode the camera image to display in JavaFX ImageView
                imencode(".bmp", cameraImage, mem);
                try {
                    java.awt.Image im = ImageIO.read(new ByteArrayInputStream(mem.getStringBytes()));
                    if (im != null) {
                        WritableImage fxImage = SwingFXUtils.toFXImage((BufferedImage) im, null);
                        updateImageView(fxImage);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @FXML
    protected void startCapture(ActionEvent event) {
        capturing = true;
        sample = 1; // Reset the sample counter for the new person
        idCounter++;
    }


    private void updateImageView(WritableImage image) {
        Platform.runLater(() -> {
            Camer_Label.setImage(image);
        });
    }

    private void updateCounterLabel(int count) {
        Platform.runLater(() -> {
            counter_Label.setText(count + "/25");
            if (count == 25) {
                showAlert("Capture Complete", "You have captured 25 images.");
            }
        });
    }
    private void updateCounterLabel(String recognizedPersonId) {
        Platform.runLater(() -> {
            counter_Label.setText("Recognized Person: " + recognizedPersonId);
        });
    }

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
        Setup_Page("views/associations.fxml",Associations_Btn);

    }

    @FXML
    void onEventsButtonClick(ActionEvent event) throws IOException {
        /*FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/events.fxml"));
        Parent root = fxmlLoader.load();
        Stage window = (Stage) Events_Btn.getScene().getWindow();
        window.setScene(new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT));*/
        Setup_Page("views/eventsfront.fxml",Events_Btn);

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
    int SCREEN_WIDTH = 1200;
    int SCREEN_HEIGHT = 700;


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
