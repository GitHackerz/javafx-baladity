package org.example.javafxbaladity.Services;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.javafxbaladity.models.Citoyen;
import org.example.javafxbaladity.utils.Database;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class CitoyenService {
    private Connection conn;


    public CitoyenService() {
        conn = Database.getConnection();

    }

    public ObservableList<Citoyen> getCitoyenList() {
        ObservableList<Citoyen> x = FXCollections.observableArrayList();

        Connection conn = Database.getConnection();
        String query = "select * from citoyen";
        Statement s1;
        ResultSet r1;
        try {
            s1 = conn.createStatement();
            r1 = s1.executeQuery(query);
            Citoyen c;
            while (r1.next()) {
                c = new Citoyen(r1.getInt("id"), r1.getInt("cin"), r1.getString("nom"),
                        r1.getString("prenom"), r1.getNString("adresse"), r1.getDate("datenaissance"), r1.getString("genre"));
                x.add(c);


            }
        } catch (Exception ex) {
            ex.printStackTrace();

        }
        return x;

    }
    public int countCitoyens() throws SQLException {
        String query = "SELECT COUNT(*) AS count FROM citoyen";
        PreparedStatement statement = conn.prepareStatement(query) ;
        ResultSet resultSet = statement.executeQuery() ;
        if (resultSet.next()) {
            return resultSet.getInt("count");
        } else {
            return 0;
        }
    }

    public int countCitoyenFemale() throws SQLException {
        String query = "SELECT COUNT(*) AS count FROM citoyen WHERE genre = 'female'";
        PreparedStatement statement = conn.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("count");
        } else {
            return 0;
        }
    }
    public static Map<Integer, Map<String, Integer>> getGenderStatisticsOverTime() {
        Map<Integer, Map<String, Integer>> stats = new HashMap<>();
        String query = "SELECT YEAR(datenaissance) as year, genre, COUNT(*) as count FROM citoyen GROUP BY year, genre ORDER BY year";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int year = rs.getInt("year");
                String genre = rs.getString("genre").toLowerCase(); // Assuming 'genre' is either 'male' or 'female'
                int count = rs.getInt("count");

                Map<String, Integer> yearStats = stats.getOrDefault(year, new HashMap<>());
                yearStats.put(genre, count);
                stats.put(year, yearStats);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stats;
    }

    public int countCitoyenMale() throws SQLException {
        String query = "SELECT COUNT(*) AS count FROM citoyen WHERE genre = 'male'";
        PreparedStatement statement = conn.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("count");
        } else {
            return 0;
        }
    }

    public Citoyen getCitoyen(int id) throws SQLException {
        String query = "SELECT * FROM citoyen WHERE id=?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();

        Citoyen citoyen = null;

        if (resultSet.next()) {
            int citoyenId = resultSet.getInt("id");
            int cinCitoyen = resultSet.getInt("cin");
            String nomCitoyen = resultSet.getString("nom");
            String prenomCitoyen = resultSet.getString("prenom");
            String adresseCitoyen = resultSet.getString("adresse");
            Date dateNCitoyen = resultSet.getDate("datenaissance");
            String genre = resultSet.getString("genre");

            // Create a new Citoyen object
            citoyen = new Citoyen(citoyenId, cinCitoyen, nomCitoyen, prenomCitoyen, adresseCitoyen, dateNCitoyen, genre);
        }

        return citoyen;
    }

    public void ajouterCitoyen(Citoyen b) throws SQLException {
        String query = "INSERT INTO citoyen VALUES (?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = conn.prepareStatement(query);

        preparedStatement.setInt(1, b.getIdCitoyen());
        preparedStatement.setInt(2, b.getCinCitoyen());
        preparedStatement.setString(3, b.getNomCitoyen());
        preparedStatement.setString(4, b.getPrenomCitoyen());
        preparedStatement.setString(5, b.getAdresseCitoyen());
        preparedStatement.setDate(6, new Date(b.getDateNCitoyen().getTime()));
        preparedStatement.setString(7, b.getGenre());

        preparedStatement.executeUpdate();
    }

    public boolean cinExists(String cin) {
        String query = "SELECT * FROM citoyen WHERE cin = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, cin);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next(); // Return true if a row with the given CIN exists
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public void ajouterCitoyen2(Citoyen b) throws SQLException {


        String query = "INSERT INTO citoyen VALUES (?, ?, ?, ?, ?)";


        PreparedStatement preparedStatement = conn.prepareStatement(query);


        preparedStatement.setInt(2, b.getCinCitoyen());
        preparedStatement.setString(3, b.getNomCitoyen());
        preparedStatement.setString(4, b.getPrenomCitoyen());
        preparedStatement.setString(5, b.getAdresseCitoyen());
//        preparedStatement.setDate(6, new Date(b.getDateNCitoyen().getTime()));
//        preparedStatement.setString(7, b.getGenre());


        preparedStatement.executeUpdate();

    }

    public void supprimerCitoyen(int id) throws SQLException {


        String query = "DELETE FROM citoyen where(id =" + id + ")";
        Statement s = conn.createStatement();
        s.executeUpdate(query);


    }

    public void supprimerCitoyen2(int id) throws SQLException {
        if (userExists(id)) {
            supprimerUtilisateur(id);
        }

        String query = "DELETE FROM citoyen WHERE id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }

    private boolean userExists(int id) throws SQLException {
        String query = "SELECT * FROM utilisateurs WHERE idCitoyen = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    private void supprimerUtilisateur(int id) throws SQLException {
        String query = "DELETE FROM utilisateurs WHERE idCitoyen = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }

    public static void main(String[] args) throws SQLException {
        CitoyenService c = new CitoyenService();
        Date birthDate = new Date(124, 1, 13);
        // Citoyen c1 = new Citoyen(2345,"thon","lakhal","manzah",birthDate) ;
        c.supprimerCitoyen2(1);


    }

    public void modify(int id, int cin, String nom, String prenom, String adresse, Date datenaissance, String genre) throws SQLException {
        String query = "UPDATE citoyen SET cin = ?, nom = ?, prenom = ?, adresse = ?, datenaissance = ?, genre = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, cin);
            preparedStatement.setString(2, nom);
            preparedStatement.setString(3, prenom);
            preparedStatement.setString(4, adresse);
            preparedStatement.setDate(5, datenaissance);
            preparedStatement.setString(6, genre);
            preparedStatement.setInt(7, id);

            preparedStatement.executeUpdate();
        }
    }


}
