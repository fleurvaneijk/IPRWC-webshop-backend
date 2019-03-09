package nl.hsleiden.database;

import java.sql.*;

/**
 * @author Fleur van Eijk
 */
public class DatabaseConnection {

    // Credentials to access database
    private final String url = "jdbc:postgresql://localhost/webshop";
    private final String username = "postgres";
    private final String password = "postgres";
    private static Connection connection = null;

    //Constructor
    public DatabaseConnection() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        try {
            connection = DriverManager.getConnection(this.url, this.username, this.password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (connection != null) {
            System.out.println("Database is connected.");
        } else {
            System.out.println("Couldn't connect with the database.");
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}
