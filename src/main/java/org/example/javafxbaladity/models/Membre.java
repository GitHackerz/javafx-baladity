package org.example.javafxbaladity.models;
import org.example.javafxbaladity.models.evenement;
public class Membre {

    private int id;
    private String nom;
    private String prenom;
    private int age;
    private int event_id;

   public Membre(){}
   public Membre(int id,String nom,String prenom,int age,int event_id)
    {
        this.id=id;
        this.nom=nom;
        this.prenom=prenom;
        this.age=age;
        this.event_id=event_id;

    }
   public Membre(String nom,String prenom,int age,int event_id)
    {
        this.nom=nom;
        this.prenom=prenom;
        this.age=age;
        this.event_id=event_id;

    }

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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

}
