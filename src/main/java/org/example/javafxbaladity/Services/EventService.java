package org.example.javafxbaladity.Services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.javafxbaladity.interfaces.IService;
import org.example.javafxbaladity.models.Membre;
import org.example.javafxbaladity.models.evenement;
import org.example.javafxbaladity.utils.Database;
import org.example.javafxbaladity.utils.Mailer;

import javax.mail.MessagingException;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EventService implements IService<evenement> {
    private final Connection connection = Database.getConnection();

    @Override
    public void create(evenement evenement) throws SQLException {
        String req = "INSERT INTO evenement (titre, description, date, lieu, nom_contact,email_contact,statut) VALUES (?, ?, ?, ?, ?,?,?)";

        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setString(1, evenement.getTitre());
        preparedStatement.setString(2, evenement.getDescription());
        preparedStatement.setString(3, evenement.getDate());
        preparedStatement.setString(4, evenement.getLieu());
        preparedStatement.setString(5, evenement.getNomContact());
        preparedStatement.setString(6, evenement.getEmailContact());
        preparedStatement.setBoolean(7, evenement.isStatut());
        preparedStatement.executeUpdate();
    }

    @Override
    public evenement read(int id) {
        return null;
    }

    @Override
    public void update(evenement evenement) throws SQLException {
        String sql = "update evenement set titre = ?, description = ?, date = ?, lieu = ?, nom_contact = ?,email_contact = ?, statut = ? where id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, evenement.getTitre());
        preparedStatement.setString(2, evenement.getDescription());
        preparedStatement.setString(3, evenement.getDate());
        preparedStatement.setString(4, evenement.getLieu());
        preparedStatement.setString(5, evenement.getNomContact());
        preparedStatement.setString(6, evenement.getEmailContact());
        preparedStatement.setBoolean(7, evenement.isStatut());
        preparedStatement.setInt(8, evenement.getId());
        preparedStatement.executeUpdate();

    }

    @Override
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
        while (rs.next()) {
            evenement p = new evenement();
            p.setId(rs.getInt("id"));
            p.setTitre(rs.getString("titre"));
            p.setDescription(rs.getString("description"));
            p.setDate(rs.getString("date"));
            p.setLieu(rs.getString("lieu"));
            p.setNomContact(rs.getString("nom_contact"));
            p.setEmailContact(rs.getString("email_contact"));
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
            p.setNomContact(rs.getString("nom_contact"));
            p.setEmailContact(rs.getString("email_contact"));
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

            return -1;
        }
    }

    public boolean checkDuplicateEvent(evenement event) throws SQLException {
        String req = "SELECT COUNT(*) FROM evenement WHERE titre = ? AND description = ? AND date = ? AND lieu = ? AND nom_contact = ? AND email_contact = ?";

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
            return count > 0;
        }
        return false;
    }


    public List<evenement> getEventsByDate(String date) throws SQLException {
        System.out.println(date);
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

    public List<evenement> getTodayEvents() throws SQLException {
        List<evenement> events = new ArrayList<>();
        try {
            LocalDate today = LocalDate.now();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", new Locale("fr"));
            String formattedToday = today.format(formatter);

            events = getEventsByDate(formattedToday);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return events;
    }

    public String getEventNameById(int eventId) throws SQLException {
        String sql = "SELECT titre FROM evenement WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, eventId);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getString("titre");
        } else {
            return null;
        }
    }

    public List<evenement> getEventsByDate1(String date) throws SQLException {
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

    public void sendEmailForTodayEvents() throws MessagingException {
        try {
            System.out.println("sending email ...");
            LocalDate today = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", new Locale("fr"));
            String formattedToday = today.format(formatter);
            List<evenement> events = getEventsByDate1(formattedToday);
            System.out.println(events.size());

            if (!events.isEmpty()) {
                StringBuilder emailBody = new StringBuilder();
                emailBody.append("Liste des événements pour aujourd'hui :\n");
                for (evenement event : events) {
                    emailBody.append("Titre : ").append(event.getTitre()).append("\n");
                    emailBody.append("Date : ").append(event.getDate()).append("\n");
                    emailBody.append("Lieu : ").append(event.getLieu()).append("\n");
                    emailBody.append("\n");
                }

                try{
                    Mailer.sendEmail("habibbibani79@gmail.com", "Événements pour aujourd'hui", emailBody);
                }
                catch (Exception e){
                    System.err.println("Error while sending an email");
                    return;
                }
                System.out.println("done");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }


}




