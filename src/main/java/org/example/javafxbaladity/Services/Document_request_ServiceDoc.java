package org.example.javafxbaladity.Services;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.javafxbaladity.interfaces.IServiceDoc;
import org.example.javafxbaladity.models.Document_request;
import org.example.javafxbaladity.utils.Database;

import java.sql.*;

public class Document_request_ServiceDoc implements IServiceDoc<Document_request> {


    private Connection conn;
    private Statement ste;
    public Document_request_ServiceDoc()
    {
        conn = Database.getConnection();
    }


    /*----------------------------------CITOYEN----------------------------*/

    //Ajouter for citoyen
    @Override
    public void ajouter(Document_request documentRequest) throws SQLException {
        ste = conn.createStatement();
        //String req = "INSERT INTO test1 (nom_2) VALUES ("+test1.getNom_1()+")";

        String req = "INSERT INTO demande_document (type_ddoc, description_ddoc,statut_ddoc ,date_ddoc,id_document) " +
                "VALUES (?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = conn.prepareStatement(req);
        preparedStatement.setString(1, documentRequest.getType_ddoc());
        preparedStatement.setString(2, documentRequest.getDescription_ddoc());
        preparedStatement.setString(3, documentRequest.getStatut_ddoc());
        preparedStatement.setString(4, documentRequest.getDate_ddoc());
        preparedStatement.setInt(5, documentRequest.getId_document());
        preparedStatement.executeUpdate();

        //ADD le nb of req docs in docs table
        String add_req = "UPDATE documents SET nb_req = nb_req + 1 " +
                "WHERE id_doc = ?";
        PreparedStatement preparedStatement2 = conn.prepareStatement(add_req);
        preparedStatement2.setInt(1, documentRequest.getId_document());
        preparedStatement2.executeUpdate();
    }


    //Modifier for Citoyen
    @Override
    public void modifier(Document_request documentRequest) throws SQLException {

        ste = conn.createStatement();
        String req = "UPDATE demande_document SET type_ddoc = ?, description_ddoc = ?, date_ddoc = ? " +
                "WHERE id_ddoc = ?";

        PreparedStatement preparedStatement = conn.prepareStatement(req);
        preparedStatement.setString(1, documentRequest.getType_ddoc());
        preparedStatement.setString(2, documentRequest.getDescription_ddoc());
        preparedStatement.setString(3, documentRequest.getDate_ddoc());
        preparedStatement.executeUpdate();

    }


    //for Citoyen
    @Override
    public ObservableList<Document_request> afficher() throws SQLException {

        String sql = "select * from demande_document";
        Statement statement = conn.createStatement();

        ResultSet rs = statement.executeQuery(sql);
        ObservableList<Document_request> list = FXCollections.observableArrayList();
        while (rs.next()){
            Document_request p = new Document_request();
            p.setId_ddoc(rs.getInt("id_ddoc"));
            p.setType_ddoc(rs.getString("type_ddoc"));
            p.setDescription_ddoc(rs.getString("description_ddoc"));
            p.setStatut_ddoc(rs.getString("statut_ddoc")); //statut ne doit pas etre affiché pour le citoyen
            p.setDate_ddoc(rs.getString("date_ddoc"));
            list.add(p);
        }
        return list;
    }


    public ObservableList<Document_request> afficherEnAttente() throws SQLException {

        String sql = "select * from demande_document";
        Statement statement = conn.createStatement();

        ResultSet rs = statement.executeQuery(sql);
        ObservableList<Document_request> list = FXCollections.observableArrayList();
        while (rs.next()){
            if(rs.getString("statut_ddoc").equals("en attente")) {  //display only the visible docs

                Document_request p = new Document_request();
                p.setId_ddoc(rs.getInt("id_ddoc"));
                p.setType_ddoc(rs.getString("type_ddoc"));
                p.setDescription_ddoc(rs.getString("description_ddoc"));
                p.setStatut_ddoc(rs.getString("statut_ddoc")); //statut ne doit pas etre affiché pour le citoyen
                p.setDate_ddoc(rs.getString("date_ddoc"));
                p.setDate_traitement_ddoc(rs.getString("date_traitement_ddoc"));

                list.add(p);
            }
        }
        return list;
    }

    public ObservableList<Document_request> afficherAccepAndRejec() throws SQLException {

        String sql = "select * from demande_document";
        Statement statement = conn.createStatement();

        ResultSet rs = statement.executeQuery(sql);
        ObservableList<Document_request> list = FXCollections.observableArrayList();
        while (rs.next()){
            if(!rs.getString("statut_ddoc").equals("en attente")) {  //display only the visible docs

                Document_request p = new Document_request();
                p.setId_ddoc(rs.getInt("id_ddoc"));
                p.setType_ddoc(rs.getString("type_ddoc"));
                p.setDescription_ddoc(rs.getString("description_ddoc"));
                p.setStatut_ddoc(rs.getString("statut_ddoc")); //statut ne doit pas etre affiché pour le citoyen
                p.setDate_ddoc(rs.getString("date_ddoc"));
                p.setDate_traitement_ddoc(rs.getString("date_traitement_ddoc"));
                list.add(p);
            }
        }
        return list;
    }



    /*----------------------------------ADMIN----------------------------*/
    //Modifier for ADMIN : ACCEPT / REJECT
    public void modifierDocsRequest(Document_request dddoc) throws SQLException {
        ste = conn.createStatement();
        String req = "UPDATE demande_document SET statut_ddoc = ? , date_traitement_ddoc = ?" +
                "WHERE id_ddoc = ?";

        PreparedStatement preparedStatement = conn.prepareStatement(req);
        preparedStatement.setString(1, dddoc.getStatut_ddoc());
        preparedStatement.setString(2, dddoc.getDate_traitement_ddoc());
        preparedStatement.setInt(3, dddoc.getId_ddoc());
        preparedStatement.executeUpdate();
    }

    public void RefactorDocsRequest(Document_request ddoc) throws SQLException {
        ste = conn.createStatement();
        String req = "UPDATE demande_document SET statut_ddoc = ? " +
                "WHERE id_ddoc = ?";

        PreparedStatement preparedStatement = conn.prepareStatement(req);
        preparedStatement.setString(1,"en attente");
        preparedStatement.setInt(2, ddoc.getId_ddoc());
        preparedStatement.executeUpdate();
    }

    // el supprimer tab9a lel ADMIN
    @Override
    public void supprimer(int id) throws SQLException {
        ste = conn.createStatement();
        String req = "DELETE FROM demande_document WHERE id_ddoc = '" + id + "'";
        ste.execute(req);
    }




    //for ADMIN
    final public ObservableList<Document_request> afficherDocsRequests() throws SQLException {
        String sql = "select * from demande_document";
        Statement statement = conn.createStatement();

        ResultSet rs = statement.executeQuery(sql);
        ObservableList<Document_request> list = FXCollections.observableArrayList();
        while (rs.next()){
            Document_request p = new Document_request();
            p.setId_ddoc(rs.getInt("id_ddoc"));
            p.setType_ddoc(rs.getString("type_ddoc"));
            p.setDescription_ddoc(rs.getString("description_ddoc"));
            p.setStatut_ddoc(rs.getString("statut_ddoc"));
            p.setDate_ddoc(rs.getString("date_ddoc"));
            p.setDate_traitement_ddoc(rs.getString("date_traitement_ddoc"));
            list.add(p);
        }
        return list;
    }
    /*-------------------------------------------------Others-----------------------------------------------------------------*/

    final public String getNbReqDocs() throws SQLException
    {
        String sql = "select COUNT(*) as nbReqDocs from demande_document ";
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        // Extracting the result value
        if (rs.next()) {
            String nbDocs = rs.getString("nbReqDocs");
            return nbDocs;
        } else {
            throw new SQLException("No documents found");
        }

    }

    final public int getNbenAttente() throws SQLException
    {
        String sql = "select COUNT(*) as nbReqDocs from demande_document where statut_ddoc = 'en attente'";
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        // Extracting the result value
        if (rs.next()) {
            int nbDocs = rs.getInt("nbReqDocs");
            return nbDocs;
        } else {
            throw new SQLException("No documents found");
        }

    }

    final public int getNbRejected() throws SQLException
    {
        String sql = "select COUNT(*) as nbReqDocs from demande_document where statut_ddoc = 'rejected'";
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        // Extracting the result value
        if (rs.next()) {
            int nbDocs = rs.getInt("nbReqDocs");
            return nbDocs;
        } else {
            throw new SQLException("No documents found");
        }

    }

    final public int getNbAccepted() throws SQLException
    {
        String sql = "select COUNT(*) as nbReqDocs from demande_document where statut_ddoc = 'accepted'";
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        // Extracting the result value
        if (rs.next()) {
            int nbDocs = rs.getInt("nbReqDocs");
            return nbDocs;
        } else {
            throw new SQLException("No documents found");
        }

    }

    final public double AcceptRate() throws SQLException
    {
        double nbRejec;
        double nbAccepted;

        String sql = "select COUNT(*) as nbAccep from demande_document where statut_ddoc = 'accepted'";
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        // Extracting the result value
        if (rs.next()) {
            nbAccepted = rs.getInt("nbAccep");
        } else {
            throw new SQLException("No documents found");
        }
        sql = "select COUNT(*) as nbRejec from demande_document where statut_ddoc = 'rejected'";
        rs = statement.executeQuery(sql);
        // Extracting the result value
        if (rs.next()) {
            nbRejec = rs.getInt("nbRejec");
        } else {
            throw new SQLException("No documents found");
        }
        if(nbRejec!=0)
            return (nbAccepted/(nbRejec+nbAccepted))*100;
        if(nbRejec==nbAccepted)
            return 50;
        return 100;

    }



}




