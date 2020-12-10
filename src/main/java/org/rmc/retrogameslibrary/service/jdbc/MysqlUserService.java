package org.rmc.retrogameslibrary.service.jdbc;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
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
    private final String GETBYLASTNAME = GETALL + " WHERE last_name = ?";

    private Connection connection;
    private PreparedStatement st;
    private ResultSet rs;

    public MysqlUserService() {
        connection = MysqlConnection.getInstance().getConnection();
        st = null;
        rs = null;
    }

    // Creates retrogamesusers table
    public void createTable() throws CrudException {
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
    public void insert(User user) throws CrudException {
        try {
            st = connection.prepareStatement(INSERT);
            st.setString(1, user.getEmail());
            st.setString(2, user.getPassword());
            st.setString(3, user.getFirstName());
            st.setString(4, user.getLastName());
            if (user.getBirthdate() == null)
                st.setDate(5, null);
            else
                st.setDate(5, Date.valueOf(user.getBirthdate()));
            if (st.executeUpdate() == 0)
                throw new CrudException("Es probable que no se haya insertado el registro.");
        } catch (SQLException e) {
            throw new CrudException("Error en MySQL", e);
        } finally {
            close(st);
        }
    }

    @Override
    public void modify(User user) throws CrudException {
        try {
            st = connection.prepareStatement(UPDATE);
            st.setString(1, user.getPassword());
            st.setString(2, user.getFirstName());
            st.setString(3, user.getLastName());
            if (user.getBirthdate() == null)
                st.setDate(4, null);
            else
                st.setDate(4, Date.valueOf(user.getBirthdate()));
            st.setString(5, user.getEmail());
            if (st.executeUpdate() == 0)
                throw new CrudException("Es probable que no se haya modificado el registro.");
        } catch (SQLException e) {
            throw new CrudException("Error en MySQL", e);
        } finally {
            close(st);
        }
    }

    @Override
    public void remove(User user) throws CrudException {
        try {
            st = connection.prepareStatement(DELETE);
            st.setString(1, user.getEmail());
            if (st.executeUpdate() == 0)
                throw new CrudException("Es probable que no se haya eliminado el registro.");
        } catch (SQLException e) {
            throw new CrudException("Error en MySQL", e);
        } finally {
            close(st);
        }
    }

    private User resultSetToUser(ResultSet rs) throws SQLException {
        String email = rs.getString("email");
        String password = rs.getString("passw");
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        LocalDate birthdate = null;
        if (rs.getDate("birthdate") != null)
            birthdate = rs.getDate("birthdate").toLocalDate();
        return new User(email, password, firstName, lastName, birthdate);
    }

    @Override
    public User getById(String id) throws CrudException {
        User user = null;
        try {
            st = connection.prepareStatement(GETBYID);
            st.setString(1, id);
            rs = st.executeQuery();
            if (rs.next())
                user = resultSetToUser(rs);
        } catch (SQLException e) {
            throw new CrudException("Error en MySQL", e);
        } finally {
            close(st, rs);
        }
        return user;
    }

    @Override
    public List<User> getAll() throws CrudException {
        List<User> users = new ArrayList<>();
        try {
            st = connection.prepareStatement(GETALL);
            rs = st.executeQuery();
            while (rs.next())
                users.add(resultSetToUser(rs));
        } catch (SQLException e) {
            throw new CrudException("Error en MySQL", e);
        } finally {
            close(st, rs);
        }
        return users;
    }

    @Override
    public List<User> getByFirstName(String firstName) throws CrudException {
        List<User> users = new ArrayList<>();
        try {
            st = connection.prepareStatement(GETBYFIRSTNAME);
            st.setString(1, firstName);
            rs = st.executeQuery();
            while (rs.next())
                users.add(resultSetToUser(rs));
        } catch (SQLException e) {
            throw new CrudException("Error en MySQL", e);
        } finally {
            close(st, rs);
        }
        return users;
    }

    @Override
    public List<User> getByLastName(String lastName) throws CrudException {
        List<User> users = new ArrayList<>();
        try {
            st = connection.prepareStatement(GETBYLASTNAME);
            st.setString(1, lastName);
            rs = st.executeQuery();
            while (rs.next())
                users.add(resultSetToUser(rs));
        } catch (SQLException e) {
            throw new CrudException("Error en MySQL", e);
        } finally {
            close(st, rs);
        }
        return users;
    }
}
