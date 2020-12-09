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
        try{
            em.getTransaction().begin();
            em.persist(platform);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new CrudException("Error de Hibernate", e);
        }
    }

    @Override
    public void modify(Platform platform) throws CrudException {
        try {
            em.getTransaction().begin();
            int result = em
                    .createQuery("UPDATE Game g SET g.title, g.description, g.year, g.gender, "
                            + "g.screenshot, g.path WHERE g.id = :id")
                    .setParameter("id", platform.getId()).executeUpdate();
            em.getTransaction().commit();
            if (result == 0)
                throw new CrudException("Es probable que no se haya eliminado el registro.");
        } catch (Exception e) {
            throw new CrudException("Error de Hibernate", e);
        }
    }

    @Override
    public void remove(Platform platform) throws CrudException {
        try {
            em.getTransaction().begin();
            int result = em.createQuery("DELETE FROM Game g WHERE g.id = :id")
                    .setParameter("id", platform.getId()).executeUpdate();
            em.getTransaction().commit();
            if (result == 0)
                throw new CrudException("Es probable que no se haya eliminado el registro.");
        } catch (Exception e) {
            throw new CrudException("Error de Hibernate", e);
        }
    }

    @Override
    public Platform getById(Long id) throws CrudException {
        Platform platform = null;
        try {
            TypedQuery<Platform> query = em.createNamedQuery("Game.findById", Platform.class);
            query.setParameter("id", id);
            platform = query.getSingleResult();
        } catch (Exception e) {
            throw new CrudException("Error de Hibernate", e);
        }
        return platform;
    }

    @Override
    public List<Platform> getAll() throws CrudException {
        List<Platform> platforms = null;
        try {
            TypedQuery<Platform> query = em.createNamedQuery("Platform.findAll", Platform.class);
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
            TypedQuery<Platform> query = em.createNamedQuery("Platform.findByName", Platform.class);
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
            TypedQuery<Platform> query = em.createNamedQuery("Platform.findByModel", Platform.class);
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
            TypedQuery<Platform> query = em.createNamedQuery("Platform.findByCompany", Platform.class);
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
            TypedQuery<Platform> query = em.createNamedQuery("Platform.findByYear", Platform.class);
            query.setParameter("year", year);
            platforms = query.getResultList();
        } catch (Exception e) {
            throw new CrudException("Error de Hibernate", e);
        }
        return platforms;
    }
}
