package org.example.javafxbaladity.Services;

import javafx.collections.ObservableList;
import org.example.javafxbaladity.interfaces.IService;
import org.example.javafxbaladity.models.ProjectParticipant;
import org.example.javafxbaladity.utils.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProjectParticipantService implements IService<ProjectParticipant> {
    Connection connection = Database.getConnection();

    @Override
    public void create(ProjectParticipant projectParticipant) throws SQLException {
        String query = "INSERT INTO projet_user (projet_id, user_id) VALUES (?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, projectParticipant.getProject_id());
        preparedStatement.setInt(2, projectParticipant.getParticipant_id());
        preparedStatement.executeUpdate();
    }

    @Override
    public ProjectParticipant read(int id) throws SQLException {
        return null;
    }

    public ProjectParticipant readByProject(int project_id) throws SQLException {
        String query = "SELECT * FROM projet_user WHERE projet_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, project_id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return new ProjectParticipant(
                    resultSet.getInt("projet_id"),
                    resultSet.getInt("user_id")
            );
        }

        return null;
    }

    public ProjectParticipant readByParticipant(int participant_id) throws Exception {
        String query = "SELECT * FROM projet_user WHERE user_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, participant_id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return new ProjectParticipant(
                    resultSet.getInt("projet_id"),
                    resultSet.getInt("user_id")
            );
        }

        return null;
    }

    public ProjectParticipant readByParticipantAndProject(int participant_id, int project_id) throws Exception {
        String query = "SELECT * FROM projet_user WHERE user_id = ? AND projet_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, participant_id);
        preparedStatement.setInt(2, project_id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return new ProjectParticipant(
                    resultSet.getInt("projet_id"),
                    resultSet.getInt("user_id")
            );
        }

        return null;
    }

    @Override
    public void update(ProjectParticipant projectParticipant) throws SQLException {
        String query = "UPDATE projet_user SET projet_id = ?, user_id = ? WHERE projet_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, projectParticipant.getProject_id());
        preparedStatement.setInt(2, projectParticipant.getParticipant_id());
        preparedStatement.setInt(3, projectParticipant.getProject_id());

    }

    @Override
    public void delete(int id) throws SQLException {

    }

    @Override
    public ObservableList<ProjectParticipant> readAll() throws SQLException {
        return null;
    }
}
