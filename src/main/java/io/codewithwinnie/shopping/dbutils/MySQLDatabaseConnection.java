package io.codewithwinnie.shopping.dbutils;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.h2.tools.RunScript;
import org.h2.tools.Server;

/*
 * Gets a connection to the database
 * Loads a database driver class and gets connection using url, username and password
 * */
public class MySQLDatabaseConnection {
    private static final Logger logger = Logger.getLogger(MySQLDatabaseConnection.class.getName());

//    static {
//        try {
//            initializeDatabase(getConnectionToDatabase());
//        } catch (FileNotFoundException exception) {
//            logger.log(Level.SEVERE, "Could not find the .sql file", exception);
//        } catch (SQLException exception) {
//            logger.log(Level.SEVERE, "SQL error", exception);
//        }
//    }

    static Server server;


    public static Connection getConnectionToDatabase() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/shoppingdb", "root", "123456");

        } catch (Exception exception) {
            logger.log(Level.SEVERE, "Could not set up connection", exception);
        }
        logger.info("Connection set up completed");
        return connection;
    }

    /*
     * Starts the database TCP server in case one needs to access it using a 3rd party(external) client
     *
     * */
    public static void startDatabase() throws SQLException {
        server = Server.createTcpServer().start();
    }

    /*
     * Stops the database server
     *
     * */
    public static void stopDatabase() {
        server.stop();
    }

    /* Loads the initialize.sql file from the classpath folder "resources".
    Runs all the mysql queries from the file to create tables, insert records and make it ready to use
    **/
    public static void initializeDatabase(Connection connection) throws FileNotFoundException, SQLException {
        InputStream inputStream = MySQLDatabaseConnection.class.getClassLoader().getResourceAsStream("initialize.sql");
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        RunScript.execute(connection, inputStreamReader);
    }


}
