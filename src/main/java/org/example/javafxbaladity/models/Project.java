package org.example.javafxbaladity.models;

import java.time.LocalDateTime;

public class Project {
    private int id;
    private String titre;
    private String description;
    private String statut;
    private float budget;
    private LocalDateTime date_debut;
    private LocalDateTime date_fin;

    public Project(int id, String titre, String description, String statut, float budget, LocalDateTime date_debut, LocalDateTime date_fin) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.statut = statut;
        this.budget = budget;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
    }

    public Project(int id, String titre, String description, String statut, LocalDateTime date_debut, LocalDateTime date_fin) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.statut = statut;
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

    public LocalDateTime getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(LocalDateTime date_debut) {
        this.date_debut = date_debut;
    }

    public LocalDateTime getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(LocalDateTime date_fin) {
        this.date_fin = date_fin;
    }
}
