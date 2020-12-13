package org.rmc.retrogameslibrary.service.hibernate;

import java.util.List;
import javax.persistence.TypedQuery;
import org.rmc.retrogameslibrary.model.Platform;
import org.rmc.retrogameslibrary.repository.CrudException;
import org.rmc.retrogameslibrary.service.PlatformService;

public class HibernatePlatformService extends HibernateService implements PlatformService {

    public HibernatePlatformService() {
        super();
    }

    @Override
    public void insert(Platform platform) throws CrudException {
        try {
            session.getTransaction().begin();
            session.save(platform);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new CrudException("Error de Hibernate", e);
        } finally {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
        }
    }

    @Override
    public void modify(Platform platform) throws CrudException {
        try {
            session.getTransaction().begin();
            session.update(platform);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new CrudException("Error de Hibernate", e);
        } finally {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
        }
    }

    @Override
    public void remove(Platform platform) throws CrudException {
        try {
            session.getTransaction().begin();
            session.remove(session.find(Platform.class, platform.getId()));
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new CrudException("Error de Hibernate", e);
        } finally {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
        }
    }

    @Override
    public Platform getById(Long id) throws CrudException {
        Platform platform = null;
        try {
            platform = session.find(Platform.class, id);
        } catch (Exception e) {
            throw new CrudException("Error de Hibernate", e);
        }
        return platform;
    }

    @Override
    public List<Platform> getAll() throws CrudException {
        List<Platform> platforms = null;
        try {
            TypedQuery<Platform> query = session.createNamedQuery("Platform.findAll", Platform.class);
            platforms = query.getResultList();
        } catch (Exception e) {
            throw new CrudException("Error de Hibernate", e);
        }
        return platforms;
    }

    @Override
    public List<Platform> getByName(String name) throws CrudException {
        List<Platform> platforms = null;
        try {
            TypedQuery<Platform> query = session.createNamedQuery("Platform.findByName", Platform.class);
            query.setParameter("name", name);
            platforms = query.getResultList();
        } catch (Exception e) {
            throw new CrudException("Error de Hibernate", e);
        }
        return platforms;
    }

    @Override
    public List<Platform> getByModel(String model) throws CrudException {
        List<Platform> platforms = null;
        try {
            TypedQuery<Platform> query =
                    session.createNamedQuery("Platform.findByModel", Platform.class);
            query.setParameter("model", model);
            platforms = query.getResultList();
        } catch (Exception e) {
            throw new CrudException("Error de Hibernate", e);
        }
        return platforms;
    }

    @Override
    public List<Platform> getByCompany(String company) throws CrudException {
        List<Platform> platforms = null;
        try {
            TypedQuery<Platform> query =
                    session.createNamedQuery("Platform.findByCompany", Platform.class);
            query.setParameter("company", company);
            platforms = query.getResultList();
        } catch (Exception e) {
            throw new CrudException("Error de Hibernate", e);
        }
        return platforms;
    }

    @Override
    public List<Platform> getByYear(int year) throws CrudException {
        List<Platform> platforms = null;
        try {
            TypedQuery<Platform> query = session.createNamedQuery("Platform.findByYear", Platform.class);
            query.setParameter("year", year);
            platforms = query.getResultList();
        } catch (Exception e) {
            throw new CrudException("Error de Hibernate", e);
        }
        return platforms;
    }

    @Override
    public Platform getByNameAndModel(String name, String model) throws CrudException {
        Platform platform = null;
        try {
            TypedQuery<Platform> query =
                    session.createNamedQuery("Platform.findByNameAndModel", Platform.class);
            query.setParameter("name", name);
            query.setParameter("model", model);
            platform = query.getSingleResult();
        } catch (Exception e) {
            throw new CrudException("Error de Hibernate", e);
        }
        return platform;
    }
}
