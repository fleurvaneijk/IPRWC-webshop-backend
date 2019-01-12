package nl.hsleiden.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.inject.Singleton;
import nl.hsleiden.model.User;

/**
 *
 * @author Peter van Vliet
 */
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
            String query = "SELECT * FROM user_account";
            PreparedStatement statement = database.connect().prepareStatement(query);
            resultSet = statement.executeQuery();

            while(resultSet.next()){
                User user = new User(resultSet.getString("email"), resultSet.getString("name"),
                        resultSet.getString("password"), resultSet.getString("role"));
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
    
    public User get(int id)
    {
        try
        {
            return users.get(id);
        }
        catch(IndexOutOfBoundsException exception)
        {
            return null;
        }
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
        users.add(user);
    }
    
    public void update(int id, User user)
    {
        users.set(id, user);
    }
    
    public void delete(int id)
    {
        users.remove(id);
    }
}
