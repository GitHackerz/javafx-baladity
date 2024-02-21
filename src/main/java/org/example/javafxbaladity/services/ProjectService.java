package org.example.javafxbaladity.services;

import org.example.javafxbaladity.interfaces.IService;
import org.example.javafxbaladity.models.Project;
import org.example.javafxbaladity.utils.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ProjectService implements IService<Project> {
    Connection connection = Database.getConnection();

    @Override
    public void create(Project project) {
        String query = "INSERT INTO projet (titre, description, statut, budget, date_debut, date_fin) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, project.getTitre());
            preparedStatement.setString(2, project.getDescription());
            preparedStatement.setString(3, project.getStatut());
            preparedStatement.setFloat(4, project.getBudget());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(project.getDate_debut()));
            preparedStatement.setTimestamp(6, Timestamp.valueOf(project.getDate_fin()));
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Project read(int id) {
        String query = "SELECT * FROM projet WHERE id = ?";
        try {
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
                        resultSet.getTimestamp("date_debut").toLocalDateTime(),
                        resultSet.getTimestamp("date_fin").toLocalDateTime()
                );
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    @Override
    public void update(Project project) {
        String query = "UPDATE projet SET titre = ?, description = ?, statut = ?, budget = ?, date_debut = ?, date_fin = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, project.getTitre());
            preparedStatement.setString(2, project.getDescription());
            preparedStatement.setString(3, project.getStatut());
            preparedStatement.setFloat(4, project.getBudget());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(project.getDate_debut()));
            preparedStatement.setTimestamp(6, Timestamp.valueOf(project.getDate_fin()));
            preparedStatement.setInt(7, project.getId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM projet WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Project> readAll() {
        String query = "SELECT * FROM projet";
        List<Project> projects = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                projects.add(new Project(
                        resultSet.getInt("id"),
                        resultSet.getString("titre"),
                        resultSet.getString("description"),
                        resultSet.getString("statut"),
                        resultSet.getFloat("budget"),
                        resultSet.getTimestamp("date_debut").toLocalDateTime(),
                        resultSet.getTimestamp("date_fin").toLocalDateTime()
                ));
            }

            return projects;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Project> readActiveProjects() {
        String query = "SELECT * FROM projet WHERE statut = 'active'";
        List<Project> projects = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                projects.add(new Project(
                        resultSet.getInt("id"),
                        resultSet.getString("titre"),
                        resultSet.getString("description"),
                        resultSet.getString("statut"),
                        resultSet.getFloat("budget"),
                        resultSet.getTimestamp("date_debut").toLocalDateTime(),
                        resultSet.getTimestamp("date_fin").toLocalDateTime()
                ));
            }

            return projects;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Project> readInactiveProjects() {
        String query = "SELECT * FROM projet WHERE statut = 'inactive'";
        List<Project> projects = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                projects.add(new Project(
                        resultSet.getInt("id"),
                        resultSet.getString("titre"),
                        resultSet.getString("description"),
                        resultSet.getString("statut"),
                        resultSet.getFloat("budget"),
                        resultSet.getTimestamp("date_debut").toLocalDateTime(),
                        resultSet.getTimestamp("date_fin").toLocalDateTime()
                ));
            }

            return projects;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
