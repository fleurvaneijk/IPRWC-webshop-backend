package nl.hsleiden.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.inject.Singleton;
import nl.hsleiden.model.User;
import nl.hsleiden.model.DatabaseInfo;
import org.postgresql.util.PSQLException;

@Singleton
public class UserDAO
{
    private final List<User> users;

    private DatabaseConnection database;
    private ResultSet resultSet = null;

    public UserDAO() {
        users = new ArrayList<>();
    }

    public UserDAO(DatabaseConnection database){
        this.database = database;
        users = new ArrayList<>();
    }
    
    public List<User> getAll()
    {
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
    
    public User getByEmail(String email)
    {
        Optional<User> result = users.stream()
            .filter(user -> user.getEmail().equals(email))
            .findAny();
        
        return result.isPresent()
            ? result.get()
            : null;
    }
    
    public void add(User user)
    {
        try {
            String query = "INSERT INTO " + DatabaseInfo.userTableName + " VALUES(?, ?, ?, ?)";
            PreparedStatement statement = database.getConnection().prepareStatement(query);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getName());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getRole());
            // TODO: 12/01/19 Message when name or password is nog long enough
            try{
                statement.execute();
            }catch(PSQLException constraintError){
                System.out.println("Het wachtwoord is niet lang genoeg.");
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
    
    public void delete(String email)
    {
        try{
            User user = getByEmail(email);

            if(user != null)
            {
                String query = "DELETE FROM " + DatabaseInfo.userTableName + " WHERE " + DatabaseInfo.userColumnNames.email + " = ?";
                PreparedStatement statement = database.getConnection().prepareStatement(query);
                statement.setString(1, email);
                statement.execute();
            }
            else {
                System.out.println("De gebruiker: " + email + " bestaat niet.");
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
