package org.example.javafxbaladity.interfaces;

import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public interface IService<T> {
    public void create(T t) throws SQLException;
    public T read(int id) throws SQLException;
    public void update(T t) throws SQLException;
    public void delete(int id) throws SQLException;
    public ObservableList<T> readAll() throws SQLException;
}
