package org.example.javafxbaladity.gui;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.example.javafxbaladity.models.Association;
import org.example.javafxbaladity.Services.AssociationHistoryService;
import org.example.javafxbaladity.Services.AssociationService;

public class AddAssociationController {
    private final AssociationService associationService = new AssociationService();
    private final AssociationHistoryService associationHistoryService = new AssociationHistoryService();
    @FXML
    public MFXComboBox<String> adresse_association;

    @FXML
    private MFXButton add_association_btn;

    @FXML
    private Label adresse_association_error;

    @FXML
    private MFXTextField caisse_association;

    @FXML
    private Label caisse_association_error;

    @FXML
    private Label description_projet_error1;

    @FXML
    private MFXTextField nom_association;

    @FXML
    private Label nom_association_error;

    @FXML
    private MFXCheckbox statut_association;

    @FXML
    private MFXComboBox<String> type_association;

    @FXML
    private Label type_association_error;

    @FXML
    void addAssociation(ActionEvent event) {
        resetErrors();
        boolean isError = false;
        String nom = nom_association.getText();
        String adresse = adresse_association.getText();
        try {
            Float.parseFloat(caisse_association.getText());
        } catch (NumberFormatException e) {
            caisse_association_error.setText("Caisse doit être un nombre");
            isError = true;
        }
        String type = type_association.getText();
        boolean statut = statut_association.isSelected();

        if (nom.isEmpty()) {
            System.out.println("Nom est obligatoire");
            nom_association_error.setText("Nom est obligatoire");
            isError = true;
        }
        if (adresse.isEmpty()) {
            adresse_association_error.setText("Adresse est obligatoire");
            isError = true;
        }
        if (type.isEmpty()) {
            type_association_error.setText("Type est obligatoire");
            isError = true;
        }

        if (isError)
            return;

        Association association = new Association(nom, adresse, type, Float.parseFloat(caisse_association.getText()), statut ? "active" : "inactive");
        try {
            associationService.create(association);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }

        nom_association.getScene().getWindow().hide();
    }

    @FXML
    void onClose() {
        nom_association.getScene().getWindow().hide();
    }

    public void initialize() {
        resetErrors();
        add_association_btn.setOnAction(this::addAssociation);
        adresse_association.getItems().addAll("Tunis", "Ariana", "Ben Arous", "Manouba", "Nabeul", "Zaghouan", "Bizerte", "Béja", "Jendouba", "Le Kef", "Siliana", "Kairouan", "Kasserine", "Sidi Bouzid", "Sousse", "Monastir", "Mahdia", "Kébili", "Gabès", "Médenine", "Tataouine", "Gafsa", "Tozeur", "Kef");
        type_association.getItems().addAll("artistiques", "religieuses", "éducatives", "professionnelles", "environnementales ", "humanitaires", "sportives", "culturelles ");
    }

    public void resetErrors() {
        nom_association_error.setText("");
        adresse_association_error.setText("");
        type_association_error.setText("");
        caisse_association_error.setText("");
    }
}
