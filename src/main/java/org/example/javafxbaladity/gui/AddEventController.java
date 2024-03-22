package org.example.javafxbaladity.gui;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.javafxbaladity.Services.EventService;
import org.example.javafxbaladity.models.evenement;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class AddEventController {
    evenement e = EventController.selectedEvent;
    EventService ev = new EventService();
    private final int ide = e.getId();

    @FXML
    private MFXDatePicker Date_Textfield;

    @FXML
    private TextField Email_Textfield, Titre_Textfield, Lieu_Textfield, Description_TextField, NomContact_Textfield;

    @FXML
    private MFXButton SaveBtn, ajouterEvents_Btn;

    @FXML
    private MFXComboBox<Boolean> Statut_Textfield;

    @FXML
    private Label erreurDate, erreurDesc, erreurEmail1, erreurEmail2, erreurGeneral, erreurLieu, erreurNomc, erreurTitre;

    @FXML
    private MFXGenericDialog genericD;

    @FXML
    public void onClose() {
        genericD.getScene().getWindow().hide();
    }

    @FXML
    public void initialize() {
        Statut_Textfield.getItems().addAll(true, false);
        resetErrors();
        Titre_Textfield.setText(e.getTitre());
        Description_TextField.setText(e.getDescription());
        Date_Textfield.setText(e.getDate());
        Lieu_Textfield.setText(e.getLieu());
        NomContact_Textfield.setText(e.getNomContact());
        Email_Textfield.setText(e.getEmailContact());
        Statut_Textfield.setText(Boolean.toString(e.isStatut()));
    }

    @FXML
    void onSaveBtnClick() throws SQLException {
        evenement e = new evenement(ide, Titre_Textfield.getText(),
                Description_TextField.getText(),
                Date_Textfield.getText(),
                Lieu_Textfield.getText(),
                NomContact_Textfield.getText(),
                Email_Textfield.getText(),
                Boolean.parseBoolean(Statut_Textfield.getText()));

        SaveBtn.getScene().getWindow().hide();

        ev.update(e);
    }

    @FXML
    void onAjouterClick() {
        try {
            String titre = Titre_Textfield.getText();
            String description = Description_TextField.getText();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", new Locale("fr"));
            LocalDate localDate = Date_Textfield.getValue();
            String date = localDate.format(formatter);

            String lieu = Lieu_Textfield.getText();
            String nomContact = NomContact_Textfield.getText();
            String emailContact = Email_Textfield.getText();
            Boolean statut = Statut_Textfield.getValue();

            if (titre.isEmpty()) {
                erreurTitre.setText("Error");
                return;
            }
            if (description.isEmpty()) {
                erreurDesc.setText("Error");
                return;
            }
            if (lieu.isEmpty()) {
                erreurLieu.setText("Error");
                return;
            }
            if (date.isEmpty()) {
                erreurDate.setText("Error");
                return;
            }
            if (nomContact.isEmpty()) {
                erreurNomc.setText("Error");
                return;
            }
            if (emailContact.isEmpty()) {
                erreurEmail1.setText("Error");
                return;
            }
            if (!emailContact.contains("@")) {
                erreurEmail2.setText("Error");
                return;
            }

            evenement event1 = new evenement(titre, description, date, lieu, nomContact, emailContact, statut);
            if (ev.checkDuplicateEvent(event1)) {
                erreurGeneral.setText("Error");
                return;
            }

            ev.create(event1);

           resetErrors();

            ajouterEvents_Btn.getScene().getWindow().hide();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void resetErrors() {
        erreurTitre.setText("");
        erreurDesc.setText("");
        erreurLieu.setText("");
        erreurDate.setText("");
        erreurNomc.setText("");
        erreurEmail1.setText("");
        erreurEmail2.setText("");
        erreurGeneral.setText("");

    }


}
