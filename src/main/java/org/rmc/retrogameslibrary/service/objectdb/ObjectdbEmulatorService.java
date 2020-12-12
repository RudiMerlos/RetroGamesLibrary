package org.rmc.retrogameslibrary.service.objectdb;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.rmc.retrogameslibrary.model.Emulator;
import org.rmc.retrogameslibrary.repository.CrudException;
import org.rmc.retrogameslibrary.service.EmulatorService;

public class ObjectdbEmulatorService implements EmulatorService {

    private EntityManager em;

    public ObjectdbEmulatorService() {
        em = ObjectdbConnection.getInstance().getEntityManager();
    }

    @Override
    public void insert(Emulator emulator) throws CrudException {
        try {
            em.getTransaction().begin();
            em.persist(emulator);
        } catch (Exception e) {
            throw new CrudException("Error de ObjectDB", e);
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().commit();
            }
        }
    }

    @Override
    public void modify(Emulator emulator) throws CrudException {
        try {
            em.getTransaction().begin();
            Query query = em.createQuery(
                    "UPDATE Emulator e SET e.name = :name, e.path = :path WHERE e.id = :id");
            query.setParameter("id", emulator.getId());
            query.setParameter("name", emulator.getName());
            query.setParameter("path", emulator.getPath());
            if (query.executeUpdate() == 0)
                throw new CrudException("Es probable que no se haya actuaizado el registro.");
        } catch (Exception e) {
            throw new CrudException("Error de ObjectDB", e);
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().commit();
            }
        }
    }

    @Override
    public void remove(Emulator emulator) throws CrudException {
        try {
            em.getTransaction().begin();
            em.remove(em.find(Emulator.class, emulator.getId()));
        } catch (Exception e) {
            throw new CrudException("Error de ObjectDB", e);
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().commit();
            }
        }
    }

    @Override
    public Emulator getById(Long id) throws CrudException {
        Emulator emulator = null;
        try {
            emulator = em.find(Emulator.class, id);
        } catch (Exception e) {
            throw new CrudException("Error de ObjectDB", e);
        }
        return emulator;
    }

    @Override
    public List<Emulator> getAll() throws CrudException {
        List<Emulator> emulators = null;
        try {
            TypedQuery<Emulator> query = em.createNamedQuery("Platform.findAll", Emulator.class);
            emulators = query.getResultList();
        } catch (Exception e) {
            throw new CrudException("Error de ObjectDB", e);
        }
        return emulators;
    }

    @Override
    public List<Emulator> getByName(String name) throws CrudException {
        List<Emulator> emulator = null;
        try {
            TypedQuery<Emulator> query = em.createNamedQuery("Emulator.findByName", Emulator.class);
            query.setParameter("name", name);
            emulator = query.getResultList();
        } catch (Exception e) {
            throw new CrudException("Error de ObjectDB", e);
        }
        return emulator;
    }
}
