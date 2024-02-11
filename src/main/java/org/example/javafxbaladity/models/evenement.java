package org.example.javafxbaladity.models;

public class evenement {
    private int id;
    private String titre,description,lieu,nomContact,emailContact,date;
    private boolean statut;

    public evenement(){
    }

    public evenement(int id,String titre, String description,String lieu, String nomContact,String emailContact,String date , boolean statut) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.lieu = lieu;
        this.nomContact = nomContact;
        this.emailContact = emailContact;
        this.statut = statut;
    }

    public evenement(String titre, String description,String lieu, String nomContact,String emailContact,String date , boolean statut) {
        this.titre = titre;
        this.description = description;
        this.lieu = lieu;
        this.nomContact = nomContact;
        this.emailContact = emailContact;
        this.statut = statut;

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getNomContact() {
        return nomContact;
    }

    public void setNomContact(String nomContact) {
        this.nomContact = nomContact;
    }

    public String getEmailContact() {
        return emailContact;
    }

    public void setEmailContact(String emailContact) {
        this.emailContact = emailContact;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isStatut() {
        return statut;
    }

    public void setStatut(boolean statut) {
        this.statut = statut;
    }


    @Override
    public String toString() {
        return "Personne{" +
                "id=" + id +
                ", titre=" + titre +
                ", description='" + description + '\'' +
                ", lieu='" + lieu + '\'' +
                ", nom conatct='" + nomContact + '\'' +
                ", email contact='" + emailContact + '\'' +
                ", statut='" + statut + '\'' +

                '}';

    }
}
