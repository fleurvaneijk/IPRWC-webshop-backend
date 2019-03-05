package nl.hsleiden.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import javax.inject.Singleton;

import nl.hsleiden.DatabaseConnection;
import nl.hsleiden.model.User;
import nl.hsleiden.model.DatabaseInfo;
import org.postgresql.util.PSQLException;

/**
 * @author Fleur van Eijk
 */
@Singleton
public class UserDAO {

    private final DatabaseConnection database;
    private final ArrayList<User> users;
    private ResultSet resultSet = null;
    private Connection connection;

    public UserDAO(){
        this.database = new DatabaseConnection();
        this.users = new ArrayList<>();
    }
    
    public ArrayList<User> getAll() {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection connection = null;
        try{
            connection = database.getConnection();
            String query = "SELECT * FROM " + DatabaseInfo.userTable;
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while(resultSet.next()){
                User user = new User(resultSet.getString(DatabaseInfo.userColumn.email), resultSet.getString(DatabaseInfo.userColumn.name),
                        resultSet.getString(DatabaseInfo.userColumn.password), resultSet.getString(DatabaseInfo.userColumn.role));

                System.out.println(user.getName());

                users.add(user);
            }
        }catch(SQLException sqle){
            sqle.printStackTrace();
        }
        finally {
            try{
                Objects.requireNonNull(statement).close();
                Objects.requireNonNull(resultSet).close();
                connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return users;
    }
    
    public User getUser(String email) {
        User user = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection connection = null;
        try{
            connection = database.getConnection();
            String query = "SELECT * FROM " + DatabaseInfo.userTable + " WHERE  " + DatabaseInfo.userColumn.email + " = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, email);
            resultSet = statement.executeQuery();

            while(resultSet.next()) {
                user = new User(resultSet.getString(DatabaseInfo.userColumn.email), resultSet.getString(DatabaseInfo.userColumn.name),
                        resultSet.getString(DatabaseInfo.userColumn.password), resultSet.getString(DatabaseInfo.userColumn.role));
            }
        }catch(Exception sqle) {
            sqle.printStackTrace();
            return null;
        }
        finally {
            try{
                Objects.requireNonNull(statement).close();
                Objects.requireNonNull(resultSet).close();
                connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return user;
    }
    
    public void add(User user) {
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = database.getConnection();
            String query = "INSERT INTO " + DatabaseInfo.userTable + " VALUES(?, ?, ?, ?)";
            statement = connection.prepareStatement(query);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getName());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getRole());
            statement.executeUpdate();
        }catch(PSQLException psql){
            System.out.println("Het wachtwoord of de gebruikersnaam is niet lang genoeg. Of de gebruiker bestaat al.");
        }catch(SQLException e){
            e.printStackTrace();

        }
        finally {
            try{
                Objects.requireNonNull(statement).close();
                connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void update(String oldEmail, User newUser) {
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = database.getConnection();
            String query = "UPDATE " + DatabaseInfo.userTable + " SET " + DatabaseInfo.userColumn.password + " = ? , "
                            + DatabaseInfo.userColumn.email + " = ? , " + DatabaseInfo.userColumn.name + " = ? "
                            + " WHERE " + DatabaseInfo.userColumn.email + " = ?;";

            statement = connection.prepareStatement(query);
            statement.setString(1, newUser.getPassword());
            statement.setString(2, newUser.getEmail());
            statement.setString(3, newUser.getName());
            statement.setString(4, oldEmail);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                Objects.requireNonNull(statement).close();
                connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
    
    public void delete(String email) {
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = database.getConnection();
            if(getUser(email) != null)
            {
                String query = "DELETE FROM " + DatabaseInfo.userTable + " WHERE " + DatabaseInfo.userColumn.email + " = ?";
                statement = connection.prepareStatement(query);
                statement.setString(1, email);
                statement.executeUpdate();
            }
        }catch(SQLException sqle) {
            sqle.printStackTrace();
        }
        finally {
            try{
                Objects.requireNonNull(statement).close();
                connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
}
