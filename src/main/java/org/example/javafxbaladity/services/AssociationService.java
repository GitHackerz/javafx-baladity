package org.example.javafxbaladity.Services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.javafxbaladity.interfaces.IService;
import org.example.javafxbaladity.models.Association;
import org.example.javafxbaladity.utils.Database;

import java.sql.*;

public class AssociationService implements IService<Association> {
    private final Connection connection = Database.getConnection();

    @Override
    public void create(Association association) {
        String sql = "INSERT INTO association (nom, adresse, caisse, type, statut) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, association.getNom());
            preparedStatement.setString(2, association.getAdresse());
            preparedStatement.setFloat(3, association.getCaisse());
            preparedStatement.setString(4, association.getType());
            preparedStatement.setString(5, association.getStatus());
            preparedStatement.executeUpdate();
            System.out.println("Association added successfully");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public Association read(int id) {
        String sql = "SELECT * FROM association WHERE id = ?";
        Association association = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                association = new Association(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        resultSet.getString("adresse"),
                        resultSet.getString("type"),
                        resultSet.getFloat("caisse"),
                        resultSet.getString("statut")
                );
            }
            preparedStatement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return association;
    }

    @Override
    public void update(Association association) {
        String sql = "UPDATE association SET nom = ?, adresse = ?, caisse = ?, type = ?, statut = ? WHERE id = ?";
        try {
            System.out.println(association);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, association.getNom());
            preparedStatement.setString(2, association.getAdresse());
            preparedStatement.setFloat(3, association.getCaisse());
            preparedStatement.setString(4, association.getType());
            preparedStatement.setString(5, association.getStatus());
            preparedStatement.setInt(6, association.getId());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }


    @Override
    public void delete(int id) {
        String sql = "DELETE FROM association WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println(id);
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public ObservableList<Association> readAll() {
        String sql = "SELECT * FROM association";
        ObservableList<Association> associations = FXCollections.observableArrayList();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {

                Association association = new Association(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        resultSet.getString("adresse"),
                        resultSet.getString("type"),
                        resultSet.getFloat("caisse"),
                        resultSet.getString("statut")
                );
                associations.add(association);

            }
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return associations;
    }
}
