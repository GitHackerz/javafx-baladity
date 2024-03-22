package org.example.javafxbaladity.models;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Association {

    private int id;
    private String nom;
    private String adresse;
    private String type;
    private float caisse;
    private String status;

    private List<AssociationHistory> history = new ArrayList<>();

    public Association(int id, String nom, String adresse, String type, float caisse, String status) {
        this.id = id;
        this.nom = nom;
        this.adresse = adresse;
        this.type = type;
        this.caisse = caisse;
        this.status = status;
    }

    public Association(String nom, String adresse, String type, float caisse, String status) {
        this(0, nom, adresse, type, caisse, status); // Appel du constructeur avec id par défaut (0)
    }

    public Association() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getCaisse() {
        return caisse;
    }

    public void setCaisse(float caisse) {
        this.caisse = caisse;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Association{" +
                "nom='" + nom + '\'' +
                ", adresse='" + adresse + '\'' +
                ", type='" + type + '\'' +
                ", caisse=" + caisse +
                ", status='" + status + '\'' +
                '}';
    }


}
