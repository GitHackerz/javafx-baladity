package org.example.javafxbaladity.models;

public class Document_request {

    private int id_ddoc;

    private String type_ddoc;
    private String description_ddoc;
    private String statut_ddoc;
    private String date_ddoc;
    private int id_document;
    private String date_traitement_ddoc;



    public Document_request()
    {

    }
    public Document_request(int id_ddoc, String type_ddoc, String description_ddoc, String statut_ddoc, String date_ddoc, int id_doc) {
        this.id_ddoc = id_ddoc;
        this.type_ddoc = type_ddoc;
        this.description_ddoc = description_ddoc;
        this.statut_ddoc = statut_ddoc;
        this.date_ddoc = date_ddoc;
        this.id_document = id_doc;
    }

    public Document_request(String type_ddoc, String description_ddoc, String statut_ddoc, String date_ddoc) {
        this.type_ddoc = type_ddoc;
        this.description_ddoc = description_ddoc;
        this.statut_ddoc = statut_ddoc;
        this.date_ddoc = date_ddoc;
    }

    public Document_request(String type_ddoc, String description_ddoc, String statut_ddoc, String date_ddoc,int id_doc) {
        this.type_ddoc = type_ddoc;
        this.description_ddoc = description_ddoc;
        this.statut_ddoc = statut_ddoc;
        this.date_ddoc = date_ddoc;
        this.id_document = id_doc;

    }


    public int getId_ddoc() {
        return id_ddoc;
    }

    public void setId_ddoc(int id_ddoc) {
        this.id_ddoc = id_ddoc;
    }

    public String getType_ddoc() {
        return type_ddoc;
    }

    public void setType_ddoc(String type_ddoc) {
        this.type_ddoc = type_ddoc;
    }

    public String getDescription_ddoc() {
        return description_ddoc;
    }

    public void setDescription_ddoc(String description_ddoc) {
        this.description_ddoc = description_ddoc;
    }

    public String getStatut_ddoc() {
        return statut_ddoc;
    }

    public void setStatut_ddoc(String statut_ddoc) {
        this.statut_ddoc = statut_ddoc;
    }

    public String getDate_ddoc() {
        return date_ddoc;
    }

    public void setDate_ddoc(String date_ddoc) {
        this.date_ddoc = date_ddoc;
    }

    public int getId_document() {
        return id_document;
    }

    public void setId_document(int id_doc) {
        this.id_document = id_doc;
    }

    public String getDate_traitement_ddoc() {
        return date_traitement_ddoc;
    }

    public void setDate_traitement_ddoc(String date_traitement_ddoc) {
        this.date_traitement_ddoc = date_traitement_ddoc;
    }
}
