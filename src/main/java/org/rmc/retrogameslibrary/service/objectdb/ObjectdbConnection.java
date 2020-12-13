package org.rmc.retrogameslibrary.service.objectdb;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ObjectdbConnection {

    private static ObjectdbConnection instance = null;

    private EntityManagerFactory emf;
    private EntityManager em;

    private ObjectdbConnection() {
        em = null;
    }

    public static ObjectdbConnection getInstance() {
        if (instance == null)
            instance = new ObjectdbConnection();
        return instance;
    }

    public void connect(String name) {
        emf = Persistence.createEntityManagerFactory("objectdb:db/" + name + ".odb");
        em = emf.createEntityManager();
    }

    public EntityManager getEntityManager() {
        return em;
    }

    public void closeConnection() {
        em.close();
        emf.close();
    }
}
