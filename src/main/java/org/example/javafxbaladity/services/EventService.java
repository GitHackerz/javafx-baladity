package org.example.javafxbaladity.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.javafxbaladity.models.Membre;
import org.example.javafxbaladity.models.evenement;
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
        String req = "INSERT INTO evenement (titre, description, date, lieu, nomContact,emailContact,statut) " +
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
        String sql = "update evenement set titre = ?, description = ?, date = ?, lieu = ?, nomContact = ?,emailContact = ?, statut = ? where id = ?";

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
   /* public void delete(int id) throws SQLException {
        String req = "DELETE FROM `evenement` WHERE id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setInt(1,id);
        preparedStatement.executeUpdate();
    }*/
    public void delete(int eventId) throws SQLException {
        // Récupérer tous les membres associés à l'événement
        MembreService membreService = new MembreService();
        List<Membre> membres = membreService.getMembresByEventId(eventId);

        //  Supprimer tous les membres associés à l'événement
        for (Membre membre : membres) {
            membreService.delete(membre.getId());
        }

        //  Supprimer l'événement
        String req = "DELETE FROM evenement WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(req)) {
            preparedStatement.setInt(1, eventId);
            preparedStatement.executeUpdate();
        }
    }


    @Override
    public ObservableList<evenement> readAll() throws SQLException {
        String sql = "select * from evenement";
        Statement statement = connection.createStatement();

        ResultSet rs = statement.executeQuery(sql);
        ObservableList<evenement> list = FXCollections.observableArrayList();
        while (rs.next()){
            evenement p = new evenement();
            p.setId(rs.getInt("id"));
            p.setTitre(rs.getString("titre"));
            p.setDescription(rs.getString("description"));
            p.setDate(rs.getString("date"));
            p.setLieu(rs.getString("lieu"));
            p.setNomContact(rs.getString("nomContact"));
            p.setEmailContact(rs.getString("emailContact"));
            p.setStatut(rs.getBoolean("statut"));
            list.add(p);

        }
        return list;
    }

    public ObservableList<evenement> searchEvents(String query) throws SQLException {
        String sql = "SELECT * FROM evenement WHERE titre LIKE ? OR description LIKE ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, "%" + query + "%");
        preparedStatement.setString(2, "%" + query + "%");

        ResultSet rs = preparedStatement.executeQuery();
        ObservableList<evenement> list = FXCollections.observableArrayList();
        while (rs.next()) {
            evenement p = new evenement();
            p.setId(rs.getInt("id"));
            p.setTitre(rs.getString("titre"));
            p.setDescription(rs.getString("description"));
            p.setDate(rs.getString("date"));
            p.setLieu(rs.getString("lieu"));
            p.setNomContact(rs.getString("nomContact"));
            p.setEmailContact(rs.getString("emailContact"));
            p.setStatut(rs.getBoolean("statut"));
            list.add(p);
        }
        return list;
    }

    public int getEventIdByTitle(String title) throws SQLException {
        String sql = "SELECT id FROM evenement WHERE titre = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, title);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("id");
        } else {

            return -1; // retourner -1 pour indiquer que l'événement n'a pas été trouvé
        }
    }

    public boolean checkDuplicateEvent(evenement event) throws SQLException {
        String req = "SELECT COUNT(*) FROM evenement WHERE titre = ? AND description = ? AND date = ? AND lieu = ? AND nomContact = ? AND emailContact = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setString(1, event.getTitre());
        preparedStatement.setString(2, event.getDescription());
        preparedStatement.setString(3, event.getDate());
        preparedStatement.setString(4, event.getLieu());
        preparedStatement.setString(5, event.getNomContact());
        preparedStatement.setString(6, event.getEmailContact());

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            int count = resultSet.getInt(1);
            return count > 0; // Si le compte est supérieur à zéro, cela signifie qu'un enregistrement similaire existe déjà
        }
        return false;
    }





    public List<evenement> getEventsByDate(String date) throws SQLException {
        String sql = "SELECT * FROM evenement WHERE date = ?";
        List<evenement> events = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, date);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    evenement event = new evenement();
                    event.setId(resultSet.getInt("id"));
                    event.setTitre(resultSet.getString("titre"));
                    event.setDate(resultSet.getString("date"));
                    event.setLieu(resultSet.getString("lieu"));

                    events.add(event);
                }
            }
        }
        return events;
    }



}
