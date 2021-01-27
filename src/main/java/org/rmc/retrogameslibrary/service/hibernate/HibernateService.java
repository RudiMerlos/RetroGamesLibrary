package org.rmc.retrogameslibrary.service.hibernate;

import org.hibernate.Session;

public class HibernateService {

    protected Session session = null;

    protected HibernateService() {
        session = HibernateConnection.getInstance().getEntityManager();
    }
}
