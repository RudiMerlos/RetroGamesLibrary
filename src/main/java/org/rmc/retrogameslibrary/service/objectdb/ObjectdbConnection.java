package org.rmc.retrogameslibrary.service.objectdb;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

// Singleton class
public class ObjectdbConnection {

    private static ObjectdbConnection instance = null;

    private EntityManagerFactory emf;
    private EntityManager em;

    private ObjectdbConnection() {
        emf = null;
        em = null;
    }

    // Return an ObjectdbConnection instance
    public static ObjectdbConnection getInstance() {
        if (instance == null)
            instance = new ObjectdbConnection();
        return instance;
    }

    // Set ObjectDB connection
    public void connect(String name) {
        emf = Persistence.createEntityManagerFactory("objectdb:db/" + name + ".odb");
        em = emf.createEntityManager();
    }

    // Return the EntityManager
    public EntityManager getEntityManager() {
        return em;
    }

    // Close resources
    public void closeConnection() {
        em.close();
        emf.close();
    }
}
