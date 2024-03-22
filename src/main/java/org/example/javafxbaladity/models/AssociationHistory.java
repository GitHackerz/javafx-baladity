package org.example.javafxbaladity.models;

public class AssociationHistory {
    private int id;
    private String title;
    private String description;
    private Association association;

    public AssociationHistory(int id, String title, String description, Association association) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.association = association;
    }

    public AssociationHistory(String title, String description, Association association) {
        this.title = title;
        this.description = description;
        this.association = association;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Association getAssociation() {
        return association;
    }

    public void setAssociation(Association association) {
        this.association = association;
    }


}
