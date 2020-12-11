package org.rmc.retrogameslibrary.service.hibernate;

import java.util.List;
import javax.persistence.Query;
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
            em.getTransaction().begin();
            em.persist(platform);
        } catch (Exception e) {
            throw new CrudException("Error de Hibernate", e);
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().commit();
            }
        }
    }

    @Override
    public void modify(Platform platform) throws CrudException {
        try {
            em.getTransaction().begin();
            Query query = em.createQuery("UPDATE Platform p SET p.name = :name, p.model = :model, "
                    + "p.company = :company, p.country = :country, p.year = :year WHERE p.id = :id");
            query.setParameter("id", platform.getId());
            query.setParameter("name", platform.getName());
            query.setParameter("model", platform.getModel());
            query.setParameter("company", platform.getCompany());
            query.setParameter("country", platform.getCountry());
            query.setParameter("year", platform.getYear());
            if (query.executeUpdate() == 0)
                throw new CrudException("Es probable que no se haya eliminado el registro.");
        } catch (Exception e) {
            throw new CrudException("Error de Hibernate", e);
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().commit();
            }
        }
    }

    @Override
    public void remove(Platform platform) throws CrudException {
        try {
            em.getTransaction().begin();
            int result = em.createQuery("DELETE FROM Platform p WHERE p.id = :id")
                    .setParameter("id", platform.getId()).executeUpdate();
            if (result == 0)
                throw new CrudException("Es probable que no se haya eliminado el registro.");
        } catch (Exception e) {
            throw new CrudException("Error de Hibernate", e);
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().commit();
            }
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
            TypedQuery<Platform> query =
                    em.createNamedQuery("Platform.findByModel", Platform.class);
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
                    em.createNamedQuery("Platform.findByCompany", Platform.class);
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

    @Override
    public Platform getByNameAndModel(String name, String model) throws CrudException {
        Platform platform = null;
        try {
            TypedQuery<Platform> query =
                    em.createNamedQuery("Game.findByNameAndModel", Platform.class);
            query.setParameter("name", name);
            query.setParameter("model", model);
            platform = query.getSingleResult();
        } catch (Exception e) {
            throw new CrudException("Error de Hibernate", e);
        }
        return platform;
    }
}
