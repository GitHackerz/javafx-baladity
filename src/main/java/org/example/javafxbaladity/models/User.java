package org.example.javafxbaladity.models;

public class User {
    private int id ;
    private String email ;
    private String password ;
    private int numero ;
    private String role ;
    private String heureDebut ;
    private String heureFin ;
    private String PathImg ;
    private int idCitoyen ;


    public User(){
    } ;

    public User(int id, String email, String password, int numero, String role, String heureDebut, String heureFin, String PathImg  , int idCitoyen) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.numero = numero;
        this.role = role;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.idCitoyen = idCitoyen;
        this.PathImg=PathImg ;

    }

    public User(String email, String password, int numero, String role, String heureDebut, String heureFin, String pathImg, int idCitoyen) {
        this.email = email;
        this.password = password;
        this.numero = numero;
        this.role = role;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        PathImg = pathImg;
        this.idCitoyen = idCitoyen;
    }

    public String getPathImg() {
        return PathImg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(String heureDebut) {
        this.heureDebut = heureDebut;
    }

    public String getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(String heureFin) {
        this.heureFin = heureFin;
    }

    public int getIdCitoyen() {
        return idCitoyen;
    }

    public void setIdCitoyen(int idCitoyen) {
        this.idCitoyen = idCitoyen;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", numero=" + numero +
                ", role='" + role + '\'' +
                ", heureDebut='" + heureDebut + '\'' +
                ", heureFin='" + heureFin + '\'' +
                ", idCitoyen=" + idCitoyen +
                '}';
    }
}
