package org.rmc.retrogameslibrary.service;

import java.util.List;
import org.rmc.retrogameslibrary.model.Emulator;
import org.rmc.retrogameslibrary.repository.CrudException;
import org.rmc.retrogameslibrary.repository.CrudRepository;

public interface EmulatorService extends CrudRepository<Emulator, Long> {

    List<Emulator> getByName(String name) throws CrudException;
}
