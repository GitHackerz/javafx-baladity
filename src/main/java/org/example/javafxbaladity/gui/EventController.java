package org.example.javafxbaladity.gui;

import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import io.github.palexdev.materialfx.filter.BooleanFilter;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import org.example.javafxbaladity.interfaces.IService;
import org.example.javafxbaladity.models.Membre;
import org.example.javafxbaladity.models.evenement;
import org.example.javafxbaladity.services.EventService;
import org.example.javafxbaladity.utils.Database;
import org.example.javafxbaladity.services.MembreService;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.*;
import java.util.*;

import org.example.javafxbaladity.models.evenement;
import org.example.javafxbaladity.interfaces.IService;
import org.example.javafxbaladity.utils.Database;
import java.sql.SQLException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;



public class EventController implements Initializable{
    Connection con =null;
    PreparedStatement st = null;
    ResultSet rs = null;
    EventService ev = new EventService();
    MembreService ms = new MembreService();

    private int ide=0;



    @FXML
    private Button Documents_Btn;

    @FXML
    private Button Events_btn;

    @FXML
    private Button Home_Btn;
    @FXML
    private MFXButton ajouterEvents_Btn;

    @FXML
    private Button Users_Btn;

    @FXML
    private MFXButton addEvent_Btn;
    @FXML
    private Pane paneadd;
    @FXML
    private MFXDatePicker Date_Textfield;

    @FXML
    private TextField Description_TextField;
    @FXML
    private TextField Email_Textfield;
    @FXML
    private TextField Lieu_Textfield;

    @FXML
    private TextField NomContact_Textfield;

    @FXML
    private MFXComboBox<Boolean> Statut_Textfield;
    @FXML
    private MFXPaginatedTableView<evenement> tableviewE;
    @FXML
    private MFXGenericDialog genericD;
    @FXML
    private MFXButton SaveBtn;

    @FXML
    private MFXTextField recherche_Textfield;






    @FXML
    private TextField Titre_Textfield;

    @FXML
    private TableView<evenement> tableview;

    @FXML
    private TableColumn<evenement, String> Coldate;

    @FXML
    private TableColumn<evenement, String> Coldescription;

    @FXML
    private TableColumn<evenement, String> Colemailcontact;

    @FXML
    private TableColumn<evenement, Integer> Colid;

    @FXML
    private TableColumn<evenement, String> Collieu;

    @FXML
    private TableColumn<evenement, String> Colnomcontact;

    @FXML
    private TableColumn<evenement, Boolean> Colstatut;

    @FXML
    private TableColumn<evenement, String> Coltitre;
    @FXML
    private MFXTextField Age_Textfield;

    @FXML
    private MFXButton BtnAjoutM;
    @FXML
    private MFXTextField Nom_Textfield;

    @FXML
    private MFXTextField Prenom_Textfield;


    @FXML
    private MFXButton Btn_AjouterMD;

    @FXML
    private MFXButton Btn_Close;

    @FXML
    private MFXButton Btn_Details;

    @FXML
    private MFXButton Btn_SuppM;
    @FXML
    private MFXGenericDialog genericTM;
    @FXML
    private MFXPaginatedTableView<Membre> tableviewM;
    @FXML
    private MFXGenericDialog genericAM;






    private Connection connection=Database.getConnection();
    EventService eventService= new EventService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        genericD.setVisible(false);
        genericTM.setVisible(false);
        genericAM.setVisible(false);
        Statut_Textfield.getItems().addAll(true, false);
        try {
            showEvenements();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            showMembres();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        recherche_Textfield.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                String query = newValue.trim(); // Obtenez le texte de recherche et supprimez les espaces blancs
                ObservableList<evenement> searchResults = eventService.searchEvents(query); // Recherchez les événements correspondants
                tableviewE.setItems(searchResults); // Mettez à jour la TableView avec les résultats de la recherche
            } catch (SQLException e) {
                e.printStackTrace(); // Gérez les exceptions de manière appropriée
            }
        });

    }
    @FXML
    void Oncloseadd(ActionEvent event) {
        genericD.setVisible(false);
    }





    public void showEvenements() throws SQLException {
        /*
        ObservableList<evenement> list = ev.readAll();
        tableview.setItems(list);
        Colid.setCellValueFactory(new PropertyValueFactory<evenement,Integer>("id"));
        Coltitre.setCellValueFactory(new PropertyValueFactory<evenement,String>("titre"));
        Coldescription.setCellValueFactory(new PropertyValueFactory<evenement,String>("description"));
       Coldate.setCellValueFactory(new PropertyValueFactory<evenement,String>("date"));
       Collieu.setCellValueFactory(new PropertyValueFactory<evenement,String>("lieu"));
       Colnomcontact.setCellValueFactory(new PropertyValueFactory<evenement,String>("nomContact"));
       Colemailcontact.setCellValueFactory(new PropertyValueFactory<evenement,String>("emailContact"));
       Colstatut.setCellValueFactory(new PropertyValueFactory<evenement, Boolean>("statut"));
       Colstatut.setCellFactory(CheckBoxTableCell.forTableColumn(Colstatut));

*/
        MFXTableColumn<evenement> id_eve_col = new MFXTableColumn<>("ID ", false, Comparator.comparing(evenement::getId));
        MFXTableColumn<evenement> titre_eve_col = new MFXTableColumn<>("Titre ", false, Comparator.comparing(evenement::getTitre));
        MFXTableColumn<evenement> description_eve_col = new MFXTableColumn<>("description ", false, Comparator.comparing(evenement::getDescription));
        MFXTableColumn<evenement> date_eve_col = new MFXTableColumn<>("date ", false, Comparator.comparing(evenement::getDate));
        MFXTableColumn<evenement> lieu_eve_col = new MFXTableColumn<>("lieu", false, Comparator.comparing(evenement::getLieu));
        MFXTableColumn<evenement> nomContact_eve_col = new MFXTableColumn<>("nomContact", false, Comparator.comparing(evenement::getNomContact));
        MFXTableColumn<evenement> emailContact_eve_col = new MFXTableColumn<>("emailContact", false, Comparator.comparing(evenement::getEmailContact));
        MFXTableColumn<evenement> statut_eve_col = new MFXTableColumn<>("statut", false, Comparator.comparing(evenement::isStatut));

        // Create CheckBox column



        id_eve_col.setRowCellFactory(device -> new MFXTableRowCell<>(evenement::getId));
        titre_eve_col.setRowCellFactory(device -> new MFXTableRowCell<>(evenement::getTitre));
        description_eve_col.setRowCellFactory(device -> new MFXTableRowCell<>(evenement::getDescription) {{
            setAlignment(Pos.BASELINE_CENTER);
        }});
        date_eve_col.setRowCellFactory(device -> new MFXTableRowCell<>(evenement::getDate));
        lieu_eve_col.setRowCellFactory(device -> new MFXTableRowCell<>(evenement::getLieu));
        id_eve_col.setAlignment(Pos.BASELINE_CENTER);
        nomContact_eve_col.setRowCellFactory(device -> new MFXTableRowCell<>(evenement::getNomContact));
        emailContact_eve_col.setRowCellFactory(device -> new MFXTableRowCell<>(evenement::getEmailContact));
        statut_eve_col.setRowCellFactory(device -> new MFXTableRowCell<>(evenement::isStatut));



        //checkbox_col.setRowCellFactory(device -> new MFXTableRowCell<>(Document::getCheckbox));


        // Add columns to the table
        tableviewE.getTableColumns().addAll( id_eve_col, titre_eve_col, description_eve_col, date_eve_col, lieu_eve_col, nomContact_eve_col,emailContact_eve_col,statut_eve_col);

        tableviewE.getFilters().addAll(
                new IntegerFilter<>("id", evenement::getId),
                new StringFilter<>("titre", evenement::getTitre),
                new StringFilter<>("description", evenement::getDescription),
                new StringFilter<>("date", evenement::getDate),
                new StringFilter<>("lieu", evenement::getLieu),
                new StringFilter<>("nomContact",evenement::getNomContact),
                new StringFilter<>("emailContact", evenement::getEmailContact),
                new BooleanFilter<>("statut", evenement::isStatut)

        );
        tableviewE.setItems(ev.readAll());

   }
    @FXML
    void OnEventsBtnClick(ActionEvent event) {

    }

    @FXML
    void onDocumentsButtonClick(ActionEvent event) {

    }

    @FXML
    void onEventBtnClick(ActionEvent event) {

    }

    @FXML
    void onHomeButtonClick(ActionEvent event) {

    }

    @FXML
    void onUserButtonClick(ActionEvent event) {

    }
    @FXML
    void onEventAddBtnClick(ActionEvent event) {

        genericD.setVisible(true);
    }
    @FXML
    void onAjouterClick(ActionEvent event) throws SQLException {
        String req = "INSERT INTO evenement (titre, description, date, lieu, nomContact, emailContact, statut) " +
                "VALUES (?, ?, ?, ?, ?,?,?)";

        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setString(1, Titre_Textfield.getText());
        preparedStatement.setString(2, Description_TextField.getText());
        preparedStatement.setString(3, Date_Textfield.getText());
        preparedStatement.setString(4, Lieu_Textfield.getText());
        preparedStatement.setString(5, NomContact_Textfield.getText());
        preparedStatement.setString(6, Email_Textfield.getText());
        preparedStatement.setBoolean(7, Statut_Textfield.getValue());
        preparedStatement.executeUpdate();
        genericD.setVisible(false);

        showEvenements();

    }



    @FXML
    void onEventDeleteBtnClick(ActionEvent event) throws SQLException{
        ev.delete(ide);
        refreshTABLEviewEvent();
        refreshTABLEviewMembre();


    }

    @FXML
   void onEventUpdateBtnClick(ActionEvent event) {
        evenement eve = new evenement(
                ide,
                Titre_Textfield.getText(),
                Description_TextField.getText(),
                Date_Textfield.getText(),
                Lieu_Textfield.getText(),
                NomContact_Textfield.getText(),
                Email_Textfield.getText(),
                Boolean.parseBoolean(Statut_Textfield.getText())

        );

        try {
            ev.update(eve);
            genericD.setVisible(false);
            refreshTABLEviewEvent();
        } catch (SQLException e) {
            e.printStackTrace(); // Gérer les exceptions de manière appropriée
        }
        genericD.setVisible(true);
    }

    private void refreshTABLEviewEvent() throws SQLException {

        tableviewE.setItems(ev.readAll());
    }
    private void refreshTABLEviewMembre() throws SQLException {

        tableviewM.setItems(ms.readAll());
    }

@FXML
    public void getData(javafx.scene.input.MouseEvent mouseEvent) {
        List<evenement> Levent = tableviewE.getSelectionModel().getSelectedValues();
        evenement even = Levent.get(0);
        ide = even.getId();
        Titre_Textfield.setText(even.getTitre());
        Description_TextField.setText(even.getDescription());
        Date_Textfield.setText(even.getDate());
        Lieu_Textfield.setText(even.getLieu());
        NomContact_Textfield.setText(even.getNomContact());
        Email_Textfield.setText(even.getEmailContact());
        Statut_Textfield.setText(Boolean.toString(even.isStatut()));
        //ajouterEvents_Btn.setDisable(true);

    }
    @FXML
    void OnsaveBtnClick(ActionEvent event) throws SQLException {
        evenement e = new evenement(ide,Titre_Textfield.getText(),
                Description_TextField.getText(),
                Date_Textfield.getText(),
                Lieu_Textfield.getText(),
                NomContact_Textfield.getText(),
                Email_Textfield.getText(),
                Boolean.parseBoolean(Statut_Textfield.getText()));

        genericD.setVisible(false);
        ev.update(e);
        refreshTABLEviewEvent();



    }

   @FXML
    void setOnAction(ActionEvent event) {
       recherche_Textfield.textProperty().addListener((observable, oldValue, newValue) -> {
           try {
               String query = newValue.trim(); // Obtenez le texte de recherche et supprimez les espaces blancs
               ObservableList<evenement> searchResults = ev.searchEvents(query); // Recherchez les événements correspondants
               tableviewE.setItems(searchResults); // Mettez à jour la TableView avec les résultats de la recherche

           } catch (SQLException e) {
               e.printStackTrace(); // Gérez les exceptions de manière appropriée
           }
       });
       }


    @FXML
    void AddMClick(ActionEvent event) {
        genericAM.setVisible(true);

    }
    @FXML
    void CloseClick(ActionEvent event) {
        genericTM.setVisible(false);

    }

    @FXML
    void DetailsClick(ActionEvent event) {
        genericTM.setVisible(true);

    }
    @FXML
    void OnsuppMClick(ActionEvent event) {

    }

    public void showMembres() throws SQLException {
        /*
        ObservableList<evenement> list = ev.readAll();
        tableview.setItems(list);
        Colid.setCellValueFactory(new PropertyValueFactory<evenement,Integer>("id"));
        Coltitre.setCellValueFactory(new PropertyValueFactory<evenement,String>("titre"));
        Coldescription.setCellValueFactory(new PropertyValueFactory<evenement,String>("description"));
       Coldate.setCellValueFactory(new PropertyValueFactory<evenement,String>("date"));
       Collieu.setCellValueFactory(new PropertyValueFactory<evenement,String>("lieu"));
       Colnomcontact.setCellValueFactory(new PropertyValueFactory<evenement,String>("nomContact"));
       Colemailcontact.setCellValueFactory(new PropertyValueFactory<evenement,String>("emailContact"));
       Colstatut.setCellValueFactory(new PropertyValueFactory<evenement, Boolean>("statut"));
       Colstatut.setCellFactory(CheckBoxTableCell.forTableColumn(Colstatut));

*/
        MFXTableColumn<Membre> id_mbr_col = new MFXTableColumn<>("ID ", false, Comparator.comparing(Membre::getId));
        MFXTableColumn<Membre> nom_mbr_col = new MFXTableColumn<>("nom ", false, Comparator.comparing(Membre::getNom));
        MFXTableColumn<Membre> prenom_eve_col = new MFXTableColumn<>("prenom ", false, Comparator.comparing(Membre::getPrenom));
        MFXTableColumn<Membre> age_mbr_col = new MFXTableColumn<>("age ", false, Comparator.comparing(Membre::getAge));
        MFXTableColumn<Membre> event_id_mbr_col = new MFXTableColumn<>("event_id", false, Comparator.comparing(Membre::getEvent_id));

        // Create CheckBox column



        id_mbr_col.setRowCellFactory(device -> new MFXTableRowCell<>(Membre::getId));
        nom_mbr_col.setRowCellFactory(device -> new MFXTableRowCell<>(Membre::getNom));
        prenom_eve_col.setRowCellFactory(device -> new MFXTableRowCell<>(Membre::getPrenom) {{
            setAlignment(Pos.BASELINE_CENTER);
        }});
        age_mbr_col.setRowCellFactory(device -> new MFXTableRowCell<>(Membre::getAge));
        event_id_mbr_col.setRowCellFactory(device -> new MFXTableRowCell<>(Membre::getEvent_id));
        id_mbr_col.setAlignment(Pos.BASELINE_CENTER);




        //checkbox_col.setRowCellFactory(device -> new MFXTableRowCell<>(Document::getCheckbox));


        // Add columns to the table
        tableviewM.getTableColumns().addAll( id_mbr_col, nom_mbr_col, prenom_eve_col, age_mbr_col, event_id_mbr_col);

        tableviewM.getFilters().addAll(
                new IntegerFilter<>("id", Membre::getId),
                new StringFilter<>("nom", Membre::getNom),
                new StringFilter<>("prenom", Membre::getPrenom),
                new IntegerFilter<>("age", Membre::getAge),
                new IntegerFilter<>("event_id", Membre::getAge)

        );
        tableviewM.setItems(ms.readAll());

    }








}
