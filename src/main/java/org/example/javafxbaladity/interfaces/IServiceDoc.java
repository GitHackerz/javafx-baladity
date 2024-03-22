package org.example.javafxbaladity.interfaces;

import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface IServiceDoc<T>{
    public void ajouter(T t) throws SQLException;
    public void modifier(T t) throws SQLException;
    public void supprimer(int id) throws SQLException;
    public ObservableList<T> afficher() throws SQLException; //T t

}
