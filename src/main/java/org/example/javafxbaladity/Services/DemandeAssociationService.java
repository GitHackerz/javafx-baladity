package org.example.javafxbaladity.Services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.javafxbaladity.interfaces.IService;
import org.example.javafxbaladity.models.DemandeAssociation;
import org.example.javafxbaladity.utils.Database;

import java.sql.*;

public class DemandeAssociationService implements IService<DemandeAssociation> {
    private final Connection connection = Database.getConnection();

    @Override
    public void create(DemandeAssociation demandeAssociation) throws SQLException {
        String sql = "INSERT INTO demande_association (nom, adresse, caisse, type, id_user) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, demandeAssociation.getNom());
        preparedStatement.setString(2, demandeAssociation.getAdresse());
        preparedStatement.setFloat(3, demandeAssociation.getCaisse());
        preparedStatement.setString(4, demandeAssociation.getType());
        preparedStatement.setInt(5, demandeAssociation.getId_user());
        preparedStatement.executeUpdate();

    }

    @Override
    public DemandeAssociation read(int id) throws SQLException {
        String sql = "SELECT * FROM demande_association WHERE id = ?";
        DemandeAssociation DemandeAssociation = null;
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            DemandeAssociation = new DemandeAssociation(
                    resultSet.getInt("id"),
                    resultSet.getString("nom"),
                    resultSet.getString("adresse"),
                    resultSet.getString("type"),
                    resultSet.getFloat("caisse")
            );
        }
        preparedStatement.close();

        return DemandeAssociation;
    }

    @Override
    public void update(DemandeAssociation demandeAssociation) throws SQLException {
        String sql = "UPDATE demande_association SET nom = ?, adresse = ?, caisse = ?, type = ? ,id_user = ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, demandeAssociation.getNom());
        preparedStatement.setString(2, demandeAssociation.getAdresse());
        preparedStatement.setFloat(3, demandeAssociation.getCaisse());
        preparedStatement.setString(4, demandeAssociation.getType());
        preparedStatement.setInt(5, 1);
        preparedStatement.setInt(6, demandeAssociation.getId());

        preparedStatement.executeUpdate();
        preparedStatement.close();
    }


    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM demande_association WHERE id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }


    @Override
    public ObservableList<DemandeAssociation> readAll() throws SQLException {
        String sql = "SELECT * FROM demande_association";
        ObservableList<DemandeAssociation> list = FXCollections.observableArrayList();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            DemandeAssociation DemandeAssociation = new DemandeAssociation(
                    resultSet.getInt("id"),
                    resultSet.getString("nom"),
                    resultSet.getString("adresse"),
                    resultSet.getString("type"),
                    resultSet.getFloat("caisse")
            );
            list.add(DemandeAssociation);
        }
        statement.close();
        return list;
    }


}
