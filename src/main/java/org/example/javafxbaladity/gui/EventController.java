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
import org.example.javafxbaladity.models.evenement;
import org.example.javafxbaladity.services.EventService;
import org.example.javafxbaladity.utils.Database;

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



public class EventController implements Initializable{
    Connection con =null;
    PreparedStatement st = null;
    ResultSet rs = null;
    EventService ev = new EventService();

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


    private Connection connection=Database.getConnection();
    EventService eventService= new EventService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        genericD.setVisible(false);
        Statut_Textfield.getItems().addAll(true, false);
        try {
            showEvenements();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

        showEvenements();

    }



    @FXML
    void onEventDeleteBtnClick(ActionEvent event) throws SQLException{
        ev.delete(ide);
        refreshTABLEviewEvent();


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

        ev.update(e);
        refreshTABLEviewEvent();



    }

}
