package nl.hsleiden;

import java.sql.*;

/**
 * @author Fleur van Eijk
 */
public class DatabaseConnection {

    // Dit zijn de credentials om toegang te krijgen naar de database
    private final String url = "jdbc:postgresql://localhost/webshop";
    private final String username = "postgres";
    private final String password = "postgres";
    private static Connection connection = null;
    PreparedStatement statement = null;
    ResultSet rs = null;

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
        connection = DriverManager.getConnection(url, username, password);
        return connection;
    }

    private boolean hasConnection(){
        if (connection != null) {
            return true;
        }
        else {
            return false;
        }
    }

    public void disconnect(){
        try{
            if(statement != null){
                statement.close();
            }
            if(rs != null){
                rs.close();
            }
            if(connection != null){
                connection.close();
            }
        }catch (SQLException sqlEx)
        {
            sqlEx.printStackTrace();
            System.out.println("Couldn't disconnect from the database");
        }
    }
}
