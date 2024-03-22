package org.example.javafxbaladity.gui;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.example.javafxbaladity.Services.EventService;
import org.example.javafxbaladity.Services.MembreService;
import org.example.javafxbaladity.models.Membre;
import org.example.javafxbaladity.models.evenement;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddMemberController {

    @FXML
    private MFXButton BtnAjoutM;

    @FXML
    private MFXTextField Age_Textfield;

    @FXML
    private MFXComboBox<String> Events_combo;

    @FXML
    private MFXTextField Nom_Textfield;

    @FXML
    private MFXTextField Prenom_Textfield;

    @FXML
    private Label erreurAge1;

    @FXML
    private Label erreurAge2;

    @FXML
    private Label erreurDoublon;

    @FXML
    private Label erreurNom;

    @FXML
    private Label erreurPrenom;

    private final EventService eventService = new EventService();
    private final MembreService memberService = new MembreService();

    @FXML
    public void onClose() {
        BtnAjoutM.getScene().getWindow().hide();
    }


    @FXML
    void OnBtnAjouM() throws SQLException {
        String selectedEventTitle = Events_combo.getValue();

        if (Nom_Textfield.getText().isEmpty()) {
            erreurNom.setText("Le nom est obligatoire");
            return;
        }
        if (Prenom_Textfield.getText().isEmpty()) {
            erreurPrenom.setText("Le prénom est obligatoire");
            return;
        }
        if (Age_Textfield.getText().isEmpty()) {
            erreurAge1.setText("l'age est obligatoire");
            return;
        }

        // age> 18
        int age;
        try {
            age = Integer.parseInt(Age_Textfield.getText());
            if (age < 18) {
                erreurAge2.setText("age < 18");

                return; // Sortir de la fonction si l'âge est inférieur à 18
            }
        } catch (NumberFormatException e) {
            System.err.println(e.getMessage());
            return;
        }

        int selectedEventId = eventService.getEventIdByTitle(selectedEventTitle);

        Membre membre = new Membre(
                Nom_Textfield.getText(),
                Prenom_Textfield.getText(),
                age,
                selectedEventId);

        try {
            if (memberService.checkDuplicateMember(membre)) {
                erreurDoublon.setText("deja existe");
                return;
            }

            memberService.create(membre);
            resetErrors();

            BtnAjoutM.getScene().getWindow().hide();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void resetErrors() {
        erreurNom.setText("");
        erreurPrenom.setText("");
        erreurAge1.setText("");
        erreurAge2.setText("");
        erreurDoublon.setText("");
    }

    public void initialize(){
        resetErrors();
        try {

            List<evenement> evenements = eventService.readAll();
            List<String> titresEvenements = new ArrayList<>();

            for (evenement event : evenements) {
                titresEvenements.add(event.getTitre());
            }

            Events_combo.setItems(FXCollections.observableArrayList(titresEvenements));

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

}
