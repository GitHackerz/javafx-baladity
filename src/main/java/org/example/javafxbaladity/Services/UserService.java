package org.example.javafxbaladity.Services;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.example.javafxbaladity.models.Citoyen;
import org.example.javafxbaladity.utils.Database;
import org.example.javafxbaladity.models.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.*;
import java.util.Base64;


public class UserService {

    private Connection conn;
    public int countusers() throws SQLException {
        String query = "SELECT COUNT(*) AS count FROM utilisateurs";
        PreparedStatement statement = conn.prepareStatement(query) ;
        ResultSet resultSet = statement.executeQuery() ;
        if (resultSet.next()) {
            return resultSet.getInt("count");
        } else {
            return 0;
        }
    }
    public int countUsersEMP() throws SQLException {
        String query = "SELECT COUNT(*) AS count FROM utilisateurs WHERE role = 'employe'";
        PreparedStatement statement = conn.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("count");
        } else {
            return 0;
        }
    }
    public UserService() {
        conn = Database.getConnection();

    }

    private static final int SALT_LENGTH = 16;
    private static final SecureRandom random = new SecureRandom();

    public static String hashPassword(String password) throws NoSuchAlgorithmException {

        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);

        // Hacher le mot de passe avec le sel
        String hashedPassword = hashWithSalt(password, salt);

        // Concaténer le sel et le hachage du mot de passe pour le stockage
        return Base64.getEncoder().encodeToString(salt) + ":" + hashedPassword;
    }

    private static String hashWithSalt(String password, byte[] salt) throws NoSuchAlgorithmException {
        // Concatène le sel avec le mot de passe
        String saltedPassword = Base64.getEncoder().encodeToString(salt) + password;

        // Hash le sel concaténé avec le mot de passe
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashedPassword = md.digest(saltedPassword.getBytes());

        // Retourne le résultat haché
        return Base64.getEncoder().encodeToString(hashedPassword);
    }

    public static boolean verifyPassword(String inputPassword, String storedHash) throws NoSuchAlgorithmException {
        // Séparation de la chaîne stockée en sel et hachage du mot de passe
        String[] parts = storedHash.split(":");
        String storedSalt = parts[0];
        String storedHashedPassword = parts[1];

        // Décodage du sel depuis Base64
        byte[] salt = Base64.getDecoder().decode(storedSalt);

        // Calcul du hachage du mot de passe fourni avec le même sel
        String hashedPassword = hashWithSalt(inputPassword, salt);

        // Comparaison des hachages
        return storedHashedPassword.equals(hashedPassword);
    }

    public Citoyen verifierCinUser(int cin) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Citoyen c = null; // Initialisation de la variable c

        try {
            String query = "SELECT * FROM citoyen WHERE cin = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, cin);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                c = new Citoyen(rs.getInt("id"), rs.getInt("cin"), rs.getString("nom"),
                        rs.getString("prenom"), rs.getString("adresse"), rs.getDate("datenaissance"), rs.getNString("genre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return c;
    }

    public boolean emailExists(String email) {
        String query = "SELECT * FROM utilisateurs WHERE email = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next(); // Si resultSet.next() est vrai, cela signifie qu'un enregistrement avec cet e-mail existe
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public int getUserIdByEmail(String email) {
        String query = "SELECT id FROM utilisateurs WHERE email = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id"); // Retourne l'ID de l'utilisateur si l'email existe
                } else {
                    return -1; // Retourne -1 si l'email n'existe pas
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1; // En cas d'erreur, retourne -1
        }
    }

    public boolean idCitoyenExists(int idCitoyen) {
        String query = "SELECT COUNT(*) FROM utilisateurs WHERE idCitoyen = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, idCitoyen);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }


    public Citoyen HetCitoyen(int id) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Citoyen c = null; // Initialisation de la variable c

        try {
            String query = "SELECT * FROM citoyen WHERE id = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, id);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                c = new Citoyen(rs.getInt("id"), rs.getInt("cin"), rs.getString("nom"),
                        rs.getString("prenom"), rs.getString("adresse"), rs.getDate("datenaissance"), rs.getNString("genre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return c;
    }

    public User HetUser(String email) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        User u = null; // Initialisation de la variable c

        try {
            String query = "SELECT * FROM utilisateurs WHERE email = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, email);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                u = new User(rs.getInt("id"), rs.getString("email"), rs.getString("password"),
                        rs.getInt("numtel"), rs.getNString("role"), rs.getString("heuredebut"), rs.getString("heurefin"), rs.getString("Image")
                        , rs.getInt("idCitoyen"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return u;


    }

    public User HetUser2(int x) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        User u = null; // Initialisation de la variable c

        try {
            String query = "SELECT * FROM utilisateurs WHERE id = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, x);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                u = new User(rs.getInt("id"), rs.getString("email"), rs.getString("password"),
                        rs.getInt("numtel"), rs.getNString("role"), rs.getString("heuredebut"), rs.getString("heurefin"), rs.getString("Image")
                        , rs.getInt("idCitoyen"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return u;


    }


    public static void main(String[] args) throws SQLException, NoSuchAlgorithmException {
        Citoyen c = new Citoyen();

        UserService u = new UserService();
        //c = u.verifierCinUser(121212) ;
        //int z = u.idCitoyen(121212) ;
        // User u1 = new User(1,"ferchichimohamed",hashPassword("emir"),1,"user",null,null,1) ;

        String password = "emir";


       /* String hashedPassword = hashPassword(password);
        System.out.println("Mot de passe haché : " + hashedPassword);


        boolean passwordMatch = verifyPassword("emir", hashedPassword);
        if (passwordMatch) {
            System.out.println("Mot de passe correct.");
        } else {
            System.out.println("Mot de passe incorrect.");
        }*/
        System.out.println(u.getUserIdByEmail("emna.lakhal@esprit.tn"));


    }

    public int idCitoyen(int cin) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        int idCitoyen = -1;

        try {
            String query = "SELECT * FROM citoyen WHERE cin = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, cin);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                idCitoyen = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return idCitoyen;


    }

    public void ajouterUser(User u) throws SQLException, NoSuchAlgorithmException {


        String query = "INSERT INTO utilisateurs VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";


        PreparedStatement preparedStatement = conn.prepareStatement(query);


        preparedStatement.setInt(1, u.getId());
        preparedStatement.setString(2, u.getEmail());
        preparedStatement.setString(3, hashPassword2(u.getPassword()));
        preparedStatement.setInt(4, u.getNumero());
        preparedStatement.setString(5, u.getRole());
        preparedStatement.setString(6, u.getHeureDebut());
        preparedStatement.setString(7, u.getHeureFin());
        preparedStatement.setString(8, u.getPathImg());
        preparedStatement.setInt(9, u.getIdCitoyen());


        preparedStatement.executeUpdate();

    }

    public ObservableList<User> getUsersList() {
        ObservableList<User> x = FXCollections.observableArrayList();

        Connection conn = Database.getConnection();
        String query = "select * from utilisateurs";
        Statement s1;
        ResultSet r1;
        try {
            s1 = conn.createStatement();
            r1 = s1.executeQuery(query);
            User u;
            while (r1.next()) {
                u = new User(r1.getInt("id"), r1.getString("email"), r1.getString("password"),
                        r1.getInt("numtel"), r1.getNString("role"), r1.getString("heuredebut"), r1.getString("heurefin"),
                        r1.getString("Image")
                        , r1.getInt("idCitoyen"));
                x.add(u);


            }
        } catch (Exception ex) {
            ex.printStackTrace();

        }
        return x;

    }

    public void updateEmployee(int id, String role, String heureDebut, String heureFin) {
        String query = "UPDATE utilisateurs SET role = ?, heuredebut = ?, heurefin = ? WHERE id = ?";


        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, role);
            preparedStatement.setString(2, heureDebut);
            preparedStatement.setString(3, heureFin);
            preparedStatement.setInt(4, id);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Employee Ajouté  jawek jben");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("fama mochkla ");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public ObservableList<User> getEmployeList() throws SQLException {
        ObservableList<User> employees = FXCollections.observableArrayList();
        String query = "SELECT * FROM utilisateurs WHERE role = ?";

        Connection conn = Database.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query);

        pstmt.setString(1, "employe");
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            User user = new User(
                    rs.getInt("id"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getInt("numtel"),
                    rs.getString("role"),
                    rs.getString("heuredebut"),
                    rs.getString("heurefin"),
                    rs.getString("Image"),
                    rs.getInt("idCitoyen"));

            employees.add(user);
        }

        return employees;
    }

    public static String hashPassword2(String password) throws NoSuchAlgorithmException {
        // Hasher le mot de passe sans sel
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashedPassword = md.digest(password.getBytes());

        // Retourner le hachage encodé en Base64
        return Base64.getEncoder().encodeToString(hashedPassword);
    }

    public boolean authenticateUser(String username, String password) {
        String query = "SELECT * FROM utilisateurs WHERE email = ? AND password = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, hashPassword2(password));
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next(); // If a row is found, the user is authenticated
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }


    public boolean validerPassword(int id, String password) {
        String query = "SELECT * FROM utilisateurs WHERE password = ? AND id = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, hashPassword2(password));
            statement.setInt(2, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                boolean isValid = resultSet.next();
                if (!isValid) {
                    // Show an alert if the password is invalid
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Invalid Password");
                    alert.setHeaderText(null);
                    alert.setContentText("The password is incorrect.");
                    alert.showAndWait();
                }
                return isValid;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Show an alert for SQL exception
            showErrorAlert("SQL Error", "An error occurred while validating the password.");
            return false;
        } catch (Exception ex) {
            ex.printStackTrace();
            // Show a generic alert for other exceptions
            showErrorAlert("Error", "An unexpected error occurred.");
            return false;
        }
    }

    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void modifyPassword(int id, String password) {
        String query = "UPDATE utilisateurs SET password = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, hashPassword2(password));
            preparedStatement.setInt(2, id);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Modification jawha hafalet");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("fama mochkla ");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQLException appropriately
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public void modify(User u) throws SQLException {
        String query = "UPDATE utilisateurs SET email = ?, password = ?, numtel = ?, role = ?, heuredebut = ?, heurefin = ?, Image = ?, idCitoyen = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, u.getEmail());
            preparedStatement.setString(2, u.getPassword());
            preparedStatement.setInt(3, u.getNumero());
            preparedStatement.setString(4, u.getRole());
            preparedStatement.setString(5, u.getHeureDebut());
            preparedStatement.setString(6, u.getHeureFin());
            preparedStatement.setString(7, u.getPathImg());
            preparedStatement.setInt(8, u.getIdCitoyen());
            preparedStatement.setInt(9, u.getId());

            preparedStatement.executeUpdate();
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Modification jawha hafalet");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("fama mochkla ");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void SupprimerUser(int id) throws SQLException {


        String query = "DELETE FROM utilisateurs where(id =" + id + ")";
        Statement s = conn.createStatement();
        s.executeUpdate(query);


    }
}








