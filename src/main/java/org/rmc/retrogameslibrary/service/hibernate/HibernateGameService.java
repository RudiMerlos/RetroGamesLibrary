package org.rmc.retrogameslibrary.service.hibernate;

import java.util.List;
import javax.persistence.Query;
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
        try{
            session.getTransaction().begin();
            Platform platform = game.getPlatform();
            platform.addGame(game);
            session.save(platform);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new CrudException("Error de Hibernate", e);
        } finally {
            if (session.getTransaction().isActive())
                session.getTransaction().rollback();
        }
    }

    @Override
    public void modify(Game game) throws CrudException {
        try {
            session.getTransaction().begin();
            session.update(game);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new CrudException("Error de Hibernate", e);
        } finally {
            if (session.getTransaction().isActive())
                session.getTransaction().rollback();
        }
    }

    @Override
    public void remove(Game game) throws CrudException {
        try {
            session.getTransaction().begin();
            Platform platform = game.getPlatform();
            platform.removeGame(game);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new CrudException("Error de Hibernate", e);
        } finally {
            if (session.getTransaction().isActive())
                session.getTransaction().rollback();
        }
    }

    @Override
    public void removeAll() throws CrudException {
        try {
            session.getTransaction().begin();
            Query query = session.createQuery("DELETE FROM Game");
            query.executeUpdate();
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
    public Game getById(Long id) throws CrudException {
        Game game = null;
        try {
            game = session.find(Game.class, id);
        } catch (Exception e) {
            throw new CrudException("Error de Hibernate", e);
        }
        return game;
    }

    @Override
    public List<Game> getAll() throws CrudException {
        List<Game> games = null;
        try {
            TypedQuery<Game> query = session.createNamedQuery("Game.findAll", Game.class);
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
            TypedQuery<Game> query = session.createNamedQuery("Game.findByTitle", Game.class);
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
            TypedQuery<Game> query = session.createNamedQuery("Game.findByYear", Game.class);
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
            TypedQuery<Game> query = session.createNamedQuery("Game.findByGender", Game.class);
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
            TypedQuery<Game> query = session.createNamedQuery("Game.findByPlatform", Game.class);
            query.setParameter("platform", platform);
            games = query.getResultList();
        } catch (Exception e) {
            throw new CrudException("Error de Hibernate", e);
        }
        return games;
    }

    public List<Game> searchByTitle(String title) throws CrudException {
        List<Game> games = null;
        try {
            TypedQuery<Game> query = session.createNamedQuery("Game.searchByTitle", Game.class);
            query.setParameter("title", title);
            games = query.getResultList();
        } catch (Exception e) {
            throw new CrudException("Error de Hibernate", e);
        }
        return games;
    }
}
