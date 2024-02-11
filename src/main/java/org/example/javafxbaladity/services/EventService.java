package org.example.javafxbaladity.services;

import models.evenement;
import org.example.javafxbaladity.interfaces.IService;
import org.example.javafxbaladity.utils.Database;
import java.sql.SQLException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class EventService implements IService<evenement> {
    private Connection connection=Database.getConnection();

    @Override
    public void create(evenement evenement) throws SQLException {
        String req = "INSERT INTO documents (type_doc, statut_doc, date_emission_doc, date_expiration_doc, estarchive) " +
                "VALUES (?, ?, ?, ?, ?,?,?)";

        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setString(1, evenement.getTitre());
        preparedStatement.setString(2, evenement.getDescription());
        preparedStatement.setString(3, evenement.getDate());
        preparedStatement.setString(4, evenement.getLieu());
        preparedStatement.setString(5,evenement.getNomContact());
        preparedStatement.setString(6,evenement.getEmailContact());
        preparedStatement.setBoolean(7,evenement.isStatut());
        preparedStatement.executeUpdate();
    }

    @Override
    public evenement read(int id) throws SQLException {
        return null;
    }

    @Override
    public void update(evenement evenement) throws SQLException {
        String sql = "update evenement set titre = ?, description = ?, date = ? description = ?, date = ?, lieu = ?, nomContact = ?,emailContact = ?, statut = ? where id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, evenement.getTitre());
        preparedStatement.setString(2, evenement.getDescription());
        preparedStatement.setString(3, evenement.getDate());
        preparedStatement.setString(4, evenement.getLieu());
        preparedStatement.setString(5,evenement.getNomContact());
        preparedStatement.setString(6,evenement.getEmailContact());
        preparedStatement.setBoolean(7,evenement.isStatut());
        preparedStatement.setInt(8, evenement.getId());
        preparedStatement.executeUpdate();

    }

    @Override
    public void delete(int id) throws SQLException {
        String req = "DELETE FROM `evenement` WHERE id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setInt(1,id);
        preparedStatement.executeUpdate();
    }

    @Override
    public List<evenement> readAll() throws SQLException {
        String sql = "select * from evenement";
        Statement statement = connection.createStatement();

        ResultSet rs = statement.executeQuery(sql);
        List<evenement> list = new ArrayList<>();
        while (rs.next()){
            evenement p = new evenement();
            p.setId(rs.getInt("id"));
            p.setTitre(rs.getString("titre"));
            p.setDescription(rs.getString("description"));
            p.setDate(rs.getString("date"));
            p.setLieu(rs.getString("lieu"));
            p.setNomContact(rs.getString("nomContact"));
            p.setStatut(rs.getBoolean("statut"));
            list.add(p);

        }
        return list;
    }
}
