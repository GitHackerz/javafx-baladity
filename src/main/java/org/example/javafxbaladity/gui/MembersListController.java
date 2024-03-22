package org.example.javafxbaladity.gui;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPaginatedTableView;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.example.javafxbaladity.Main;
import org.example.javafxbaladity.Services.MembreService;
import org.example.javafxbaladity.models.Membre;
import org.example.javafxbaladity.models.evenement;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Comparator;

public class MembersListController {
    @FXML
    private MFXButton Btn_AjouterMD;

    @FXML
    private MFXButton Btn_Close;

    @FXML
    private MFXButton Btn_SuppM;

    @FXML
    private MFXButton exportToPDF;

    @FXML
    private MFXGenericDialog genericTM;

    @FXML
    private MFXPaginatedTableView<Membre> tableviewM;

    private Membre selectedMembre = new Membre();
    private evenement selectedEvent = new evenement();
    private MembreService memberService = new MembreService();

    public evenement getSelectedEvent() {
        return selectedEvent;
    }

    public void setSelectedEvent(evenement selectedEvent) {
        this.selectedEvent = selectedEvent;
    }

    @FXML
    void AddMClick(ActionEvent event) throws SQLException, IOException {
        BoxBlur blur = new BoxBlur(3, 3, 3);
        Node source = (Node) event.getSource();
        Stage primaryStage = (Stage) source.getScene().getWindow();

        primaryStage.getScene().getRoot().setEffect(blur);

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("views/events/addMember.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);
        stage.setScene(new Scene(root));

        stage.showAndWait();
        refreshTABLEviewMembre();
        primaryStage.getScene().getRoot().setEffect(null);
    }

    @FXML
    void onClose() {
        genericTM.getScene().getWindow().hide();
    }

    @FXML
    void OnsuppMClick(ActionEvent event) throws SQLException {
        memberService.delete(selectedMembre.getId());
        refreshTABLEviewMembre();
    }

    @FXML
    void exportToPDF(ActionEvent event) {
        ObservableList<Membre> membres = tableviewM.getItems();
        Stage stage = (Stage) exportToPDF.getScene().getWindow();

        try {
            // Créer un document PDF
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            // Définir le titre en rouge et en grand
            contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_BOLD, 24);
            contentStream.setLeading(30); // Espacement entre les lignes
            contentStream.newLineAtOffset(200, 750); // Position du titre
            contentStream.setNonStrokingColor(255, 0, 0); // Couleur rouge
            contentStream.showText("Liste des Membres");
            contentStream.endText();

            // Définir les coordonnées de départ pour l'écriture
            float y = page.getMediaBox().getHeight() - 100;

            // Écrire les en-têtes de colonnes
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.newLineAtOffset(50, y);
            contentStream.setNonStrokingColor(0, 0, 0); // Couleur noire pour les en-têtes
            contentStream.showText("ID");
            contentStream.newLineAtOffset(100, 0);
            contentStream.showText("Nom");
            contentStream.newLineAtOffset(100, 0);
            contentStream.showText("Prénom");
            contentStream.newLineAtOffset(100, 0);
            contentStream.showText("Age");
            contentStream.newLineAtOffset(100, 0);
            contentStream.showText("Event ID");
            contentStream.endText();

            // Écrire les données des membres
            y -= 30;
            for (Membre membre : membres) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(50, y);
                contentStream.setNonStrokingColor(0, 0, 0); // Couleur noire pour les données des membres
                contentStream.showText(String.valueOf(membre.getId()));
                contentStream.newLineAtOffset(100, 0);
                contentStream.showText(membre.getNom());
                contentStream.newLineAtOffset(100, 0);
                contentStream.showText(membre.getPrenom());
                contentStream.newLineAtOffset(100, 0);
                contentStream.showText(String.valueOf(membre.getAge()));
                contentStream.newLineAtOffset(100, 0);
                contentStream.showText(String.valueOf(membre.getEvent_id()));
                contentStream.endText();
                y -= 20;
            }

            contentStream.close();

            // Enregistrer le document PDF
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Enregistrer le PDF");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf"));
            File file = fileChooser.showSaveDialog(stage);

            if (file != null) {
                document.save(file);
                document.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void getDataM() {
        selectedMembre = tableviewM.getSelectionModel().getSelectedValue();
    }

    public void showMembres() throws SQLException {
        MFXTableColumn<Membre> id_mbr_col = new MFXTableColumn<>("ID ", false, Comparator.comparing(Membre::getId));
        MFXTableColumn<Membre> nom_mbr_col = new MFXTableColumn<>("nom ", false, Comparator.comparing(Membre::getNom));
        MFXTableColumn<Membre> prenom_eve_col = new MFXTableColumn<>("prenom ", false, Comparator.comparing(Membre::getPrenom));
        MFXTableColumn<Membre> age_mbr_col = new MFXTableColumn<>("age ", false, Comparator.comparing(Membre::getAge));
        MFXTableColumn<Membre> event_id_mbr_col = new MFXTableColumn<>("event_id", false, Comparator.comparing(Membre::getEvent_id));

        id_mbr_col.setRowCellFactory(device -> new MFXTableRowCell<>(Membre::getId));
        nom_mbr_col.setRowCellFactory(device -> new MFXTableRowCell<>(Membre::getNom));
        prenom_eve_col.setRowCellFactory(device -> new MFXTableRowCell<>(Membre::getPrenom) {{
            setAlignment(Pos.BASELINE_CENTER);
        }});
        age_mbr_col.setRowCellFactory(device -> new MFXTableRowCell<>(Membre::getAge));
        event_id_mbr_col.setRowCellFactory(device -> new MFXTableRowCell<>(Membre::getEvent_id));
        id_mbr_col.setAlignment(Pos.BASELINE_CENTER);

        // Add columns to the table
        tableviewM.getTableColumns().addAll(id_mbr_col, nom_mbr_col, prenom_eve_col, age_mbr_col, event_id_mbr_col);

        tableviewM.getFilters().addAll(
                new IntegerFilter<>("id", Membre::getId),
                new StringFilter<>("nom", Membre::getNom),
                new StringFilter<>("prenom", Membre::getPrenom),
                new IntegerFilter<>("age", Membre::getAge),
                new IntegerFilter<>("event_id", Membre::getEvent_id)

        );
        refreshTABLEviewMembre();
    }

    private void refreshTABLEviewMembre() throws SQLException {
        tableviewM.setItems(memberService.readAll());
    }

    public void initialize() throws SQLException {
        showMembres();
    }
}
