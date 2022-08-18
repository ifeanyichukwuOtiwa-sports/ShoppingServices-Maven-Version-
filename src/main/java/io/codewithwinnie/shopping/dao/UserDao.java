package io.codewithwinnie.shopping.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import io.codewithwinnie.shopping.dbutils.H2DatabaseConnection;
import io.codewithwinnie.shopping.dbutils.MySQLDatabaseConnection;
import io.codewithwinnie.shopping.domain.User;

/**
 * Created by @author Ifeanyichukwu Otiwa
 * 17/08/2022
 */

public class UserDao {
    private static final Logger LOGGER = Logger.getLogger(UserDao.class.getName());

    public User getUserDetails(final String username) {
        LOGGER.log(Level.INFO, String.format("Getting user details for username: %s", username));
        User user = new User();

        try {
            Connection con = MySQLDatabaseConnection.getConnectionToDatabase();
            ResultSet rs;
            try (PreparedStatement statement = con.prepareStatement("select * from user where username=?")) {
                statement.setString(1, username);
                rs = statement.executeQuery();

                while (rs.next()) {
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setName(rs.getString("name"));
                    user.setAge(rs.getInt("age"));
                    user.setGender(rs.getString("gender"));
                }
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Could not execute Query", e);
        }

        LOGGER.log(Level.INFO, String.format("User details for username: %s is %s", username, user));
        return user;
    }
}
