package org.example.javafxbaladity.services;

import org.example.javafxbaladity.interfaces.IService;
import org.example.javafxbaladity.models.Project;
import org.example.javafxbaladity.utils.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

public class Porject implements IService<Project> {
    Connection connection = Database.getConnection();

    @Override
    public void create(Project project) {
        String query = "INSERT INTO projects (title, description, status, budget, start_date, end_date) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, project.getTitle());
            preparedStatement.setString(2, project.getDescription());
            preparedStatement.setString(3, project.getStatus());
            preparedStatement.setFloat(4, project.getBudget());
            preparedStatement.setDate(5, (java.sql.Date) project.getStartDate());
            preparedStatement.setDate(6, (java.sql.Date) project.getEndDate());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Project read(int id) {
        String query = "SELECT * FROM projects WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Project(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getString("status"),
                        resultSet.getFloat("budget"),
                        resultSet.getDate("start_date"),
                        resultSet.getDate("end_date")
                );
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    @Override
    public void update(Project project) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<Project> readAll() {
        return null;
    }
}
