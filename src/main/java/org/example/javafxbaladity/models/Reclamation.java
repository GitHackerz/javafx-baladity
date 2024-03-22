package org.example.javafxbaladity.models;

public class Reclamation {
    private int idReclamation;
    private String typeReclamation;
    private String descriptionReclamation;
    private String statutReclamation;
    private String dateReclamation;

    public Reclamation(String statut) {

    }

    public Reclamation(int x, String type, String description, String date) {

    }

    public int getIdReclamation() {
        return idReclamation;
    }

    public void setIdReclamation(int idReclamation) {
        this.idReclamation = idReclamation;
    }

    public String getTypeReclamation() {
        return typeReclamation;
    }

    public void setTypeReclamation(String typeReclamation) {
        this.typeReclamation = typeReclamation;
    }

    public String getDescriptionReclamation() {
        return descriptionReclamation;
    }

    public void setDescriptionReclamation(String descriptionReclamation) {
        this.descriptionReclamation = descriptionReclamation;
    }

    public String getStatutReclamation() {
        return statutReclamation;
    }

    public void setStatutReclamation(String statutReclamation) {
        this.statutReclamation = statutReclamation;
    }

    public String getDateReclamation() {
        return dateReclamation;
    }

    public void setDateReclamation(String dateReclamation) {
        this.dateReclamation = dateReclamation;
    }

    public Reclamation(int idReclamation, String typeReclamation, String descriptionReclamation, String statutReclamation, String dateReclamation) {
        this.idReclamation = idReclamation;
        this.typeReclamation = typeReclamation;
        this.descriptionReclamation = descriptionReclamation;
        this.statutReclamation = statutReclamation;
        this.dateReclamation = dateReclamation;
    }

    public Reclamation(String typeReclamation, String descriptionReclamation, String statutReclamation, String dateReclamation) {
        this.typeReclamation = typeReclamation;
        this.descriptionReclamation = descriptionReclamation;
        this.statutReclamation = statutReclamation;
        this.dateReclamation = dateReclamation;
    }

    public Reclamation() {
    }

    @Override
    public String toString() {
        return "Reclamation{" +
                "id=" + idReclamation +
                ", type=" + typeReclamation +
                ", description='" + descriptionReclamation + '\'' +
                ", statut='" + statutReclamation + '\'' +
                ", date='" + dateReclamation + '\'' +
                '}';
    }

    public void open() {
    }
}
