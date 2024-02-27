package org.example.javafxbaladity.services;

import org.example.javafxbaladity.interfaces.IService;
import org.example.javafxbaladity.models.Membre;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.javafxbaladity.models.evenement;
import org.example.javafxbaladity.interfaces.IService;
import org.example.javafxbaladity.utils.Database;
import java.sql.SQLException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MembreService implements IService<Membre> {

    private Connection connection=Database.getConnection();

    @Override
    public void create(Membre membre) throws SQLException {
        String req = "INSERT INTO membre (nom,prenom,age,event_id)" + "VALUES (?,?,?,?)";
        PreparedStatement preparedStatement= connection.prepareStatement(req);
        preparedStatement.setString(1, membre.getNom());
        preparedStatement.setString(2,membre.getPrenom());
        preparedStatement.setInt(3,membre.getAge());
        preparedStatement.setInt(4,membre.getEvent_id());
        preparedStatement.executeUpdate();
    }

    public void delete(int id) throws SQLException {
        String req = "DELETE FROM `membre` WHERE id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setInt(1,id);
        preparedStatement.executeUpdate();
    }

    public ObservableList<Membre> readAll() throws SQLException {
        String sql = "select * from membre";
        Statement statement = connection.createStatement();

        ResultSet rs = statement.executeQuery(sql);
        ObservableList<Membre> list = FXCollections.observableArrayList();
        while (rs.next()){
            Membre p = new Membre();
            p.setId(rs.getInt("id"));
            p.setNom(rs.getString("nom"));
            p.setPrenom(rs.getString("prenom"));
            p.setAge(rs.getInt("age"));
            p.setEvent_id(rs.getInt("event_id"));

            list.add(p);

        }
        return list;
    }

    public Membre read(int id) throws SQLException {
        return null;
    }

    @Override
    public void update(Membre membre) throws SQLException {
        String sql = "update membre set nom = ?, prenom = ?, age = ?, event_id = ? where id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, membre.getNom());
        preparedStatement.setString(2, membre.getPrenom());
        preparedStatement.setInt(3, membre.getAge());
        preparedStatement.setInt(4, membre.getEvent_id());
        preparedStatement.setInt(5, membre.getId());
        preparedStatement.executeUpdate();

    }

    public List<Membre> getMembresByEventId(int eventId) throws SQLException {
        String req = "SELECT * FROM membre WHERE event_id=?";
        List<Membre> membres = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(req)) {
            preparedStatement.setInt(1, eventId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    // Créez un objet Membre à partir des données de la ligne actuelle
                    Membre membre = new Membre(
                            resultSet.getInt("id"),
                            resultSet.getString("nom"),
                            resultSet.getString("prenom"),
                            resultSet.getInt("age"),
                            resultSet.getInt("event_id")
                    );
                    // Ajoutez le membre à la liste des membres
                    membres.add(membre);
                }
            }
        }
        return membres;
    }

    public boolean checkDuplicateMember(Membre membre) throws SQLException {
        String req = "SELECT COUNT(*) FROM membre WHERE nom = ? AND prenom = ? AND age = ? AND event_id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setString(1, membre.getNom());
        preparedStatement.setString(2, membre.getPrenom());
        preparedStatement.setInt(3, membre.getAge());
        preparedStatement.setInt(4, membre.getEvent_id());

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            int count = resultSet.getInt(1);
            return count > 0; // Si le compte est supérieur à zéro, cela signifie qu'un enregistrement similaire existe déjà
        }
        return false;
    }


}
