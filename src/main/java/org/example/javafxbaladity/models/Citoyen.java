package org.example.javafxbaladity.models;

import java.util.Date;

public class Citoyen {
    private  int idCitoyen ;
    private int cinCitoyen ;
    private String nomCitoyen ;
    private String prenomCitoyen ;
    private String adresseCitoyen ;
    private Date DateNCitoyen ;
    private String genre ;
public Citoyen(){} ;
    public Citoyen(int idCitoyen, int cinCitoyen, String nomCitoyen, String prenomCitoyen, String adresseCitoyen, Date dateNCitoyen, String genre) {
        this.idCitoyen = idCitoyen;
        this.cinCitoyen = cinCitoyen;
        this.nomCitoyen = nomCitoyen;
        this.prenomCitoyen = prenomCitoyen;
        this.adresseCitoyen = adresseCitoyen;
        DateNCitoyen = dateNCitoyen;
        this.genre = genre ;
    }

    public Citoyen(int cinCitoyen, String nomCitoyen, String prenomCitoyen, String adresseCitoyen, Date dateNCitoyen, String genre) {
        this.cinCitoyen = cinCitoyen;
        this.nomCitoyen = nomCitoyen;
        this.prenomCitoyen = prenomCitoyen;
        this.adresseCitoyen = adresseCitoyen;
        DateNCitoyen = dateNCitoyen;
        this.genre = genre ;

    }

    @Override
    public String toString() {
        return "Citoyen{" +
                "idCitoyen=" + idCitoyen +
                ", cinCitoyen=" + cinCitoyen +
                ", nomCitoyen='" + nomCitoyen + '\'' +
                ", prenomCitoyen='" + prenomCitoyen + '\'' +
                ", adresseCitoyen='" + adresseCitoyen + '\'' +
                ", DateNCitoyen=" + DateNCitoyen +
                ", genre='" + genre + '\'' +
                '}';
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getIdCitoyen() {
        return idCitoyen;
    }

    public int getCinCitoyen() {
        return cinCitoyen;
    }

    public String getNomCitoyen() {
        return nomCitoyen;
    }

    public String getPrenomCitoyen() {
        return prenomCitoyen;
    }

    public String getAdresseCitoyen() {
        return adresseCitoyen;
    }

    public Date getDateNCitoyen() {
        return DateNCitoyen;
    }

    public void setIdCitoyen(int idCitoyen) {
        this.idCitoyen = idCitoyen;
    }

    public void setCinCitoyen(int cinCitoyen) {
        this.cinCitoyen = cinCitoyen;
    }

    public void setNomCitoyen(String nomCitoyen) {
        this.nomCitoyen = nomCitoyen;
    }

    public void setPrenomCitoyen(String prenomCitoyen) {
        this.prenomCitoyen = prenomCitoyen;
    }

    public void setAdresseCitoyen(String adresseCitoyen) {
        this.adresseCitoyen = adresseCitoyen;
    }

    public void setDateNCitoyen(Date dateNCitoyen) {
        DateNCitoyen = dateNCitoyen;
    }
}
