package org.rmc.retrogameslibrary.service.hibernate;

import org.hibernate.Session;

public class HibernateService {

    protected Session session = null;

    public HibernateService() {
        session = HibernateConnection.getInstance().getEntityManager();
    }
}
