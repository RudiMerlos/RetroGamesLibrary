package org.rmc.retrogameslibrary.service;

import java.util.List;
import org.rmc.retrogameslibrary.model.Platform;
import org.rmc.retrogameslibrary.repository.CrudRepository;

public interface PlatformService extends CrudRepository<Platform, Long> {

    List<Platform> getByName(String name);

    List<Platform> getByModel(String model);

    List<Platform> getByCompany(String company);

    List<Platform> getByYear(int year);
}
