package org.rmc.retrogameslibrary.service.hibernate;

import java.util.List;
import javax.persistence.EntityManager;
import org.rmc.retrogameslibrary.dialog.AppDialog;
import org.rmc.retrogameslibrary.model.Game;
import org.rmc.retrogameslibrary.model.Platform;
import org.rmc.retrogameslibrary.repository.CrudException;
import org.rmc.retrogameslibrary.service.GameService;

public class HibernateGameService implements GameService {

    private EntityManager em = null;

    public HibernateGameService() {
        em = HibernateConnection.getInstance().getEntityManager();
    }

    @Override
    public void insert(Game game) throws CrudException {
        em.getTransaction().begin();
        em.persist(game);
        em.getTransaction().commit();
    }

    @Override
    public void modify(Game game) throws CrudException {
        int result = em
                .createQuery("UPDATE Game g SET g.title, g.description, g.year, g.gender, "
                        + "g.screenshot, g.path WHERE g.id = :id")
                .setParameter("id", game.getId()).executeUpdate();
        AppDialog.messageDialog("Modificación de juego",
                result == 0 ? "No se ha modificado ningún juego." : "Juego modificado con éxito.");
    }

    @Override
    public void remove(Game game) throws CrudException {
        int result = em.createQuery("DELETE FROM Game g WHERE g.id = :id")
                .setParameter("id", game.getId()).executeUpdate();
        AppDialog.messageDialog("Modificación de juego",
                result == 0 ? "No se ha eliminado ningún juego." : "Juego eliminado con éxito.");
    }

    @Override
    public Game getById(Long id) throws CrudException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Game> getAll() throws CrudException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Game> getByTitle(String title) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Game> getByYear(int year) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Game> getByGendre(String gendre) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Game> getByPlatform(Platform platform) {
        // TODO Auto-generated method stub
        return null;
    }
}
