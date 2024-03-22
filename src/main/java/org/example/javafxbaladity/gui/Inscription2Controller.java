package org.example.javafxbaladity.gui;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.bytedeco.javacpp.indexer.FloatRawIndexer;
import org.bytedeco.opencv.global.opencv_core;
import org.bytedeco.opencv.global.opencv_dnn;
import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.opencv_core.*;
import org.bytedeco.opencv.opencv_dnn.Net;
import org.bytedeco.opencv.opencv_face.LBPHFaceRecognizer;
import org.bytedeco.opencv.opencv_videoio.VideoCapture;
import org.example.javafxbaladity.Main;
import org.example.javafxbaladity.Services.UserService;
import org.example.javafxbaladity.models.Citoyen;
import org.example.javafxbaladity.models.User;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import static org.bytedeco.opencv.global.opencv_imgproc.rectangle;


public class Inscription2Controller implements Initializable {
    UserService u = new UserService() ;
    UserSession us = ApplicationContext.getInstance().getUserSession();
    @FXML
    private ImageView Camer_Label;
    @FXML
    private MFXButton Capture_BTN;
    private VideoCapture webSource;
    private LBPHFaceRecognizer recognizer;
    private Net dnnFaceDetector;
    private boolean capturing = false;

    @FXML
    private MFXGenericDialog Dialog_camera;

    private int numSamples = 25, sample = 1, idCounter = 1;
    private static final String DNN_MODEL = "doc//deploy.prototxt.txt";
    private static final String DNN_WEIGHTS ="doc//res10_300x300_ssd_iter_140000.caffemodel";

    @FXML
    private MFXButton Sinscrire_User_Btn;

    @FXML
    private MFXTextField tf_UserEmail_Inscrip;

    @FXML
    private MFXTextField tf_UserNumero_Inscrip;

    @FXML
    private MFXTextField tf_UserPassword_Inscrip;

    @FXML
    private TextField tf_Userid_Inscrip;

    @FXML
    private MFXGenericDialog Dialog_user;

    @FXML
    private MFXButton Configrmer_user_btn;

    @FXML
    private Button Reclam_user_Btn;

    @FXML
    private TextField tf_PrenomCitoyen_verif;

    @FXML
    private TextField tf_adresseCitoyen_verif;

    @FXML
    private TextField tf_dateCitoyen_verif;

    @FXML
    private TextField tf_idCitoyen_Verif;

    @FXML
    private TextField tf_nomCitoyen_verif;

    @FXML
    private HBox tf_userCin_verif;

    @FXML
    private MFXTextField tf_userCin_valid;

    @FXML
    private HBox tf_userDateN;

    @FXML
    private HBox tf_userNom;

    @FXML
    private HBox tf_userPrenom;

    @FXML
    private MFXButton ConfirmerData_user,Annuler_Emp,CloseDocReq1;
    @FXML
    private MFXGenericDialog Dialog_inscriptio;
    @FXML
    private MFXButton Sinscrire_User_Image;

    private volatile boolean running = true;

    @FXML
    private MFXButton Capture_CloseBTN1;

    private String selectedImagePath;



    private    int idC = 0  ;
    private    int CinUser ;
    final  int x = 1315 ;
    final  int y = 890 ;



    public void onConfirmerCinBtn(ActionEvent event) throws Exception {
        if (event.getSource() == Configrmer_user_btn) {
            String cinStr = tf_userCin_valid.getText();

            // Vérifier si le CIN a une longueur de 8 chiffres
            if (cinStr.length() != 8 || !cinStr.matches("\\d+")) {
                // Afficher une alerte si le CIN n'est pas valide
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Le CIN doit comporter exactement 8 chiffres.");
                alert.showAndWait();
                return;
            }

            int cin = Integer.parseInt(cinStr);

            // Vérifier si un citoyen correspondant au CIN existe dans la base de données
            Citoyen c = u.verifierCinUser(cin);
            if (c == null) {
                // Afficher une alerte si aucun citoyen correspondant n'est trouvé
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Aucun citoyen correspondant trouvé pour le CIN spécifié.");
                alert.showAndWait();
                return;
            }
            Dialog_user.setVisible(true);

            int z = Integer.parseInt(tf_userCin_valid.getText()) ;
            CinUser = z ;
            System.out.println(CinUser);


            // Remplir les champs avec les informations du citoyen trouvé
            tf_nomCitoyen_verif.setText(c.getNomCitoyen());
            tf_PrenomCitoyen_verif.setText(c.getPrenomCitoyen());
            tf_adresseCitoyen_verif.setText(c.getAdresseCitoyen());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = dateFormat.format(c.getDateNCitoyen());
            tf_dateCitoyen_verif.setText(formattedDate);
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Dialog_camera.setVisible(false);

        if (Dialog_user != null) {
            Dialog_user.setVisible(false);
            Dialog_inscriptio.setVisible(false);
        } else {
            System.err.println("Dialog_user is not properly initialized!");
        }

    }

    @FXML
    void annulerEmpBTN(ActionEvent event) {
        Dialog_user.setVisible(false);
    }

    @FXML
    void closeaddpane1(ActionEvent event) {
        Dialog_inscriptio.setVisible(false);
        stopC();
    }

    public void OninterfaceInscripBTN(ActionEvent event) throws Exception
    {
        if(event.getSource()==ConfirmerData_user)
        {

            Dialog_user.setVisible(false);
        idC = u.idCitoyen(CinUser) ;
        System.out.println(idC);
            Dialog_inscriptio.setVisible(true);
             Dialog_camera.setVisible(true);
            loadFaceRecognizer();
            loadDnnModel();
            startCamera();
        System.out.println(idC);
        addUser_BTN(new ActionEvent());
        }


    }
    public void stopC()
    {
        Dialog_camera.setVisible(false);
        running = false;
        if (webSource != null) {
            webSource.release();
        }

    }


    @FXML
    public void addUser_BTN(ActionEvent event) throws SQLException, ParseException, NoSuchAlgorithmException, IOException {
        if (event.getSource() == Sinscrire_User_Btn) {
            System.out.println(idC);

            String email = tf_UserEmail_Inscrip.getText();
            if (!isValidEmail(email)) {
                showAlert("Invalid Email", "Please enter a valid email address.");
                return;
            }

            String numero = tf_UserNumero_Inscrip.getText();
            if (!isValidPhoneNumber(numero)) {
                showAlert("Invalid Phone Number", "Please enter a valid phone number with 8 digits.");
                return;
            }

            int idCitoyen = idC;
            if (idCitoyen != -1) {
                if (u.idCitoyenExists(idCitoyen)) {
                    showAlert("Invalid IdCitoyen", "un compte associcé a cet utilisiteur deja crée ");
                    return;

                }
                if (u.emailExists(email)) {
                    showAlert("Invalid Email", "un compte associcé a cet email deja crée ");
                    return;

                }

                User u1 = new User(
                        email,
                        tf_UserPassword_Inscrip.getText(),
                        Integer.parseInt(numero),
                        "user",
                        null,
                        null,
                        selectedImagePath,
                        idCitoyen);

                System.out.println(u1);

                u.ajouterUser(u1);
               /* Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
                Stage window = (Stage) Sinscrire_User_Btn.getScene().getWindow();
                window.setScene(new Scene(root, x, y));*/
                Setup_Page("views/login.fxml",Sinscrire_User_Btn);

            } else {
                System.err.println("idC is not assigned a valid value!");
            }

        }


    }

    int SCREEN_WIDTH = 1200;
    int SCREEN_HEIGHT = 700;
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


    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]+$";
        return email.matches(emailRegex);
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        String phoneRegex = "^\\d{8}$";
        return phoneNumber.matches(phoneRegex);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void addImageUser_BTN()
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open a file");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Desktop"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPEG Image", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG Image", "*.png"),
                new FileChooser.ExtensionFilter("All image files", "*.jpg", "*.png")
        );

        // Show the file chooser dialog
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            // Store the path of the selected file in the global variable
            selectedImagePath = selectedFile.getAbsolutePath();
        } else {
            System.out.println("No file has been selected");
        }
    }
    private void loadFaceRecognizer() {
        recognizer = LBPHFaceRecognizer.create();
        recognizer.read("doc//classifierLBPH.yml");
    }

    private void loadDnnModel() {
        dnnFaceDetector = opencv_dnn.readNetFromCaffe(DNN_MODEL, DNN_WEIGHTS);
    }

    private void startCamera() {
        webSource = new VideoCapture(0);
        if (!webSource.isOpened()) {
            System.err.println("Error: Could not open camera.");
            return;
        }

        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                cameraTask();
                return null;
            }
        };

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    private void cameraTask() {
        Mat cameraImage = new Mat();
        while (running) {
            if (webSource.read(cameraImage)) {
                detectAndDisplay(cameraImage);
            }
        }
    }

    private void detectAndDisplay(Mat frame) {
        Mat blob = opencv_dnn.blobFromImage(frame, 1.0, new Size(300, 300),
                new Scalar(104.0, 177.0, 123.0, 0), false, false, opencv_core.CV_32F);
        dnnFaceDetector.setInput(blob);
        Mat detections = dnnFaceDetector.forward();
        FloatRawIndexer indexer = detections.createIndexer();

        for (int i = 0; i < detections.size(3); i++) {
            float confidence = indexer.get(0, 0, i, 2);
            if (confidence > 0.5) {
                int xLeftBottom = (int) (indexer.get(0, 0, i, 3) * frame.cols());
                int yLeftBottom = (int) (indexer.get(0, 0, i, 4) * frame.rows());
                int xRightTop = (int) (indexer.get(0, 0, i, 5) * frame.cols());
                int yRightTop = (int) (indexer.get(0, 0, i, 6) * frame.rows());

                rectangle(frame, new Point(xLeftBottom, yLeftBottom), new Point(xRightTop, yRightTop),
                        Scalar.GREEN, 2, 8, 0);

                if (capturing && sample <= numSamples) {
                    Rect rect = new Rect(xLeftBottom, yLeftBottom, xRightTop - xLeftBottom, yRightTop - yLeftBottom);
                    Mat face = new Mat(frame, rect);
                    String filename = "detected_face_" + idCounter + "_" + sample + ".png";
                    String filePath = "doc/" + filename; // Ensure this directory exists
                    opencv_imgcodecs.imwrite(filePath, face);
                    sample++;
                }
            }
        }
        indexer.release();
        displayFrame(frame);
    }

    private void displayFrame(Mat frame) {
        // Conversion to BufferedImage
        BufferedImage bufferedImage = new BufferedImage(frame.cols(), frame.rows(), BufferedImage.TYPE_3BYTE_BGR);
        byte[] data = new byte[frame.cols() * frame.rows() * (int)frame.elemSize()];
        frame.data().get(data);
        bufferedImage.getRaster().setDataElements(0, 0, frame.cols(), frame.rows(), data);

        // JavaFX image
        WritableImage writableImage = SwingFXUtils.toFXImage(bufferedImage, null);
        Platform.runLater(() -> {
            Camer_Label.setImage(writableImage);
        });
    }

    @FXML
    protected void startCapture(ActionEvent event) {
        capturing = !capturing; // Toggle capturing state
        Platform.runLater(() -> {
            if (capturing) {
                sample = 1; // Reset the sample counter for the new session
                idCounter++; // Increment the ID for the new session
                Capture_BTN.setText("Stop Capture");
            } else {
                Capture_BTN.setText("Start Capture");
            }
        });
    }

}
