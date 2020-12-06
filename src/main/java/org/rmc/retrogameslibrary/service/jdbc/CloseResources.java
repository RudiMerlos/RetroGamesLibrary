package org.rmc.retrogameslibrary.service.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.rmc.retrogameslibrary.repository.CrudException;

public interface CloseResources {

    default void close(Statement st, ResultSet rs) throws CrudException {
        if (rs != null) {
            try {
                rs.close();
                rs = null;
            } catch (SQLException e) {
                throw new CrudException("Error de MySQL", e);
            }
        }
        if (st != null) {
            try {
                st.close();
                st = null;
            } catch (SQLException e) {
                throw new CrudException("Error de MySQL", e);
            }
        }
    }
}
