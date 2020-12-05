package org.rmc.retrogameslibrary.repository;

import java.util.List;

public interface CrudRepository<T, K> {

    T insert(T pT) throws CrudException;

    T modify(T pT) throws CrudException;

    void remove(T pT) throws CrudException;

    T getById(K id) throws CrudException;

    List<T> getAll() throws CrudException;
}
