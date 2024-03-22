package org.example.javafxbaladity.Services;

import org.example.javafxbaladity.models.ProjectTask;
import org.example.javafxbaladity.utils.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjectTaskService {
    Connection connection = Database.getConnection();

    public void create(ProjectTask task) throws SQLException {
        System.out.println(task.getUser_id());
        String query = "INSERT INTO tache_projet (titre, description, statut, date, user_id, projet_id) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, task.getTitre());
        preparedStatement.setString(2, task.getDescription());
        preparedStatement.setString(3, task.getStatut().toString());
        preparedStatement.setDate(4, task.getDate());
        preparedStatement.setInt(5, task.getUser_id());
        preparedStatement.setInt(6, task.getProject_id());
        preparedStatement.executeUpdate();
    }

    public void update(ProjectTask task) throws SQLException {
        String query = "UPDATE tache_projet SET titre = ?, description = ?, statut = ?, date = ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, task.getTitre());
        preparedStatement.setString(2, task.getDescription());
        preparedStatement.setString(3, task.getStatut().toString());
        preparedStatement.setDate(4, task.getDate());
        preparedStatement.setInt(5, task.getId());
        preparedStatement.executeUpdate();
    }

    public List<ProjectTask> readAll() throws SQLException {
        String query = "SELECT * FROM tache_projet";
        List<ProjectTask> projectTasks = new ArrayList<>();

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            projectTasks.add(new ProjectTask(
                    resultSet.getInt("id"),
                    resultSet.getString("titre"),
                    resultSet.getString("description"),
                    resultSet.getDate("date"),
                    ProjectTask.Statut.valueOf(resultSet.getString("statut")),
                    resultSet.getInt("user_id"),
                    resultSet.getInt("projet_id")
            ));
        }

        return projectTasks;
    }

    public void delete(ProjectTask task) throws SQLException {
        String query = "DELETE FROM tache_projet WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, task.getId());
        preparedStatement.executeUpdate();
    }

    public void changeTaskStatus(ProjectTask task) throws SQLException {
        String query = "UPDATE tache_projet SET statut = ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, task.getStatut().toString());
        preparedStatement.setInt(2, task.getId());
        preparedStatement.executeUpdate();
    }

    public void assignTaskToUser(ProjectTask task, int userId) throws SQLException {
        String query = "UPDATE tache_projet SET user_id = ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, userId);
        preparedStatement.setInt(2, task.getId());
        preparedStatement.executeUpdate();
    }

    public void removeTaskFromUser(ProjectTask task) throws SQLException {
        String query = "UPDATE tache_projet SET user_id = NULL WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, task.getId());
        preparedStatement.executeUpdate();
    }


    public List<ProjectTask> getTasksByProject(int project_id) throws SQLException {
        List<ProjectTask> tasks = new ArrayList<>();

        String query = "SELECT * FROM tache_projet WHERE projet_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, project_id);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            tasks.add(new ProjectTask(
                    resultSet.getInt("id"),
                    resultSet.getString("titre"),
                    resultSet.getString("description"),
                    resultSet.getDate("date"),
                    ProjectTask.Statut.valueOf(resultSet.getString("statut")),
                    resultSet.getInt("user_id"),
                    resultSet.getInt("projet_id")
            ));
        }

        return tasks;
    }

    public List<ProjectTask> getTasksByUser(int user_id) throws SQLException {
        List<ProjectTask> tasks = new ArrayList<>();

        String query = "SELECT * FROM tache_projet WHERE user_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, user_id);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            tasks.add(new ProjectTask(
                    resultSet.getInt("id"),
                    resultSet.getString("titre"),
                    resultSet.getString("description"),
                    resultSet.getDate("date"),
                    ProjectTask.Statut.valueOf(resultSet.getString("statut")),
                    resultSet.getInt("user_id"),
                    resultSet.getInt("projet_id")
            ));
        }

        return tasks;
    }
}
