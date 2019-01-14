package nl.hsleiden.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Singleton;
import nl.hsleiden.model.User;
import nl.hsleiden.model.DatabaseInfo;
import org.postgresql.util.PSQLException;

/**
 * @author Fleur van Eijk
 */
@Singleton
public class UserDAO {

    private final DatabaseConnection database;
    private final List<User> users;
    private ResultSet resultSet = null;

    public UserDAO(){
        this.database = new DatabaseConnection();
        this.users = new ArrayList<>();
    }
    
    public List<User> getAll() {
        try{
            String query = "SELECT * FROM " + DatabaseInfo.userTableName;
            PreparedStatement statement = database.getConnection().prepareStatement(query);
            resultSet = statement.executeQuery();

            while(resultSet.next()){
                User user = new User(resultSet.getString(DatabaseInfo.userColumnNames.email), resultSet.getString(DatabaseInfo.userColumnNames.name),
                        resultSet.getString(DatabaseInfo.userColumnNames.password), resultSet.getString(DatabaseInfo.userColumnNames.role));

                System.out.println(user.getName());

                users.add(user);
            }
        }catch(SQLException sqle){
            sqle.printStackTrace();
        }
        finally {
            try{
                resultSet.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return users;
    }
    
    public User getUser(String email) {
        User user;
        try{
            try{
                String query = "SELECT * FROM " + DatabaseInfo.userTableName + " WHERE  " + DatabaseInfo.userColumnNames.email + " = ?";
                PreparedStatement statement = database.getConnection().prepareStatement(query);
                statement.setString(1, email);
                resultSet = statement.executeQuery();
                resultSet.next();
                user = new User(resultSet.getString(DatabaseInfo.userColumnNames.email), resultSet.getString(DatabaseInfo.userColumnNames.name),
                        resultSet.getString(DatabaseInfo.userColumnNames.password), resultSet.getString(DatabaseInfo.userColumnNames.role));
            }catch(PSQLException e){
                System.out.println("De gebruiker: " + email + " bestaat niet.");
                return null;
            }
        }catch(SQLException sqle) {
            sqle.printStackTrace();
            return null;
        }
        finally {
            try{
                resultSet.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return user;
    }
    
    public void add(User user) {
        try {
            String query = "INSERT INTO " + DatabaseInfo.userTableName + " VALUES(?, ?, ?, ?)";
            PreparedStatement statement = database.getConnection().prepareStatement(query);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getName());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getRole());
            try{
                statement.execute();
            }catch(PSQLException constraintError){
                System.out.println("Het wachtwoord of de gebruikersnaam is niet lang genoeg.");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        finally {
            try{
                resultSet.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void changePassword(User user) {
        try {
            if(getUser(user.getEmail()) != null) {
                String query = "UPDATE " + DatabaseInfo.userTableName + " SET " + DatabaseInfo.userColumnNames.password + " = ? WHERE " + DatabaseInfo.userColumnNames.email + " = ?;";
                PreparedStatement statement = database.getConnection().prepareStatement(query);
                statement.setString(1, user.getPassword());
                statement.setString(2, user.getEmail());
                statement.execute();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try{
                resultSet.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
    
    public void delete(String email) {
        try{
            if(getUser(email) != null)
            {
                String query = "DELETE FROM " + DatabaseInfo.userTableName + " WHERE " + DatabaseInfo.userColumnNames.email + " = ?";
                PreparedStatement statement = database.getConnection().prepareStatement(query);
                statement.setString(1, email);
                statement.execute();
            }
        }catch(SQLException sqle) {
            sqle.printStackTrace();
        }
        finally {
            try{
                resultSet.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
}
