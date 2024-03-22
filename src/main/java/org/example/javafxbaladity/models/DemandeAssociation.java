package org.example.javafxbaladity.models;

public class DemandeAssociation {

    private int id , id_user;
    private String nom,Adresse,type;
    private float Caisse;

    public DemandeAssociation( String nom, String adresse, String type, float caisse,int id_user) {
        this.nom = nom;
        Adresse = adresse;
        this.type = type;
        Caisse = caisse;
        this.id_user=id_user;

    }
    public DemandeAssociation( int id ,String nom, String adresse, String type, float caisse,int id_user) {
        this.nom = nom;
        Adresse = adresse;
        this.type = type;
        Caisse = caisse;
        this.id_user=id_user;

    }

    public DemandeAssociation( String nom, String adresse, String type, float caisse) {
        this.nom = nom;
        Adresse = adresse;
        this.type = type;
        Caisse = caisse;
    }
    public DemandeAssociation( int id, String nom, String adresse, String type, float caisse) {
        this.id = id;
        this.nom = nom;
        Adresse = adresse;
        this.type = type;
        Caisse = caisse;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return Adresse;
    }

    public void setAdresse(String adresse) {
        Adresse = adresse;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getCaisse() {
        return Caisse;
    }

    public void setCaisse(float caisse) {
        Caisse = caisse;
    }

    @Override
    public String toString() {
        return "DemandeAssociation{" +
                "id=" + id +
                ", id_user=" + id_user +
                ", nom='" + nom + '\'' +
                ", Adresse='" + Adresse + '\'' +
                ", type='" + type + '\'' +
                ", Caisse=" + Caisse +
                '}';
    }
}
