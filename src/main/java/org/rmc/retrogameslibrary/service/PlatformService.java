package org.rmc.retrogameslibrary.service;

import java.util.List;
import org.rmc.retrogameslibrary.model.Platform;
import org.rmc.retrogameslibrary.repository.CrudException;
import org.rmc.retrogameslibrary.repository.CrudRepository;

public interface PlatformService extends CrudRepository<Platform, Long> {

    List<Platform> getByName(String name) throws CrudException;

    List<Platform> getByModel(String model) throws CrudException;

    List<Platform> getByCompany(String company) throws CrudException;

    List<Platform> getByYear(int year) throws CrudException;

    Platform getByNameAndModel(String name, String model) throws CrudException;
}
