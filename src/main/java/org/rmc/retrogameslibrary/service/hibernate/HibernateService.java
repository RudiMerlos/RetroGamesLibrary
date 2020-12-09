package org.rmc.retrogameslibrary.service.hibernate;

import javax.persistence.EntityManager;

public class HibernateService {

    protected EntityManager em = null;

    public HibernateService() {
        em = HibernateConnection.getInstance().getEntityManager();
    }
}
