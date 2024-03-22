package org.example.javafxbaladity.gui;

import io.github.palexdev.materialfx.controls.MFXPaginatedTableView;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.FloatFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.effect.BoxBlur;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import netscape.javascript.JSObject;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.example.javafxbaladity.Main;
import org.example.javafxbaladity.Services.AssociationHistoryService;
import org.example.javafxbaladity.Services.AssociationService;
import org.example.javafxbaladity.models.Association;
import org.example.javafxbaladity.models.AssociationHistory;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Comparator;

public class AssociationController  {

    private final AssociationHistoryService associationHistoryService = new AssociationHistoryService();
    private final AssociationService assocservice = new AssociationService();

    String floatRegex = "[+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?";
    private int nbLines = 0;

    @FXML
    private Button Home_Btn, Users_Btn, Documents_Btn, Association_btn, Project_Btn, btn_pdf, History_Btn;
    private Association selectedAssociation = null;
    private WebEngine webEngine;
    @FXML
    private MFXPaginatedTableView<Association> table;


    public void initialize() {

        System.out.println("init");

        showAssociation();
        System.out.println("done init");
    }

    @FXML
    void addAssociation(ActionEvent event) throws IOException {
        BoxBlur blur = new BoxBlur(3, 3, 3);
        Node source = (Node) event.getSource();
        Stage primaryStage = (Stage) source.getScene().getWindow();

        primaryStage.getScene().getRoot().setEffect(blur);

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("views/association/addAssociation.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);
        stage.setScene(new Scene(root));

        stage.showAndWait();
        refreshTable();
        primaryStage.getScene().getRoot().setEffect(null);
    }

    @FXML
    void onHistoryBtnClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("views/association/association_history.fxml"));
        Parent root = loader.load();

        History_Btn.getScene().setRoot(root);
    }


    @FXML
    public void getData(javafx.scene.input.MouseEvent mouseEvent) {
        selectedAssociation = table.getSelectionModel().getSelectedValue();
    }

    @FXML
    void SupprimerAssociation() throws SQLException {
        if (selectedAssociation == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de suppression");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une association à supprimer.");
            alert.showAndWait();

            return;
        }
        assocservice.delete(selectedAssociation.getId());
        associationHistoryService.create(new AssociationHistory("Suppression de l'association " + selectedAssociation.getNom(), "L'association " + selectedAssociation.getNom() + " a été supprimée.", selectedAssociation));
        refreshTable();
    }

    private void showAssociation() {

        MFXTableColumn<Association> id_col = new MFXTableColumn<>("ID", false, Comparator.comparing(Association::getId));
        MFXTableColumn<Association> nom_col = new MFXTableColumn<>("NOM COL", false, Comparator.comparing(Association::getNom));
        MFXTableColumn<Association> statut_col = new MFXTableColumn<>("STATUT ", false, Comparator.comparing(Association::getStatus));
        MFXTableColumn<Association> adresse_col = new MFXTableColumn<>("ADR ", false, Comparator.comparing(Association::getAdresse));
        MFXTableColumn<Association> type_col = new MFXTableColumn<>("TYPE ", false, Comparator.comparing(Association::getType));
        MFXTableColumn<Association> caisse_col = new MFXTableColumn<>("CAISSE", false, Comparator.comparing(Association::getCaisse));

        id_col.setRowCellFactory(device -> new MFXTableRowCell<>(Association::getId));
        nom_col.setRowCellFactory(device -> new MFXTableRowCell<>(Association::getNom));
        statut_col.setRowCellFactory(device -> new MFXTableRowCell<>(Association::getStatus));
        adresse_col.setRowCellFactory(device -> new MFXTableRowCell<>(Association::getAdresse));
        type_col.setRowCellFactory(device -> new MFXTableRowCell<>(Association::getType));
        caisse_col.setRowCellFactory(device -> new MFXTableRowCell<>(Association::getCaisse));

        table.getFilters().addAll(
                new StringFilter<>("nom", Association::getNom),
                new StringFilter<>("statut", Association::getStatus),
                new StringFilter<>("adresse", Association::getAdresse),
                new StringFilter<>("type ", Association::getType),
                new FloatFilter<>("caissee", Association::getCaisse)
        );
        // Add columns to the table
        table.getTableColumns().addAll(id_col, nom_col, statut_col, adresse_col, type_col, caisse_col);

        table.setItems(assocservice.readAll());

    }

    public void refreshTable() {
        table.setItems(assocservice.readAll());
    }

    @FXML
    void onDocumentsButtonClick() {

    }

    @FXML
    void onHomeButtonClick() {

    }

    @FXML
    void onUserButtonClick() {

    }

    @FXML
    void showMap() {
        WebView webView = new WebView();
        webEngine = webView.getEngine();
        webEngine.load(Main.class.getResource("Maps.html").toExternalForm());
        addMarkers();
        Stage stage = new Stage();
        stage.setScene(new Scene(webView));
        stage.show();
    }

    @FXML
    void generatePDF() {
        Association selectedAssociation = table.getSelectionModel().getSelectedValue();
        if (selectedAssociation != null) {
            try {
                PDDocument document = new PDDocument();
                PDPage page = new PDPage();
                document.addPage(page);

                PDPageContentStream contentStream = new PDPageContentStream(document, page);

                String text = "Détails de l'association:";
                float pageWidth = page.getMediaBox().getWidth();
                float textWidth = PDType1Font.HELVETICA_BOLD.getStringWidth(text) / 1000 * 11;
                float startX = (pageWidth - textWidth) / 2;

                float logoWidth = 150;
                float logoHeight = 150;
                float logoX = pageWidth - logoWidth - 10;
                float logoY = page.getMediaBox().getHeight() - logoHeight - 10;
                String path = (Main.class.getResource("assets/logo.png").getPath()).substring(1);
                path = path.replace("/", "\\");
                path = path.replace("%20", " ");
//                System.out.println(path);
//                System.out.println("C:\\Users\\Sarra Abben\\Documents\\GitHub\\javafx-baladity\\src\\main\\resources\\org\\example\\javafxbaladity\\assets\\logo2.png");
                PDImageXObject logoImage = PDImageXObject.createFromFile(path, document);
                contentStream.drawImage(logoImage, logoX, logoY, logoWidth, logoHeight);

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                contentStream.setLeading(20);
                contentStream.newLineAtOffset(startX, 700);
                addText(contentStream, 2, text);
                contentStream.endText();
                contentStream.beginText();
                contentStream.newLineAtOffset(110, 670);
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.setNonStrokingColor(Color.BLACK);
                addText(contentStream, 1, ("Nom de l'association: " + selectedAssociation.getNom()));
                addText(contentStream, 1, ("Adresse: " + selectedAssociation.getAdresse()));
                addText(contentStream, 1, ("Type: " + selectedAssociation.getType()));
                addText(contentStream, 1, ("Caisse: " + selectedAssociation.getCaisse()));
                addText(contentStream, 1, ("Statut: " + selectedAssociation.getStatus()));
                contentStream.endText();

                PDRectangle mediaBox = page.getMediaBox();
                float padding = 10;
                float lowerLeftX = mediaBox.getLowerLeftX() + padding;
                float height = nbLines * 20 + 100;
                float lowerLeftY = mediaBox.getHeight() - height - 2 * padding;
                float width = mediaBox.getWidth() - padding;


                float borderWidth = 2;
                contentStream.setLineWidth(borderWidth);
                contentStream.addRect(lowerLeftX, lowerLeftY, width, height);
                contentStream.stroke();

                contentStream.close();

                // Spécifier le chemin où vous souhaitez enregistrer le fichier PDF
                String filePath = System.getProperty("user.home") + "/Downloads/" + selectedAssociation.getNom() + ".pdf";
                document.save(filePath);
                document.close();

                // Afficher un message de confirmation
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("PDF Généré");
                alert.setHeaderText(null);
                alert.setContentText("Le fichier PDF a été généré avec succès !");
                alert.showAndWait();
            } catch (IOException e) {
                System.err.println(e.getMessage());
                // Gérer les erreurs d'entrée/sortie ici
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de génération PDF");
                alert.setHeaderText(null);
                alert.setContentText("Une erreur s'est produite lors de la génération du PDF.");
                alert.showAndWait();
            }
        } else {
            // Afficher un message d'erreur si aucune association n'est sélectionnée
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de génération PDF");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une association pour générer le PDF.");
            alert.showAndWait();
        }
    }

    private void addText(PDPageContentStream contentStream, int nbAddedLines, String text) throws IOException {
        contentStream.showText(text);
        for (int i = 0; i < nbAddedLines; i++) {
            contentStream.newLine();
        }
        this.nbLines++;
    }

    public void DetailsAssociation(ActionEvent event) throws IOException, SQLException {
        if (selectedAssociation == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de détails");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une association pour voir les détails.");
            alert.showAndWait();
            return;
        }
        BoxBlur blur = new BoxBlur(3, 3, 3);
        Node source = (Node) event.getSource();
        Stage primaryStage = (Stage) source.getScene().getWindow();

        primaryStage.getScene().getRoot().setEffect(blur);

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("views/association/editAssociation.fxml"));
        Parent root = loader.load();

        EditAssociationController controller = loader.getController();
        controller.setSelectedAssociation(selectedAssociation);

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);
        stage.setScene(new Scene(root));

        stage.showAndWait();
        associationHistoryService.create(new AssociationHistory("Modification de l'association " + selectedAssociation.getNom(), "L'association " + selectedAssociation.getNom() + " a été modifiée.", selectedAssociation));
        refreshTable();
        primaryStage.getScene().getRoot().setEffect(null);
    }


    private void addMarkers() {
        // Get the JavaScript window object
        JSObject window = (JSObject) webEngine.executeScript("window");

        // Create an array of marker data
        Object[] markers = {
                new Marker(51.5074, -0.1278, "London"),
                new Marker(48.8566, 2.3522, "Paris"),
                new Marker(40.7128, -74.0060, "New York")
        };

        // Pass the markers array to the JavaScript function
        window.call("addMarkers", markers);
    }

    public static class Marker {
        public double lat;
        public double lon;
        public String name;

        public Marker(double lat, double lon, String name) {
            this.lat = lat;
            this.lon = lon;
            this.name = name;
        }
    }


}










