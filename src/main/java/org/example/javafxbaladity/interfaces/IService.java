package org.example.javafxbaladity.interfaces;

import java.util.List;

public interface IService<T> {
    public void create(T t) throws Exception;
    public T read(int id) throws Exception;
    public void update(T t) throws Exception;
    public void delete(int id) throws Exception;
    public List<T> readAll() throws Exception;
}
