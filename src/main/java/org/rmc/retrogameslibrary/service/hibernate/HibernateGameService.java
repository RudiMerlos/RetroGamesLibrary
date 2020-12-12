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
            em.getTransaction().begin();
            Platform platform = game.getPlatform();
            platform.addGame(game);
            em.persist(platform);
        } catch (Exception e) {
            throw new CrudException("Error de Hibernate", e);
        } finally {
            if (em.getTransaction().isActive())
                em.getTransaction().commit();
        }
    }

    @Override
    public void modify(Game game) throws CrudException {
        try {
            em.getTransaction().begin();
            Query query = em.createQuery("UPDATE Game g SET g.title = :title, "
                + "g.description = :description, g.year = :year, g.gender = :gender, "
                + "g.screenshot = :screenshot, g.path = :path, g.command = :command "
                + "WHERE g.id = :id");
            query.setParameter("id", game.getId());
            query.setParameter("title", game.getTitle());
            query.setParameter("description", game.getDescription());
            query.setParameter("year", game.getYear());
            query.setParameter("gender", game.getGender());
            query.setParameter("screenshot", game.getScreenshot());
            query.setParameter("path", game.getPath());
            query.setParameter("command", game.getCommand());
            if (query.executeUpdate() == 0)
                throw new CrudException("Es probable que no se haya eliminado el registro.");
        } catch (Exception e) {
            throw new CrudException("Error de Hibernate", e);
        } finally {
            if (em.getTransaction().isActive())
                em.getTransaction().commit();
        }
    }

    @Override
    public void remove(Game game) throws CrudException {
        try {
            em.getTransaction().begin();
            Platform platform = game.getPlatform();
            platform.removeGame(game);
        } catch (Exception e) {
            throw new CrudException("Error de Hibernate", e);
        } finally {
            if (em.getTransaction().isActive())
                em.getTransaction().commit();
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

    public List<Game> searchByTitle(String title) throws CrudException {
        List<Game> games = null;
        try {
            TypedQuery<Game> query = em.createNamedQuery("Game.searchByTitle", Game.class);
            query.setParameter("title", title);
            games = query.getResultList();
        } catch (Exception e) {
            throw new CrudException("Error de Hibernate", e);
        }
        return games;
    }
}
