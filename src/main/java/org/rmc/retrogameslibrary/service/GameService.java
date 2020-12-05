package org.rmc.retrogameslibrary.service;

import java.util.List;
import org.rmc.retrogameslibrary.model.Game;
import org.rmc.retrogameslibrary.model.Platform;
import org.rmc.retrogameslibrary.repository.CrudRepository;

public interface GameService extends CrudRepository<Game, Long> {

    List<Game> getByTitle(String title);

    List<Game> getByYear(int year);

    List<Game> getByGendre(String gendre);

    List<Game> getByPlatform(Platform platform);
}
