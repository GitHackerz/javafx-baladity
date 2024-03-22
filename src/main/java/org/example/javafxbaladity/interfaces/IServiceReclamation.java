package org.example.javafxbaladity.interfaces;

import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface IServiceReclamation<T>{
    void ajouter(T t) throws SQLException;
    void modifier(T t) throws SQLException;
    void supprimer(int id) throws SQLException;
    ObservableList<T> recuperer() throws SQLException;

}
