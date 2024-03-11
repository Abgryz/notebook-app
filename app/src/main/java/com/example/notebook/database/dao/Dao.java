package com.example.notebook.database.dao;

import java.util.List;

public interface Dao<T> {
    T get(int id);
    List<T> getAll();
    boolean create(T t);
    boolean update(T t);
    boolean delete(int id);
}
