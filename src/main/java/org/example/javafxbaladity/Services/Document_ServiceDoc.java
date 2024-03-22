package org.example.javafxbaladity.Services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.javafxbaladity.interfaces.IServiceDoc;
import org.example.javafxbaladity.models.Document;
import org.example.javafxbaladity.utils.Database;

import java.sql.*;

public class Document_ServiceDoc implements IServiceDoc<Document> {
    private Connection conn;
    private Statement ste;
    public Document_ServiceDoc()
    {
       conn = Database.getConnection();
    }
    @Override
    public void ajouter(Document document) throws SQLException {

        ste = conn.createStatement();
        //String req = "INSERT INTO test1 (nom_2) VALUES ("+test1.getNom_1()+")";

        String req = "INSERT INTO documents (type_doc, statut_doc, date_emission_doc, date_expiration_doc, estarchive) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = conn.prepareStatement(req);
        preparedStatement.setString(1, document.getType_doc());
        preparedStatement.setString(2, document.getStatut_doc());
        preparedStatement.setString(3, document.getDate_emission_doc());
        preparedStatement.setString(4, document.getDate_expiration_doc());
        preparedStatement.setBoolean(5, document.isEstarchive());
        preparedStatement.executeUpdate();

    }

    @Override
    public void modifier(Document document) throws SQLException {

        ste = conn.createStatement();
        String req = "UPDATE documents SET type_doc=? ,statut_doc = ?, date_emission_doc = ?, date_expiration_doc = ?, estarchive = ? WHERE id_doc = ?";

        PreparedStatement preparedStatement = conn.prepareStatement(req);
        preparedStatement.setString(1, document.getType_doc());
        preparedStatement.setString(2, document.getStatut_doc());
        preparedStatement.setString(3, document.getDate_emission_doc());
        preparedStatement.setString(4, document.getDate_expiration_doc());
        preparedStatement.setBoolean(5, document.isEstarchive());
        preparedStatement.setInt(6, document.getId_doc());
        preparedStatement.executeUpdate();

    }

    @Override
    public void supprimer(int id) throws SQLException {
        ste = conn.createStatement();
        String req = "DELETE FROM documents WHERE id_doc = '" + id + "'";
        ste.execute(req);
    }
    public boolean RechercherDoc(Document d) throws SQLException {
        String sql = "select * from documents where type_doc = '" + d.getType_doc() + "'";
        Statement statement = conn.createStatement();

        ResultSet rs = statement.executeQuery(sql);
        ObservableList<Document> list = FXCollections.observableArrayList();
        while (rs.next()){
            Document p = new Document();
            p.setId_doc(rs.getInt("id_doc"));
            list.add(p);
        }
        return !list.isEmpty();
    }


    @Override
    public ObservableList<Document> afficher() throws SQLException { //Entities.Test1 test1
        String sql = "select * from documents";
        Statement statement = conn.createStatement();

        ResultSet rs = statement.executeQuery(sql);
        ObservableList<Document> list = FXCollections.observableArrayList();
        while (rs.next()){
            Document p = new Document();
            p.setId_doc(rs.getInt("id_doc"));
            p.setType_doc(rs.getString("type_doc"));
            p.setStatut_doc(rs.getString("statut_doc"));
            p.setDate_emission_doc(rs.getString("date_emission_doc"));
            p.setDate_expiration_doc(rs.getString("date_expiration_doc"));
            p.setEstarchive(rs.getBoolean("estarchive"));
            p.setNb_req(rs.getInt("nb_req"));

            list.add(p);

        }
        return list;
    }


    public ObservableList<Document> afficherCitoyen() throws SQLException {
        String sql = "select * from documents";
        Statement statement = conn.createStatement();

        ResultSet rs = statement.executeQuery(sql);
        ObservableList<Document> list = FXCollections.observableArrayList();
        while (rs.next()){
            if(!rs.getBoolean("estarchive")){
                Document p = new Document();
                p.setId_doc(rs.getInt("id_doc"));
                p.setType_doc(rs.getString("type_doc"));
                p.setStatut_doc(rs.getString("statut_doc"));
                p.setDate_emission_doc(rs.getString("date_emission_doc"));
                p.setDate_expiration_doc(rs.getString("date_expiration_doc"));
                p.setEstarchive(rs.getBoolean("estarchive"));
                list.add(p);

            }
        }
        return list;
    }


    final public String getNbDocs() throws SQLException
    {
        String sql = "select COUNT(*) as nbDocs from documents";
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        if (rs.next()) {
            String nbDocs = rs.getString("nbDocs");
            return nbDocs;
        } else {
            throw new SQLException("No documents found");
        }

    }


}
