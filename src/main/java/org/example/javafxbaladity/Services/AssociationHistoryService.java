package org.example.javafxbaladity.Services;

import org.example.javafxbaladity.interfaces.IService;
import org.example.javafxbaladity.models.AssociationHistory;
import org.example.javafxbaladity.utils.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AssociationHistoryService implements IService<AssociationHistory> {
    private final Connection connection = Database.getConnection();

    @Override
    public void create(AssociationHistory associationHistory) throws SQLException {
        String query = "INSERT INTO historique_modification (title, description, id_association) VALUES (?, ?, ?)";

        PreparedStatement preparedStatement = .prepareStatement(query);
        preparedStatement.setString(1, associationHistory.getTitle());
        preparedStatement.setString(2, associationHistory.getDescription());
        preparedStatement.setInt(3, associationHistory.getAssociation().getId());

        preparedStatement.executeUpdate();
    }

    @Override
    public AssociationHistory read(int id) throws SQLException {
        String sql = "SELECT * FROM historique_modification WHERE id_association = ?";
        AssociationHistory associationHistory = null;

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            associationHistory = new AssociationHistory(
                    resultSet.getInt("id"),
                    resultSet.getString("title"),
                    resultSet.getString("description"),
                    new AssociationService().read(id)
            );
        }

        return associationHistory;
    }


    @Override
    public void update(AssociationHistory associationHistory) throws SQLException {
        String sql = "UPDATE historique_modification SET title = ?, description = ? WHERE id_association = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, associationHistory.getTitle());
        preparedStatement.setString(2, associationHistory.getDescription());
        preparedStatement.setInt(3, associationHistory.getAssociation().getId());

        preparedStatement.executeUpdate();
    }


    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM historique_modification WHERE id_association = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);

        preparedStatement.executeUpdate();
    }


    @Override
    public List<AssociationHistory> readAll() throws SQLException {
        String sql = "SELECT * FROM historique_modification";
        List<AssociationHistory> associationHistories = new ArrayList<>();

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            associationHistories.add(new AssociationHistory(
                    resultSet.getInt("id"),
                    resultSet.getString("title"),
                    resultSet.getString("description"),
                    new AssociationService().read(resultSet.getInt("id_association"))
            ));
        }
        return associationHistories;
    }
}
