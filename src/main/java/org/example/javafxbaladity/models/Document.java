package org.example.javafxbaladity.models;


public class Document {
    private int id_doc;

    private String type_doc;
    private String statut_doc;
    private String date_emission_doc;
    private String date_expiration_doc;
    private boolean estarchive;
    private int nb_req;


    public Document() {
    }

    public Document(String type_doc, String statut_doc, String date_emission_doc, String date_expiration_doc, boolean estarchive) {
        this.type_doc = type_doc;
        this.statut_doc = statut_doc;
        this.date_emission_doc = date_emission_doc;
        this.date_expiration_doc = date_expiration_doc;
        this.estarchive = estarchive;

    }
    public int getId_doc() {
        return id_doc;
    }

    public void setId_doc(int id_doc) {
        this.id_doc = id_doc;
    }

    public String getType_doc() {
        return type_doc;
    }

    public void setType_doc(String type_doc) {
        this.type_doc = type_doc;
    }

    public String getStatut_doc() {
        return statut_doc;
    }

    public void setStatut_doc(String statut_doc) {
        this.statut_doc = statut_doc;
    }

    public String getDate_emission_doc() {
        return date_emission_doc;
    }

    public void setDate_emission_doc(String date_emission_doc) {
        this.date_emission_doc = date_emission_doc;
    }

    public String getDate_expiration_doc() {
        return date_expiration_doc;
    }

    public void setDate_expiration_doc(String date_expiration_doc) {
        this.date_expiration_doc = date_expiration_doc;
    }

    public boolean isEstarchive() {
        return estarchive;
    }

    public void setEstarchive(boolean estarchive) {
        this.estarchive = estarchive;
    }


    public int getNb_req() {
        return nb_req;
    }

    public void setNb_req(int nb_req) {
        this.nb_req = nb_req;
    }

    @Override
    public String toString() {
        return "Document{" +
                "id_doc=" + id_doc +
                ", type_doc='" + type_doc + '\'' +
                ", statut_doc='" + statut_doc + '\'' +
                ", date_emission_doc='" + date_emission_doc + '\'' +
                ", date_expiration_doc='" + date_expiration_doc + '\'' +
                ", estarchive=" + estarchive +
                '}';
    }

}
