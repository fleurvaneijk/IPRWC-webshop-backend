package nl.hsleiden.persistence;

import java.sql.*;

public class DatabaseConnection {

    // Dit zijn de credentials om toegang te krijgen naar de database
    private final String url = "jdbc:postgresql://localhost/webshop";
    private final String user = "postgres";
    private final String pass = "postgres";
    private static Connection conn = null;
    PreparedStatement statement = null;
    ResultSet rs = null;

    //Constructor
    public DatabaseConnection() {
    }

    public void initializeConnection(){
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        try {
            conn = DriverManager.getConnection(this.url, this.user, this.pass);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (conn != null) {
            System.out.println("Database is connected.");
        } else {
            System.out.println("Couldn't connect with the database.");
        }
    }

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, pass);
    }

    private boolean hasConnection(){
        if (conn != null) {
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
            if(conn != null){
                conn.close();
            }
        }catch (SQLException sqlEx)
        {
            sqlEx.printStackTrace();
            System.out.println("Couldn't disconnect from the database");
        }
    }
}
