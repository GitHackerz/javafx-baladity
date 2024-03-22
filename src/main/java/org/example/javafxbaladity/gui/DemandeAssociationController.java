package org.example.javafxbaladity.gui;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXPaginatedTableView;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import io.github.palexdev.materialfx.filter.FloatFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.javafxbaladity.models.DemandeAssociation;
import org.example.javafxbaladity.Services.DemandeAssociationService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.ResourceBundle;

public class DemandeAssociationController implements Initializable {

    @FXML
    private MFXGenericDialog AssociationManager;

    @FXML
    private Button Association_btn;

    @FXML
    private MFXButton BttnClose;

    @FXML
    private Button Documents_Btn;

    @FXML
    private Button Home_Btn;

    @FXML
    private Button Project_Btn;

    @FXML
    private Button Users_Btn;

    @FXML
    private Button btn_add;

    @FXML
    private MFXButton bttnAjouterAssociation;

    @FXML
    private Button bttnDetails;

    @FXML
    private Button bttnModifier;

    @FXML
    private Button bttnSupprimer;

    @FXML
    private MFXPaginatedTableView<DemandeAssociation> table;

    @FXML
    private MFXComboBox<String> tfAdresse;

    @FXML
    private TextField tfCaisse;

    @FXML
    private TextField tfNom;

    @FXML
    private MFXComboBox<String> tfType;


    @FXML
    void onDocumentsButtonClick(ActionEvent event) {

    }

    @FXML
    void onHomeButtonClick(ActionEvent event) {

    }


    @FXML
    void onUserButtonClick(ActionEvent event) {

    }

    private final DemandeAssociationService demandeAssociationService = new DemandeAssociationService();
    String floatRegex = "[+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            showAssociation();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        AssociationManager.setVisible(false);
        tfAdresse.getItems().addAll("Tunis", "Ariana", "Ben Arous", "Manouba", "Nabeul", "Zaghouan", "Bizerte", "Béja", "Jendouba", "Le Kef", "Siliana", "Kairouan", "Kasserine", "Sidi Bouzid", "Sousse", "Monastir", "Mahdia", "Kébili", "Gabès", "Médenine", "Tataouine", "Gafsa", "Tozeur", "Kef");
        tfType.getItems().addAll("artistiques", "religieuses", "éducatives", "professionnelles", "environnementales ", "humanitaires", "sportives", "culturelles ");
    }


    @FXML
    void AjouterAssociation1(ActionEvent event) {

        AssociationManager.setVisible(true);
        bttnModifier.setVisible(false);
        btn_add.setVisible(true);

    }

    @FXML
    void onModifierAssociationClick(ActionEvent event) throws SQLException {
        // Récupérez l'Association sélectionnée dans votre interface utilisateur
        DemandeAssociation selectedAssociation = (DemandeAssociation) table.getSelectionModel().getSelectedValue();
        if (selectedAssociation == null) {
            // Affichez un message d'erreur si aucune Association n'est sélectionnée
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de modification");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une association à modifier.");
            alert.showAndWait();
            return;
        }

        if (isValidInput()) {
            // Récupérez les informations de l'Association à partir des champs de saisie
            String nom = tfNom.getText();
            String adresse = tfAdresse.getText();
            String type = tfType.getText();

            // Vérifiez si le champ de la caisse n'est pas vide avant de convertir sa valeur en nombre flottant
            float caisse = 0; // Valeur par défaut si le champ de la caisse est vide
            String caisseText = tfCaisse.getText();
            if (!caisseText.isEmpty()) {
                caisse = Float.parseFloat(caisseText);
            }

            // Créer un objet Association avec les nouvelles informations
            DemandeAssociation updatedAssociation = new DemandeAssociation(selectedAssociation.getId(), nom, adresse, type, caisse, 1);

            // Mettre à jour l'Association dans la base de données en utilisant le service
            demandeAssociationService.update(updatedAssociation);
            // Rafraîchir la liste des associations affichées dans votre interface utilisateur
            refreshTable();
            AssociationManager.setVisible(false);
        } else {
            // Affichez un message d'erreur indiquant à l'utilisateur que les champs sont invalides
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs de manière valide.");
            alert.showAndWait();
        }
    }

    @FXML
    public void getData(javafx.scene.input.MouseEvent mouseEvent) {
        // Récupérer l'association sélectionnée dans la table
        DemandeAssociation selectedAssociation = table.getSelectionModel().getSelectedValue();

        if (selectedAssociation != null) {
            // Afficher les données de l'association dans les champs de texte
            tfNom.setText(selectedAssociation.getNom());
            tfAdresse.setText(selectedAssociation.getAdresse());
            tfCaisse.setText(Float.toString(selectedAssociation.getCaisse()));
            tfType.setText(selectedAssociation.getType());
        }
        // Ajoutez ici d'autres actions si nécessaire
    }

    @FXML
    void AjouterAssociation(ActionEvent event) throws SQLException {
        if (isValidInput()) {
            String nom = tfNom.getText();
            String adresse = tfAdresse.getText();
            String caisseText = tfCaisse.getText();
            float caisse = 0;
            if (!caisseText.isEmpty() && caisseText.matches(floatRegex)) {
                caisse = Float.parseFloat(caisseText);


            }
            if (associationExisteDeja(nom)) {
                // Afficher un message d'erreur si l'association existe déjà
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur d'ajout");
                alert.setHeaderText(null);
                alert.setContentText("Une association avec le même nom existe déjà.");
                alert.showAndWait();
                return;
            }
            if (!estNombreFlottant(caisseText)) {
                // Afficher un message d'erreur si la caisse n'est pas un nombre flottant
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setHeaderText(null);
                alert.setContentText("La caisse doit être un nombre flottant.");
                alert.showAndWait();
                return;
            }
            String type = tfType.getText();

            DemandeAssociation associationModel = new DemandeAssociation(nom, adresse, type, caisse);

            demandeAssociationService.create(associationModel);
            refreshTable();
            AssociationManager.setVisible(false);
            bttnModifier.setVisible(false);
        } else {
            // Affichez un message d'erreur indiquant à l'utilisateur que les champs sont invalides
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs de manière valide.");
            alert.showAndWait();
        }
    }

    private boolean estNombreFlottant(String Text) {
        try {
            Float.parseFloat(Text);
            return true; // La chaîne peut être convertie en nombre flottant
        } catch (NumberFormatException e) {
            return false; // La chaîne ne peut pas être convertie en nombre flottant
        }
    }

    @FXML
    void SupprimerAssociation(ActionEvent event) throws IOException, SQLException {
        System.out.println("jhjh");
        // Obtenez l'Association sélectionnée dans votre interface utilisateur
        DemandeAssociation selectedAssociation = (DemandeAssociation) table.getSelectionModel().getSelectedValue();
        System.out.println(selectedAssociation.getId());
        System.out.println(selectedAssociation.getType());
        if (selectedAssociation == null) {
            // Affichez un message d'erreur si aucune Association n'est sélectionnée
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de suppression");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une association à supprimer.");
            alert.showAndWait();
            return;
        }

        // Supprimez l'Association sélectionnée en utilisant le service approprié
        demandeAssociationService.delete(selectedAssociation.getId());
        // Rafraîchissez la liste des associations affichées dans votre interface utilisateur
        refreshTable();
    }

    private void showAssociation() throws SQLException {

        MFXTableColumn<DemandeAssociation> id_col = new MFXTableColumn<>("ID", false, Comparator.comparing(DemandeAssociation::getId));
        MFXTableColumn<DemandeAssociation> nom_col = new MFXTableColumn<>("NOM COL", false, Comparator.comparing(DemandeAssociation::getNom));
        MFXTableColumn<DemandeAssociation> adresse_col = new MFXTableColumn<>("ADR ", false, Comparator.comparing(DemandeAssociation::getAdresse));
        MFXTableColumn<DemandeAssociation> type_col = new MFXTableColumn<>("TYPE ", false, Comparator.comparing(DemandeAssociation::getType));
        MFXTableColumn<DemandeAssociation> caisse_col = new MFXTableColumn<>("CAISSE", false, Comparator.comparing(DemandeAssociation::getCaisse));

        id_col.setRowCellFactory(device -> new MFXTableRowCell<>(DemandeAssociation::getId));
        nom_col.setRowCellFactory(device -> new MFXTableRowCell<>(DemandeAssociation::getNom));
        adresse_col.setRowCellFactory(device -> new MFXTableRowCell<>(DemandeAssociation::getAdresse));
        type_col.setRowCellFactory(device -> new MFXTableRowCell<>(DemandeAssociation::getType));
        caisse_col.setRowCellFactory(device -> new MFXTableRowCell<>(DemandeAssociation::getCaisse));

        table.getFilters().addAll(
                new StringFilter<>("nom", DemandeAssociation::getNom),
                new StringFilter<>("adresse", DemandeAssociation::getAdresse),
                new StringFilter<>("type ", DemandeAssociation::getType),
                new FloatFilter<>("caissee", DemandeAssociation::getCaisse)
        );
        // Add columns to the table
        table.getTableColumns().addAll(id_col, nom_col, adresse_col, type_col, caisse_col);

        table.setItems(demandeAssociationService.readAll());
    }

    private boolean associationExisteDeja(String nom) {
        ObservableList<DemandeAssociation> associations = table.getItems();
        for (DemandeAssociation association : associations) {
            if (association.getNom().equals(nom)) {
                return true; // L'association existe déjà
            }
        }
        return false; // L'association n'existe pas encore

    }


    private void refreshTable() throws SQLException {
        table.setItems(demandeAssociationService.readAll());
    }

    private boolean isValidInput() {
        return !tfNom.getText().isEmpty() && !tfAdresse.getText().isEmpty() && !tfCaisse.getText().isEmpty() && !tfType.getText().isEmpty();

    }

    @FXML
    void openModal(org.w3c.dom.events.MouseEvent event) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("org/example/javafxbaladity/views/AjoutAssociation.fxml"));
            Parent parent = fxmlLoader.load();
            Stage modalStage = new Stage();
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.setTitle("Ajouter un forum");
            modalStage.setScene(new Scene(parent));
            modalStage.showAndWait();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void DetailsAssociation(ActionEvent event) {
        AssociationManager.setVisible(true);
        bttnModifier.setVisible(true);
        btn_add.setVisible(false);
    }

    @FXML
    void onCloseClick(ActionEvent event) {
        AssociationManager.setVisible(false);
        bttnModifier.setVisible(false);
        btn_add.setVisible(false);

    }


}

