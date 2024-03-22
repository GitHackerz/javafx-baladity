package org.example.javafxbaladity.Services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.javafxbaladity.interfaces.IService;
import org.example.javafxbaladity.models.Project;
import org.example.javafxbaladity.utils.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjectService implements IService<Project> {
    Connection connection = Database.getConnection();

    @Override
    public void create(Project project) throws SQLException {
        String query = "INSERT INTO projet (titre, description, statut, budget, date_debut, date_fin) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, project.getTitre());
        preparedStatement.setString(2, project.getDescription());
        preparedStatement.setString(3, project.getStatut());
        preparedStatement.setFloat(4, project.getBudget());
        preparedStatement.setDate(5, project.getDate_debut());
        preparedStatement.setDate(6, project.getDate_fin());
        preparedStatement.executeUpdate();
    }

    @Override
    public Project read(int id) throws SQLException {
        String query = "SELECT * FROM projet WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return new Project(
                    resultSet.getInt("id"),
                    resultSet.getString("titre"),
                    resultSet.getString("description"),
                    resultSet.getString("statut"),
                    resultSet.getFloat("budget"),
                    resultSet.getDate("date_debut"),
                    resultSet.getDate("date_fin")
            );
        }
        return null;
    }

    @Override
    public void update(Project project) throws SQLException {
        String query = "UPDATE projet SET titre = ?, description = ?, statut = ?, budget = ?, date_debut = ?, date_fin = ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, project.getTitre());
        preparedStatement.setString(2, project.getDescription());
        preparedStatement.setString(3, project.getStatut());
        preparedStatement.setFloat(4, project.getBudget());
        preparedStatement.setDate(5, project.getDate_debut());
        preparedStatement.setDate(6, project.getDate_fin());
        preparedStatement.setInt(7, project.getId());
        preparedStatement.executeUpdate();
    }

    @Override
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM projet WHERE id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();

    }

    @Override
    public ObservableList<Project> readAll() throws SQLException {
        String query = "SELECT * FROM projet";
        ObservableList<Project> projects = FXCollections.observableArrayList();

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            projects.add(new Project(
                    resultSet.getInt("id"),
                    resultSet.getString("titre"),
                    resultSet.getString("description"),
                    resultSet.getString("statut"),
                    resultSet.getFloat("budget"),
                    resultSet.getDate("date_debut"),
                    resultSet.getDate("date_fin")
            ));
        }

        return projects;
    }

    public List<Project> readActiveProjects() throws SQLException {
        String query = "SELECT * FROM projet WHERE statut = 'active'";
        List<Project> projects = new ArrayList<>();

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            projects.add(new Project(
                    resultSet.getInt("id"),
                    resultSet.getString("titre"),
                    resultSet.getString("description"),
                    resultSet.getString("statut"),
                    resultSet.getFloat("budget"),
                    resultSet.getDate("date_debut"),
                    resultSet.getDate("date_fin")
            ));
        }

        return projects;
    }

    public List<Project> readInactiveProjects() throws SQLException {
        String query = "SELECT * FROM projet WHERE statut = 'inactive'";
        List<Project> projects = new ArrayList<>();

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            projects.add(new Project(
                    resultSet.getInt("id"),
                    resultSet.getString("titre"),
                    resultSet.getString("description"),
                    resultSet.getString("statut"),
                    resultSet.getFloat("budget"),
                    resultSet.getDate("date_debut"),
                    resultSet.getDate("date_fin")
            ));
        }

        return projects;
    }

    public void updateStatut(int id, String statut) throws SQLException {
        String query = "UPDATE projet SET statut = ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, statut);
        preparedStatement.setInt(2, id);
        preparedStatement.executeUpdate();
    }
}
