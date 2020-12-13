package org.rmc.retrogameslibrary.service.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateConnection {

    private static HibernateConnection instance = null;

    private SessionFactory sf;
    private Session session;

    private HibernateConnection() {
        sf = null;
        session = null;
    }

    public static HibernateConnection getInstance() {
        if (instance == null)
            instance = new HibernateConnection();
        return instance;
    }

    public void connect() {
        StandardServiceRegistry sr = new StandardServiceRegistryBuilder().configure().build();
        sf = new MetadataSources(sr).buildMetadata().buildSessionFactory();
        session = sf.openSession();
    }

    public Session getEntityManager() {
        return session;
    }

    public void closeConnection() {
        session.close();
        sf.close();
    }
}
