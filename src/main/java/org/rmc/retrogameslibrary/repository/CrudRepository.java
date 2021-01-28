package org.rmc.retrogameslibrary.repository;

import java.util.List;

public interface CrudRepository<T, K> {

    void insert(T pT) throws CrudException;

    void modify(T pT) throws CrudException;

    void remove(T pT) throws CrudException;

    void removeAll() throws CrudException;

    T getById(K id) throws CrudException;

    List<T> getAll() throws CrudException;
}
