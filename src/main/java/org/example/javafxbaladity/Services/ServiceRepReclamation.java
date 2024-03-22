package org.example.javafxbaladity.Services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.javafxbaladity.interfaces.IService;
import org.example.javafxbaladity.interfaces.IServiceReclamation;
import org.example.javafxbaladity.models.Reclamation;
import org.example.javafxbaladity.utils.Database;

import java.sql.*;

public class ServiceRepReclamation implements IServiceReclamation<Reclamation> {

    private Connection connection;
    private Statement ste;

    public ServiceRepReclamation() {

        connection = Database.getConnection();
    }

    @Override
    public void ajouter(Reclamation reclamation) throws SQLException {
        ste = connection.createStatement();
        String req = "insert into reclamation (typeReclamation,descriptionReclamation,statutReclamation,dateReclamation) values (?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setString(1, reclamation.getTypeReclamation());
        preparedStatement.setString(2, reclamation.getDescriptionReclamation());
        preparedStatement.setString(3, reclamation.getStatutReclamation());
        preparedStatement.setString(4, reclamation.getDateReclamation());
        preparedStatement.executeUpdate();
    }

    @Override
    public void modifier(Reclamation reclamation) throws SQLException {
        ste = connection.createStatement();
        String sql = "update reclamation set typeReclamation = ?, descriptionReclamation = ?, statutReclamation = ?,dateReclamation = ? where idReclamation = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, reclamation.getTypeReclamation());
        preparedStatement.setString(2, reclamation.getDescriptionReclamation());
        preparedStatement.setString(3, reclamation.getStatutReclamation());
        preparedStatement.setString(4, reclamation.getDateReclamation());
        preparedStatement.setInt(5, reclamation.getIdReclamation());
        preparedStatement.executeUpdate();

    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM `reclamation` WHERE idReclamation=?";
        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    @Override
    public ObservableList<Reclamation> recuperer() throws SQLException {
        String sql = "select * from reclamation";
        Statement statement = connection.createStatement();

        ResultSet rs = statement.executeQuery(sql);
        ObservableList <Reclamation> list = FXCollections.observableArrayList ();
        while (rs.next()) {
            Reclamation r = new Reclamation();
            r.setIdReclamation(rs.getInt("idReclamation"));
            r.setTypeReclamation(rs.getString("typeReclamation"));
            r.setDescriptionReclamation(rs.getString("descriptionReclamation"));
            r.setStatutReclamation(rs.getString("statutReclamation"));
            r.setDateReclamation(rs.getString("dateReclamation"));
            list.add(r);

        }
        return list;
    }


}