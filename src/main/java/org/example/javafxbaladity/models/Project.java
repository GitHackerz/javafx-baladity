package org.example.javafxbaladity.models;

import java.sql.Date;

public class Project {
    private int id;
    private String titre;
    private String description;
    private String statut;
    private float budget;
    private Date date_debut;
    private Date date_fin;

    public Project(int id, String titre, String description, String statut, float budget, Date date_debut, Date date_fin) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.statut = statut;
        this.budget = budget;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
    }

    public Project(String titre, String description, String statut, float budget, Date date_debut, Date date_fin) {
        this.titre = titre;
        this.description = description;
        this.statut = statut;
        this.budget = budget;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
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

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public float getBudget() {
        return budget;
    }

    public void setBudget(float budget) {
        this.budget = budget;
    }

    public Date getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(Date date_debut) {
        this.date_debut = date_debut;
    }

    public Date getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(Date date_fin) {
        this.date_fin = date_fin;
    }
}
