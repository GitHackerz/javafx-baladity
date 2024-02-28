package org.example.javafxbaladity.services;

import org.example.javafxbaladity.models.ProjectTask;
import org.example.javafxbaladity.utils.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProjectTaskService {
    Connection connection = Database.getConnection();
    public void addTask(ProjectTask task) throws SQLException {
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
}
