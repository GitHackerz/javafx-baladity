package org.example.javafxbaladity.gui;

import io.github.palexdev.materialfx.controls.MFXPaginatedTableView;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import org.example.javafxbaladity.Services.AssociationHistoryService;
import org.example.javafxbaladity.models.AssociationHistory;

import java.sql.SQLException;
import java.util.Comparator;

public class AssociationHistoryController {

    private final AssociationHistoryService associationHistoryService = new AssociationHistoryService();
    @FXML
    private Button Association_btn;

    @FXML
    private Button Documents_Btn;

    @FXML
    private Button Home_Btn;

    @FXML
    private Button Project_Btn;

    @FXML
    private Button Users_Btn;

    @FXML
    private MFXPaginatedTableView<AssociationHistory> table;

    @FXML
    void getData(MouseEvent event) {

    }

    @FXML
    void onDocumentsButtonClick(ActionEvent event) {

    }

    @FXML
    void onHomeButtonClick(ActionEvent event) {

    }

    @FXML
    void onUserButtonClick(ActionEvent event) {

    }
    private void showAssociation() throws SQLException {

        MFXTableColumn<AssociationHistory> id_col = new MFXTableColumn<>("ID", false, Comparator.comparing(AssociationHistory::getId));
        MFXTableColumn<AssociationHistory> title_col = new MFXTableColumn<>("Title", false, Comparator.comparing(AssociationHistory::getTitle));
        MFXTableColumn<AssociationHistory> description_col = new MFXTableColumn<>("Description ", true, Comparator.comparing(AssociationHistory::getDescription));
//        MFXTableColumn<AssociationHistory> association_col = new MFXTableColumn<>("Association ", false, Comparator.comparing(AssociationHistory::getAssociation));

        id_col.setRowCellFactory(device -> new MFXTableRowCell<>(AssociationHistory::getId));
        title_col.setRowCellFactory(device -> new MFXTableRowCell<>(AssociationHistory::getTitle));
        description_col.setRowCellFactory(device -> new MFXTableRowCell<>(AssociationHistory::getDescription));
//        association_col.setRowCellFactory(device -> new MFXTableRowCell<>(AssociationHistory::getAssociation));

        description_col.setMinWidth(500);
        table.getFilters().addAll(
                new StringFilter<>("Title", AssociationHistory::getTitle),
                new StringFilter<>("statut", AssociationHistory::getDescription)
//                new StringFilter<>("adresse", AssociationHistory::getAssociation)
        );
        // Add columns to the table
        table.getTableColumns().addAll(title_col, description_col);

        table.setItems(FXCollections.observableArrayList(associationHistoryService.readAll()));

    }

    @FXML
    void initialize() {
        table.setItems(null);
        try {
            showAssociation();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
