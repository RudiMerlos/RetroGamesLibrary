package org.rmc.retrogameslibrary.service.hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateConnection {

    private static HibernateConnection instance = null;

    private EntityManagerFactory emf;
    private EntityManager em;

    private HibernateConnection() {
        em = null;
    }

    public static HibernateConnection getInstance() {
        if (instance == null)
            instance = new HibernateConnection();
        return instance;
    }

    public void connect(String name) {
        emf = Persistence.createEntityManagerFactory(name);
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
