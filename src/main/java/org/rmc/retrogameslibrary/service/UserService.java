package org.rmc.retrogameslibrary.service;

import java.util.List;
import org.rmc.retrogameslibrary.model.User;
import org.rmc.retrogameslibrary.repository.CrudRepository;

public interface UserService extends CrudRepository<User, Long> {

    List<User> getByFirstName(String firstName);

    List<User> getByLastName(String lastName);
}
