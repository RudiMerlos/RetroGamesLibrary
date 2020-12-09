package org.rmc.retrogameslibrary.service.hibernate;

import java.util.List;
import javax.persistence.TypedQuery;
import org.rmc.retrogameslibrary.model.Game;
import org.rmc.retrogameslibrary.model.Platform;
import org.rmc.retrogameslibrary.repository.CrudException;
import org.rmc.retrogameslibrary.service.GameService;

public class HibernateGameService extends HibernateService implements GameService {

    public HibernateGameService() {
        super();
    }

    @Override
    public void insert(Game game) throws CrudException {
        // TODO set Platform
        try{
            em.getTransaction().begin();
            em.persist(game);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new CrudException("Error de Hibernate", e);
        }
    }

    @Override
    public void modify(Game game) throws CrudException {
        try {
            em.getTransaction().begin();
            int result = em
                    .createQuery("UPDATE Game g SET g.title, g.description, g.year, g.gender, "
                            + "g.screenshot, g.path WHERE g.id = :id")
                    .setParameter("id", game.getId()).executeUpdate();
            em.getTransaction().commit();
            if (result == 0)
                throw new CrudException("Es probable que no se haya eliminado el registro.");
        } catch (Exception e) {
            throw new CrudException("Error de Hibernate", e);
        }
    }

    @Override
    public void remove(Game game) throws CrudException {
        // TODO set Platform
        try {
            em.getTransaction().begin();
            int result = em.createQuery("DELETE FROM Game g WHERE g.id = :id")
                    .setParameter("id", game.getId()).executeUpdate();
            em.getTransaction().commit();
            if (result == 0)
                throw new CrudException("Es probable que no se haya eliminado el registro.");
        } catch (Exception e) {
            throw new CrudException("Error de Hibernate", e);
        }
    }

    @Override
    public Game getById(Long id) throws CrudException {
        Game game = null;
        try {
            TypedQuery<Game> query = em.createNamedQuery("Game.findById", Game.class);
            query.setParameter("id", id);
            game = query.getSingleResult();
        } catch (Exception e) {
            throw new CrudException("Error de Hibernate", e);
        }
        return game;
    }

    @Override
    public List<Game> getAll() throws CrudException {
        List<Game> games = null;
        try {
            TypedQuery<Game> query = em.createNamedQuery("Game.findAll", Game.class);
            games = query.getResultList();
        } catch (Exception e) {
            throw new CrudException("Error de Hibernate", e);
        }
        return games;
    }

    @Override
    public List<Game> getByTitle(String title) throws CrudException {
        List<Game> games = null;
        try {
            TypedQuery<Game> query = em.createNamedQuery("Game.findByTitle", Game.class);
            query.setParameter("title", title);
            games = query.getResultList();
        } catch (Exception e) {
            throw new CrudException("Error de Hibernate", e);
        }
        return games;
    }

    @Override
    public List<Game> getByYear(int year) throws CrudException {
        List<Game> games = null;
        try {
            TypedQuery<Game> query = em.createNamedQuery("Game.findByYear", Game.class);
            query.setParameter("year", year);
            games = query.getResultList();
        } catch (Exception e) {
            throw new CrudException("Error de Hibernate", e);
        }
        return games;
    }

    @Override
    public List<Game> getByGender(String gender) throws CrudException {
        List<Game> games = null;
        try {
            TypedQuery<Game> query = em.createNamedQuery("Game.findByGender", Game.class);
            query.setParameter("gender", gender);
            games = query.getResultList();
        } catch (Exception e) {
            throw new CrudException("Error de Hibernate", e);
        }
        return games;
    }

    @Override
    public List<Game> getByPlatform(Platform platform) throws CrudException {
        List<Game> games = null;
        try {
            TypedQuery<Game> query = em.createNamedQuery("Game.findByPlatform", Game.class);
            query.setParameter("platform", platform);
            games = query.getResultList();
        } catch (Exception e) {
            throw new CrudException("Error de Hibernate", e);
        }
        return games;
    }
}
