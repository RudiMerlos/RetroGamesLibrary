package org.rmc.retrogameslibrary.service;

import java.util.List;
import org.rmc.retrogameslibrary.model.Game;
import org.rmc.retrogameslibrary.model.Platform;
import org.rmc.retrogameslibrary.repository.CrudException;
import org.rmc.retrogameslibrary.repository.CrudRepository;

public interface GameService extends CrudRepository<Game, Long> {

    List<Game> getByTitle(String title) throws CrudException;

    List<Game> getByYear(int year) throws CrudException;

    List<Game> getByGender(String gender) throws CrudException;

    List<Game> getByPlatform(Platform platform) throws CrudException;
}
