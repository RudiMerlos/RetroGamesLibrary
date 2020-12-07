package org.rmc.retrogameslibrary.service;

import java.util.List;
import org.rmc.retrogameslibrary.model.User;
import org.rmc.retrogameslibrary.repository.CrudException;
import org.rmc.retrogameslibrary.repository.CrudRepository;

public interface UserService extends CrudRepository<User, String> {

    List<User> getByFirstName(String firstName) throws CrudException;

    List<User> getByLastName(String lastName) throws CrudException;
}
