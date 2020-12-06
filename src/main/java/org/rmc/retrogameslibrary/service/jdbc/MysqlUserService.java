package org.rmc.retrogameslibrary.service.jdbc;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.rmc.retrogameslibrary.model.User;
import org.rmc.retrogameslibrary.repository.CrudException;
import org.rmc.retrogameslibrary.service.UserService;

public class MysqlUserService implements UserService, CloseResources {

    private final String INSERT = "INSERT INTO user VALUES (?, ?, ?, ?, ?)";
    private final String UPDATE = "UPDATE user SET passw = ?, first_name = ?, last_name = ?, "
            + "birthdate = ? WHERE email = ?";
    private final String DELETE = "DELETE FROM user WHERE email = ?";
    private final String GETALL = "SELECT email, passw, first_name, last_name, birthdate FROM user";
    private final String GETBYID = GETALL + " WHERE email = ?";
    private final String GETBYFIRSTNAME = GETALL + " WHERE first_name = ?";
    private final String GETBYLASTNAME = GETALL + " WHERE lastname = ?";

    private PreparedStatement st;
    private ResultSet rs;

    public MysqlUserService() {
        st = null;
        rs = null;
    }

    // Creates retrogamesusers table
    public void createTable(Connection connection) throws CrudException {
        String query = null;
        try {
            query = createQuery();
        } catch (IOException e) {
            throw new CrudException("Error al leer el script de creación de tabla de usuarios.", e);
        }

        Statement st = null;
        try {
            st = connection.createStatement();
            if (st.executeUpdate(query) != 0) {
                throw new CrudException(
                        "Es posible que no se haya creado la tabla de usuarios correctamente.");
            }
        } catch (SQLException e) {
            throw new CrudException("Error en MySQL.", e);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    throw new CrudException("Error en MySQL.", e);
                }
            }
        }
    }

    // Reads sql script
    private String createQuery() throws IOException {
        BufferedReader reader =
                Files.newBufferedReader(Paths.get(getClass().getResource("/users.sql").getPath()));
        String line = null;
        String newLine = System.getProperty("line.separator");
        StringBuilder query = new StringBuilder();

        while ((line = reader.readLine()) != null) {
            query.append(line);
            query.append(newLine);
        }
        return query.toString();
    }

    @Override
    public User insert(User user) throws CrudException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public User modify(User user) throws CrudException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void remove(User user) throws CrudException {
        // TODO Auto-generated method stub

    }

    @Override
    public User getById(Long id) throws CrudException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<User> getAll() throws CrudException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<User> getByFirstName(String firstName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<User> getByLastName(String lastName) {
        // TODO Auto-generated method stub
        return null;
    }
}
